import java.util.ArrayList;
import java.util.List;

public class ClassNode {
    String className;
    List<ClassNode> predClassNodes;
    List<MethodNode> methods;
    MethodNode currentMethodNode;


    public ClassNode(String className) {
        this.className = className;
        methods = new ArrayList<MethodNode>();
        predClassNodes = new ArrayList<ClassNode>();
        currentMethodNode = null;
    }

    /**
     * 添加方法节点
     *
     * @param method 方法节点
     */
    public void addMethod(MethodNode method) {
        if (!this.methods.contains(method)) {
            this.methods.add(method);
        }
        method.classNode=this;
    }

    /**
     * 添加前继类节点
     *
     * @param classNode 类节点
     */
    public void addPredClass(ClassNode classNode) {
        if (!this.predClassNodes.contains(classNode)) {
            this.predClassNodes.add(classNode);
        }
    }

}
