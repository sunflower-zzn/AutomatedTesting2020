import java.util.ArrayList;
import java.util.List;

public class MethodNode {
    String methodName;
    ClassNode classNode;
    List<MethodNode> predMethodNodes;

    public MethodNode(String methodName) {
        this.methodName = methodName;
        classNode = null;
        this.predMethodNodes = new ArrayList<MethodNode>();
    }

    /**
     * 添加前继方法节点
     *
     * @param methodNode 方法节点
     */
    public void addPredMethod(MethodNode methodNode) {
        if (!this.predMethodNodes.contains(methodNode)) {
            this.predMethodNodes.add(methodNode);
        }
    }
}
