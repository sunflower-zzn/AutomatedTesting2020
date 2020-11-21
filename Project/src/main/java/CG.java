import com.ibm.wala.classLoader.Language;
import com.ibm.wala.ipa.callgraph.AnalysisCacheImpl;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchy;

public class CG {
    CallGraph cg;

    /**
     * 构建调用图（利用0-CFA算法构建调用图）
     *
     * @param cha 类层次对象
     * @param eps 进入点对象
     */
    public CG(AnalysisScope scope, ClassHierarchy cha, AllApplicationEntrypoints eps) {
        try {
            AnalysisOptions options = new AnalysisOptions(scope, eps);
            SSAPropagationCallGraphBuilder builder = Util.makeZeroCFABuilder(Language.JAVA, options, new AnalysisCacheImpl(), cha, scope);
            cg = builder.makeCallGraph(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
