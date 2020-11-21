import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.cha.ClassHierarchy;

public class Entry {
    private AllApplicationEntrypoints eps;

    /**
     * 生成进入点，以分析域和类层次对象为构建原料
     * @param scope 分析域对象
     * @param cha 类层次对象
     */
    public Entry(AnalysisScope scope, ClassHierarchy cha){
        try{
            eps = new AllApplicationEntrypoints(scope, cha);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 返回Entry类的Iterable<Entrypoint>对象
     * @return eps
     */
    public AllApplicationEntrypoints getEps() {
        return eps;
    }
}
