import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;

public class CH {
    private ClassHierarchy cha;

    /**
     * 生成类层次关系对象
     * @param scope 分析域对象
     */
    public CH(AnalysisScope scope){
        try{
            //在缺失了某些分析所需的类时， makeWithRoot 方法会尽可能地为依赖这些缺失类的方法添加“Root”
            //即认为java.lang.Object 为这些类的父类。
            cha = ClassHierarchyFactory.makeWithRoot(scope);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 返回CH类的ClassHierarchy 对象
     * @return cha
     */
    public ClassHierarchy getCha() {
        return cha;
    }
}
