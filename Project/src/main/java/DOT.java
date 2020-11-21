import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DOT {
    String rootPath;
    List<String> classDependencies;
    List<String> methodDependencies;

    public DOT(String rootPath, List<String> classDependencies, List<String> methodDependences) {
        this.rootPath = rootPath;
        this.classDependencies = classDependencies;
        this.methodDependencies = methodDependences;
    }

    /**
     * 生成类dot文件并绘制pdf
     */
    public void classDotFileBuilder() throws IOException {
        FileWriter classDotFile;
        String name = this.rootPath + "-class";
        Runtime run = Runtime.getRuntime();
        classDotFile = new FileWriter("../Report/" + name + ".dot");
        String content;
        content = "digraph cmd_class {\n";
        for (String s : this.classDependencies) {
            content += s + "\n";
        }
        content += "}";
        classDotFile.write(content);
        classDotFile.close();
        run.exec("cmd.exe /c " + "dot -T pdf -o ../Report/" + name + ".pdf ../Report/" + name + ".dot");
    }

    /**
     * 生成方法dot文件并绘制pdf
     */
    public void methodDotFileBuilder() throws IOException {
        FileWriter methodDotFile;
        String name = this.rootPath + "-method";
        Runtime run = Runtime.getRuntime();
        methodDotFile = new FileWriter("../Report/" + name + ".dot");
        String content;
        content = "digraph cmd_method {\n";
        for (String s : this.methodDependencies) {
            content += s + "\n";
        }
        content += "}";
        methodDotFile.write(content);
        methodDotFile.close();
        run.exec("cmd.exe /c " + "dot -T pdf -o ../Report/" + name + ".pdf ../Report/" + name + ".dot");
    }
}
