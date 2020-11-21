import org.junit.Test;

public class test {
    @Test
    public void CentralNodeDotFileBuilder0() {
        try{
            String init = Analysis.rootPath;
            Analysis centralNode = new Analysis(new String[]{"-c",
                    Analysis.rootPath.substring(0,Analysis.rootPath.length()-7) + "Data/0-CMD/target",
                    Analysis.rootPath.substring(0,Analysis.rootPath.length()-7)+"Data/0-CMD/data/change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
