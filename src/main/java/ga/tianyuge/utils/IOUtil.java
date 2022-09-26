package ga.tianyuge.utils;

import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/08/30 16:52
 */
public class IOUtil {
    public static String readLineFromConsole(){
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String input = null;
        try {
            inputStreamReader = new InputStreamReader(System.in);
            bufferedReader = new BufferedReader(inputStreamReader);
            System.out.println("Input Stream Created");
            while (true) {
                System.out.print("Enter something : ");
                input = bufferedReader.readLine();
                if ("q".equals(input)) {
                    System.out.println("Exit!");
                    System.exit(0);
                }
                System.out.println("input : " + input);
                System.out.println("-----------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!ObjectUtils.isEmpty(bufferedReader)) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!ObjectUtils.isEmpty(inputStreamReader)) {
                try {
                    assert bufferedReader != null;
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return input;
    }
}
