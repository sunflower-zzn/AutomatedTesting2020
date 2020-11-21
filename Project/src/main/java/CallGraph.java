import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.cha.ClassHierarchy;

public class CallGraph {
    private CHACallGraph cg;

    /**
     * 构建调用图（利用CHA算法构建调用图）
     * @param cha 类层次对象
     * @param eps 进入点对象
     */
    public CallGraph(ClassHierarchy cha, AllApplicationEntrypoints eps){
        try {
            CHACallGraph cg = new CHACallGraph(cha);
            cg.init(eps);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 返回CallGraph类的CHACallGraph对象
     * @return CHACallGraph
     */
    public CHACallGraph getCg() {
        return cg;
    }
}
