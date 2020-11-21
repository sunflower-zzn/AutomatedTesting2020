import com.ibm.wala.classLoader.ShrikeBTMethod;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;

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
    private List<String> classDependencies;
    private List<String> methodDependencies;
    private List<ClassNode> changeClassNodes;
    private List<MethodNode> changeMethodNodes;
    private List<String> changeResultInfos;
    private String changeResultInfo = "";
    private String rootName;

    public Analysis(String[] args) {
        this.param = args[0];
        this.targetPath = args[1];
        this.changeInfoPath = args[2];
        this.changeInfos = getChangeInfo();
        classNodes = new ArrayList<ClassNode>();
        methodNodes = new ArrayList<MethodNode>();
        classDependencies = new ArrayList<String>();
        methodDependencies = new ArrayList<String>();
        changeClassNodes = new ArrayList<ClassNode>();
        changeMethodNodes = new ArrayList<MethodNode>();
        changeResultInfos = new ArrayList<String>();
        try {
            String[] temp = targetPath.split("/");
            this.rootName = temp[temp.length - 2];
        } catch (Exception e) {
            String[] temp = targetPath.split("\\\\");
            this.rootName = temp[temp.length - 2];
        }
    }

    /**
     * 静态测试入口方法
     */
    public static void main(String[] args) {
        Analysis analysis = new Analysis(args);
        //生成分析域
        Scope scope = new Scope(analysis.targetPath);
        //生成类层次
        CH ch = new CH(scope.scope);
        //确定进入点
        Entry entry = new Entry(scope.scope, ch.cha);
        //构建调用图
        CG cg = new CG(scope.scope, ch.cha, entry.eps);
        //遍历调用图
        analysis.forEachCG(cg.cg);
        //生成dot文件并绘制pdf文件
        DOT dot = new DOT(analysis.rootName, analysis.classDependencies, analysis.methodDependencies);
        try {
            dot.classDotFileBuilder();
            dot.methodDotFileBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取change_info.txt文件，转化为list
     *
     * @return 处理后的代码变更信息
     */
    private List<String> getChangeInfo() {
        if (!changeInfoPath.endsWith(".txt")) return null;
        BufferedReader bufferedReader;
        List<String> changeInfos = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(changeInfoPath));
            String str = bufferedReader.readLine();
            while (str != null) {
                changeInfos.add(str);
                str = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return changeInfos;
    }

    /**
     * 遍历调用图
     */
    public void forEachCG(CallGraph cg) {
        //遍历cg中所有的节点，更新classNodes和methodNodes，记录所有的依赖关系
        for (CGNode cgNode : cg) {
            if (cgNode.getMethod() instanceof ShrikeBTMethod) {
                ShrikeBTMethod method = (ShrikeBTMethod) cgNode.getMethod();
                if ("Application".equals(method.getDeclaringClass().getClassLoader().toString())) {
                    //获取当前method的类节点和方法节点
                    ClassNode classNode = getCurrentClassNode(method);
                    MethodNode methodNode = classNode.currentMethodNode;
                    Iterator<CGNode> iterator = cg.getPredNodes(cgNode);
                    //遍历当前节点的前继节点
                    while (iterator.hasNext()) {
                        CGNode predCGNode = iterator.next();
                        if (predCGNode.getMethod() instanceof ShrikeBTMethod) {
                            ShrikeBTMethod predMethod = (ShrikeBTMethod) predCGNode.getMethod();
                            if ("Application".equals(predMethod.getDeclaringClass().getClassLoader().toString())) {
                                //获取前继节点method的类节点和方法节点
                                ClassNode predClassNode = getCurrentClassNode(predMethod);
                                MethodNode predMethodNode = predClassNode.currentMethodNode;
                                //更新前继类和前继方法
                                classNode.addPredClass(predClassNode);
                                methodNode.addPredMethod(predMethodNode);
                                //增加方法依赖
                                String classReference = "\t\"" + classNode.className + "\" -> \"" + predClassNode.className + "\";";
                                String methodReference = "\t\"" + methodNode.methodName + "\" -> \"" + predMethodNode.methodName + "\";";
                                if (!this.classDependencies.contains(classReference))
                                    this.classDependencies.add(classReference);
                                if (!this.methodDependencies.contains(methodReference))
                                    this.methodDependencies.add(methodReference);
                            }
                        }
                    }
                }
            }
        }
        //根据参数选择不同粒度
        if (param.equals("-c")) {
            classChangeInfo();
        } else {
            methodChangeInfo();
        }
        //打印选择结果
        printChangeResult();
    }

    /**
     * 获取method对应的classNode
     *
     * @param method 一般地，本项目中所有和业务逻辑相关的方法都是ShrikeBTMethod对象
     * @return method对应的类节点
     */
    public ClassNode getCurrentClassNode(ShrikeBTMethod method) {
        // 获取声明该方法的类的内部表示
        String classInnerName = method.getDeclaringClass().getName().toString();
        // 获取方法签名
        String signature = method.getSignature();
        // 组合得到方法的内部表示
        String methodInnerName = classInnerName + " " + signature;
        // 遍历ClassNodes和MethodNodes找到对应的节点，没有则设置为null
        ClassNode classNode = null;
        for (ClassNode node : this.classNodes) {
            if (node.className.equals(classInnerName)) {
                classNode = node;
                break;
            }
        }
        MethodNode methodNode = null;
        for (MethodNode node : this.methodNodes) {
            if (node.methodName.equals(methodInnerName)) {
                methodNode = node;
                break;
            }
        }
        //为null的话创建classNode节点
        if (classNode == null) {
            classNode = new ClassNode(classInnerName);
            this.classNodes.add(classNode);
        }
        if (methodNode == null) {
            methodNode = new MethodNode(methodInnerName);
            this.methodNodes.add(methodNode);
        }
        methodNode.classNode = classNode;
        classNode.addMethod(methodNode);
        classNode.currentMethodNode = methodNode;
        return classNode;
    }

    /**
     * 类粒度变更信息
     */
    public void classChangeInfo() {
        MethodNode changeMethod;
        ClassNode changeClass;
        for (String changeInfo : this.changeInfos) {
            changeMethod = null;
            for (MethodNode node : this.methodNodes) {
                if (node.methodName.equals(changeInfo)) {
                    changeMethod = node;
                    break;
                }
            }
            assert changeMethod != null;
            changeClass = changeMethod.classNode;
            getChangeClass(changeClass);
        }
    }

    /**
     * 方法粒度变更信息
     */
    public void methodChangeInfo() {
        MethodNode changeMethod;
        for (String changeInfo : this.changeInfos) {
            changeMethod = null;
            for (MethodNode node : this.methodNodes) {
                if (node.methodName.equals(changeInfo)) {
                    changeMethod = node;
                    break;
                }
            }
            assert changeMethod != null;
            getChangeMethod(changeMethod);
        }
    }


    /**
     * 递归寻找所有改变的类节点
     *
     * @param classNode 类节点
     */
    private void getChangeClass(ClassNode classNode) {
        for (ClassNode node : classNode.predClassNodes) {
            if (!this.changeClassNodes.contains(node) && !node.equals(classNode)) {
                for (MethodNode methodNode : node.methods) {
                    if (!this.changeMethodNodes.contains(methodNode)) {
                        this.changeMethodNodes.add(methodNode);
                    }
                }
                changeClassNodes.add(node);
                getChangeClass(node);
            }
        }
    }

    /**
     * 递归寻找所有改变的方法节点
     *
     * @param methodNode 方法节点
     */
    private void getChangeMethod(MethodNode methodNode) {
        for (MethodNode node : methodNode.predMethodNodes) {
            if (!this.changeMethodNodes.contains(node) && !node.equals(methodNode)) {
                this.changeMethodNodes.add(node);
                getChangeMethod(node);
            }
        }
    }

    /**
     * 打印参数选择的粒度的测试选择信息
     */
    public void printChangeResult() {
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
            if (param.equals("-c")) {
                fileWriter = new FileWriter("selection-class.txt");
            } else {
                fileWriter = new FileWriter("selection-method.txt");
            }
            fileWriter.write(this.changeResultInfo);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
