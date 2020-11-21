import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.config.AnalysisScopeReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Scope {
    AnalysisScope scope;

    /**
     * 生成分析域，结合两种方法
     *
     * @param targetPath .class文件目录
     */
    public Scope(String targetPath) {
        scopeRead(targetPath);
        addScope(getFiles(targetPath + "/classes"));
        addScope(getFiles(targetPath + "/test-classes"));
    }

    /**
     * 构建分析域：AnalysisScopeReader.readJavaScope
     *
     * @param targetPath .class文件目录
     */
    private void scopeRead(String targetPath) {
        try {
            //构建AnalysisScope对象
            //返回一个只包含Java原生类的分析域，并排除一些不常用的原生类
            scope = AnalysisScopeReader.readJavaScope(
                    "scope.txt",
                    new File("exclusion.txt"),
                    AnalysisScope.class.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    /**
     * 使用字节码（ .class ）作为程序分析的原料，返回.class文件list
     *
     * @param root 代码路径
     * @return 返回文件List
     */
    private List<File> getFiles(String root) {
        List<File> res = new ArrayList<File>();
        String[] paths = new File(root).list();
        assert paths != null;
        for (String path : paths) {
            File f = new File(root + "/" + path);
            if (!f.isDirectory()) {
                if (path.endsWith(".class")) res.add(f);
            } else {
                res.addAll(getFiles(root + "/" + path));
            }
        }
        return res;
    }

    /**
     * 分析域动态添加：scope.addClassFileToScope
     *
     * @param files 需要添加的文件列表
     */
    private void addScope(List<File> files) {
        try {
            for (File file : files) {
                //动态添加想要分析的类(.class)
                scope.addClassFileToScope(ClassLoaderReference.Application, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
