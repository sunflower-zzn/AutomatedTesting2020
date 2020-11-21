import org.junit.Test;

import java.io.IOException;

public class test {
    @Test
    public void test0C() {
        try{
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\0-CMD\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\0-CMD\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test0M() {
        try{
            Analysis.main(new String[]{"-m",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\0-CMD\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\0-CMD\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test1C() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\1-ALU\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\1-ALU\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test1M() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\1-ALU\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\1-ALU\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test2C() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\2-DataLog\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\2-DataLog\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test2M() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\2-DataLog\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\2-DataLog\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test3C() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\3-BinaryHeap\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\3-BinaryHeap\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test3M() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\3-BinaryHeap\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\3-BinaryHeap\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void test4C() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\4-NextDay\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\4-NextDay\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test4M() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\4-NextDay\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\4-NextDay\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void test5C() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\5-MoreTriangle\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\5-MoreTriangle\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test5M() {
        try {
            Analysis.main(new String[]{"-c",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\5-MoreTriangle\\target",
                    "C:\\Users\\sunflower\\Documents\\GitHub\\AutomatedTesting2020\\Data\\5-MoreTriangle\\data\\change_info.txt"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
