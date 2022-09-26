package ga.tianyuge.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: guoqing.chen01@hand-china.com 2022-02-11 10:13
 **/

public class Test5 {

    public static void main(String[] args) throws IOException {
//        File file = new File("C:/temp/PhysicalCount_5030_01_00815_20220126_104256_1.txt");
        File file = new File("C:/temp/test1.txt");

        int counter = 1;

        FileReader fileReader = null;
        FileWriter fileWriter = null;
        List<String> lines = new ArrayList<>();
        List<String> result = new ArrayList<>();

        try {
            fileReader = new FileReader(file);

            StringBuilder content = new StringBuilder();
            String alineString = null;
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(fileReader);
            while ((alineString = bufferedReader.readLine()) != null) {
                lines.add(alineString);
            }

            for (String line : lines) {
                String[] split = line.split("\t");
                String target = split[1];
                byte[] bytes = target.getBytes();
                if (bytes.length > 50) {

                    /*System.out.println("----------");
                    String[] split1 = target.split("");
                    for (String s : split1) {
                        byte[] bytes1 = s.getBytes();
                        System.out.println(s +  "-" + bytes1.length);
                    }
                    System.out.println("----------");*/


                    String res = counter + "-" + target + " - " + bytes.length;
                    result.add(res);
                }
                counter++;
            }

            for (String s : result) {
                System.out.println(s);
            }
            System.out.println("----------------");
            System.out.println(result.size());

            /*for (String line : lines) {
                fileWriter = new FileWriter("C:/temp/errorData/PhysicalCount_5030_01_00815_20220126_104256_"+counter+".txt");
                fileWriter.write(line);
                fileWriter.flush();
                counter++;
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }
}
