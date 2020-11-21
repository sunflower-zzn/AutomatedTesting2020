import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DOT {
    private String rootPath;
    private List<String> classDotSentences;
    private List<String> methodDotSentences;

    public DOT(String rootPath,List<String> classDotSentences,List<String> methodDotSentences){
        this.rootPath=rootPath;
        this.classDotSentences=classDotSentences;
        this.methodDotSentences=methodDotSentences;
    }
    public void classDotFileBuilder() throws IOException {
        FileWriter classDotFile;
        String content;
        String path;
        Runtime run;
        String command;

        String name = this.rootPath + "-class";
        path = "../Report/" + name + ".dot";
        classDotFile = new FileWriter(path);
        content = "";
        command = "dot -T pdf -o ../Report/" + name + ".pdf ../Report/" + name + ".dot";
        run = Runtime.getRuntime();

        for (String sentence : this.classDotSentences) {
            content = content + sentence + "\n";
        }
        classDotFile.write(content);
        classDotFile.close();
        run.exec("cmd.exe /c " + command);
    }

    public void methodDotFileBuilder() throws IOException {
        FileWriter methodDotFile;
        String content;
        String path;
        Runtime run;
        String command;

        String name = this.rootPath + "-method";
        path = "../Report/" + name + ".dot";
        methodDotFile = new FileWriter(path);
        content = "";
        command = "dot -T pdf -o ../Report/" + name + ".pdf ../Report/" + name + ".dot";
        run = Runtime.getRuntime();

        for (String sentence : this.methodDotSentences) {
            content = content + sentence + "\n";
        }
        methodDotFile.write(content);
        methodDotFile.close();
        run.exec("cmd.exe /c " + command);
    }
}
