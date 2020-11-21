import com.ibm.wala.classLoader.ShrikeBTMethod;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Analysis {
    private String param;
    private String targetPath;
    private String changeInfoPath;
    private List<String> changeInfos;
    private List<ClassNode> classNodes;
    private List<MethodNode> methodNodes;
    private List<String> classNames;
    private List<String> methodNames;
    private List<String> classDotSentences;
    private List<String> methodDotSentences;
    private List<ClassNode> changeClassNodes;
    private List<MethodNode> changeMethodNodes;
    private List<String> changeResultInfos;
    private String changeResultInfo = "";
    private String projectName;
    static String rootPath = System.getProperty("user.dir");

    public Analysis(String[] args){
        this.param=args[0];
        this.targetPath=args[1];
        this.changeInfoPath=args[2];
        this.changeInfos=getChangeInfo();
        classNodes=new ArrayList<ClassNode>();
        methodNodes=new ArrayList<MethodNode>();
        classNames=new ArrayList<String>();
        methodNames=new ArrayList<String>();
        classDotSentences=new ArrayList<String>();
        this.classDotSentences.add("digraph cmd_class {");
        methodDotSentences=new ArrayList<String>();
        this.methodDotSentences.add("digraph cmd_method {");
        changeClassNodes=new ArrayList<ClassNode>();
        changeMethodNodes=new ArrayList<MethodNode>();
        changeResultInfos=new ArrayList<String>();
        try {
            String[] temp = targetPath.split("/");
            this.projectName = temp[temp.length - 2];
        }catch (Exception e){
            String[] temp = targetPath.split("\\\\");
            this.projectName = temp[temp.length - 2];
        }
        Scope scope=new Scope(this.targetPath);
        CH ch=new CH(scope.getScope());
        Entry entry=new Entry(scope.getScope(),ch.getCha());
        CG cg=new CG(scope.getScope(),ch.getCha(),entry.getEps());
        forEachCG(cg.getCg());
        DOT dot=new DOT(this.projectName,this.classDotSentences,this.methodDotSentences);
        try{
            dot.classDotFileBuilder();
            dot.methodDotFileBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void forEachCG(CallGraph cg){
        for(CGNode node: cg) {
            // node中包含了很多信息，包括类加载器、方法信息等，这里只筛选出需要的信息
            if(node.getMethod() instanceof ShrikeBTMethod) {
                // node.getMethod()返回一个比较泛化的IMethod实例，不能获取到我们想要的信息
                // 一般地，本项目中所有和业务逻辑相关的方法都是ShrikeBTMethod对象
                ShrikeBTMethod method = (ShrikeBTMethod) node.getMethod();
                // 使用Primordial类加载器加载的类都属于Java原生类，我们一般不关心。
                if("Application".equals(method.getDeclaringClass().getClassLoader().toString())) {
                    ClassNode classNode=getCurrentClassNode(method);
                    MethodNode methodNode = classNode.currentMethodNode;
                    Iterator<CGNode> iterator = cg.getPredNodes(node);
                    while (iterator.hasNext()) {
                        CGNode predNode = iterator.next();
                        if (predNode.getContext() instanceof ShrikeBTMethod) {
                            ShrikeBTMethod predMethod = (ShrikeBTMethod) predNode.getMethod();
                            if ("Application".equals(predMethod.getDeclaringClass().getClassLoader().toString())) {
                                ClassNode predClassNode=getCurrentClassNode(predMethod);
                                MethodNode predMethodNode = predClassNode.currentMethodNode;
                                classNode.addPredClass(predClassNode);
                                methodNode.addPredMethod(predMethodNode);
                                String classReference = "    \"" + classNode.className + "\" -> \"" + predClassNode.className + "\";";
                                String methodReference = "    \"" + methodNode.methodName + "\" -> \"" + predMethodNode.methodName + "\";";
                                if (!this.classDotSentences.contains(classReference)) {
                                    this.classDotSentences.add(classReference);
                                }
                                if (!this.methodDotSentences.contains(methodReference)) {
                                    this.methodDotSentences.add(methodReference);
                                }
                            }
                        }
                    }
                }
            }
        }
        this.classDotSentences.add("}");
        this.methodDotSentences.add("}");
        if (param.equals("-c")) {
            this.classChangeInfo();
        } else{
            this.methodChangeInfo();
        }
        this.collectChangeResultInfo();
    }

    public ClassNode getCurrentClassNode(ShrikeBTMethod method) {
        // 获取声明该方法的类的内部表示
        String classInnerName = method.getDeclaringClass().getName().toString();
        // 获取方法签名
        String signature = method.getSignature();
        // 组合得到方法的内部表示
        String methodInnerName = classInnerName + " " + signature;

        ClassNode classNode = getClassNode(classInnerName);
        MethodNode methodNode = getMethodNode(methodInnerName);
        if (classNode == null) {
            classNode = new ClassNode(classInnerName);
            this.classNames.add(classInnerName);
            this.classNodes.add(classNode);
        }
        if (methodNode == null) {
            methodNode = new MethodNode(methodInnerName);
            this.methodNames.add(methodInnerName);
            this.methodNodes.add(methodNode);
        }
        methodNode.setClassName(classNode);
        classNode.addMethod(methodNode);
        classNode.setCurrentNode(methodNode);
        return classNode;
    }

    public ClassNode getClassNode(String className) {
        if (!this.classNames.contains(className)) {
            return null;
        } else {
            for (ClassNode node : this.classNodes) {
                if (node.methods.equals(className)) {
                    return node;
                }
            }
        }
        return null;
    }

    public void classChangeInfo() {
        MethodNode changeMethod;
        ClassNode changeClass;

        for (String changeInfo : this.changeInfos) {
            changeMethod = getMethodNode(changeInfo);
            changeClass = changeMethod.classNode;
            this.getChangeClass(changeClass, this.changeClassNodes, this.changeMethodNodes);
        }
    }

    public void methodChangeInfo() {
        MethodNode changeMethod;

        for (String changeInfo : this.changeInfos) {
            changeMethod = getMethodNode(changeInfo);
            this.getChangeMethod(changeMethod, this.changeMethodNodes);
        }
    }

    public MethodNode getMethodNode(String methodName) {
        if (!this.methodNames.contains(methodName)) {
            return null;
        } else {
            for (MethodNode node : this.methodNodes) {
                if (node.methodName.equals(methodName)) {
                    return node;
                }
            }
        }
        return null;
    }

    private void getChangeClass(ClassNode classNode, List<ClassNode> changeClassNodes, List<MethodNode> changeMethodNodes) {
        for (ClassNode node : classNode.predClassNodes) {
            if (!changeClassNodes.contains(node) && !node.equals(classNode)) {
                for (MethodNode methodNode : node.methods) {
                    if (!changeMethodNodes.contains(methodNode)) {
                        changeMethodNodes.add(methodNode);
                    }
                }
                changeClassNodes.add(node);
                this.getChangeClass(node, changeClassNodes, changeMethodNodes);
            }
        }
    }


    private void getChangeMethod(MethodNode methodNode, List<MethodNode> changeMethodNodes) {
        for (MethodNode node : methodNode.predMethodNodes) {
            if (!changeMethodNodes.contains(node) && !node.equals(methodNode)) {
                changeMethodNodes.add(node);
                this.getChangeMethod(node, changeMethodNodes);
            }
        }
    }

    public void collectChangeResultInfo() {
        for (MethodNode node : this.changeMethodNodes) {
            if (node.methodName.contains("Test") && !node.methodName.contains("init")) {
                this.changeResultInfos.add(node.methodName);
            }
        }
        this.changeResultInfos.sort(Comparator.naturalOrder());
        for (String info : this.changeResultInfos) {
            this.changeResultInfo += info;
            this.changeResultInfo += "\n";
        }
        this.changeResultInfo += "\n";

        try {
            FileWriter fileWriter = null;
            if(param.equals("-c")){
                fileWriter = new FileWriter("selection-class.txt");
            }else{
                fileWriter = new FileWriter("selection-method.txt");
            }
            fileWriter.write(this.changeResultInfo);
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<String> getChangeInfo(){
        if(!changeInfoPath.endsWith(".txt"))return null;
        BufferedReader bufferedReader;
        List<String> changeInfos=new ArrayList<String>();
        try{
            bufferedReader=new BufferedReader(new FileReader(changeInfoPath));
            String str=bufferedReader.readLine();
            while(str!=null){
                changeInfos.add(str);
                str=bufferedReader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return changeInfos;
    }
}
