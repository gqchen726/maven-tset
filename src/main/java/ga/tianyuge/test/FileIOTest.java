package ga.tianyuge.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author: guoqing.chen01@hand-china.com 2022-02-15 14:54
 **/

public class FileIOTest {

    public static void main(String[] args) {
        read("C:\\temp\\POS_CN_ADP_1001_POSIPL_20220126_042452_000001.dat");
    }

    public static void read(String fileName) {
        File file = new File(fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                System.out.print(bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
