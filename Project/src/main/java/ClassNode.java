import java.util.ArrayList;
import java.util.List;

public class ClassNode {
    String className;
    List<ClassNode> predClassNodes;
    List<MethodNode> methods;
    MethodNode currentMethodNode;



    public ClassNode(String className){
        this.className = className;
        methods = new ArrayList<MethodNode>();
        predClassNodes = new ArrayList<ClassNode>();
        currentMethodNode = null;
    }

    public void addMethod(MethodNode method){
        if(!this.methods.contains(method)) {
            this.methods.add(method);
        }
        method.setClassName(this);
    }

    public void setCurrentNode(MethodNode currentNode) {
        this.currentMethodNode = currentNode;
    }

    public void addPredClass(ClassNode classNode){
        if(!this.predClassNodes.contains(classNode)){
            this.predClassNodes.add(classNode);
        }
    }

}
