package ga.tianyuge.test;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.*;

/**
 * @author: guoqing.chen01@hand-china.com 2022-02-11 17:50
 **/

public class Test6 {

    public static void main(String[] args) {
        try {
            byte[] bytes = new byte[100];
            ByteInputStream byteInputStream = new ByteInputStream();
            File file = new File("C:/temp/errorData/errorData/test.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            int i = 0;
            while ((i = fileInputStream.read(bytes)) != -1) {
                System.out.println();

            }


            FileReader fileReader = new FileReader(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
