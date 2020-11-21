import org.junit.Test;

public class test {
    @Test
    public void CentralNodeDotFileBuilder0() {
        try{
            String init = Analysis.rootPath;
            Analysis centralNode = new Analysis(new String[]{"-c",
                    Analysis.rootPath.substring(0,Analysis.rootPath.length()-7) + "Data/0-CMD/target",
                    Analysis.rootPath.substring(0,Analysis.rootPath.length()-7)+"Data/0-CMD/data/change_info.txt"});
            //centralNode.classDotFileBuilder();
            //centralNode.methodDotFileBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*@Test
    public void CentralNodeDotFileBuilder1() {
        try {
            String init = CentralNode.rootPath;
            CentralNode centralNode = new CentralNode(new String[]{"-c",
                    CentralNode.rootPath.substring(0,CentralNode.rootPath.length()-7)+"Data/1-ALU/target",
                    "noChange"});
            centralNode.classDotFileBuilder();
            centralNode.methodDotFileBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void CentralNodeDotFileBuilder2() {
        try {
            String init = CentralNode.rootPath;
            CentralNode centralNode = new CentralNode(new String[]{"-c",
                    CentralNode.rootPath.substring(0,CentralNode.rootPath.length()-7)+"Data/2-DataLog/target",
                    "noChange"});
            centralNode.classDotFileBuilder();
            centralNode.methodDotFileBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void CentralNodeDotFileBuilder3() {
        try {
            String init = CentralNode.rootPath;
            CentralNode centralNode = new CentralNode(new String[]{"-c",
                    CentralNode.rootPath.substring(0,CentralNode.rootPath.length()-7)+"Data/3-BinaryHeap/target",
                    "noChange"});
            centralNode.classDotFileBuilder();
            centralNode.methodDotFileBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void CentralNodeDotFileBuilder4() {
        try {
            String init = CentralNode.rootPath;
            CentralNode centralNode = new CentralNode(new String[]{"-c",
                    CentralNode.rootPath.substring(0,CentralNode.rootPath.length()-7)+"Data/4-NextDay/target",
                    "noChange"});
            centralNode.classDotFileBuilder();
            centralNode.methodDotFileBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void CentralNodeDotFileBuilder5() {
        try {
            String init = CentralNode.rootPath;
            CentralNode centralNode = new CentralNode(new String[]{"-c",
                    CentralNode.rootPath.substring(0,CentralNode.rootPath.length()-7)+"Data/5-MoreTriangle/target",
                    "noChange"});
            centralNode.classDotFileBuilder();
            centralNode.methodDotFileBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void CentralNodeDotFileBuilderOwn(){
        try{
            CentralNode centralNode = new CentralNode(new String[]{"-c",
                    "./target",
                    "noChange"});
            centralNode.classDotFileBuilder();
            centralNode.methodDotFileBuilder();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
