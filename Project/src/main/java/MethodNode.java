import java.util.ArrayList;
import java.util.List;

public class MethodNode {
    String methodName;
    ClassNode classNode;
    List<MethodNode> predMethodNodes;

    public MethodNode(String methodName){
        this.methodName = methodName;
        classNode = null;
        this.predMethodNodes = new ArrayList<MethodNode>();
    }

    public void setClassName(ClassNode classNode) {
        this.classNode = classNode;
    }

    public void addPredMethod(MethodNode methodNode){
        if(!this.predMethodNodes.contains(methodNode)) {
            this.predMethodNodes.add(methodNode);
        }
    }
}
