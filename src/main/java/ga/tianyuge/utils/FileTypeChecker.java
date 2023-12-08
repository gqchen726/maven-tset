package ga.tianyuge.utils;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileTypeChecker {
    private static Tika tika = null;
    static {
        if (Objects.isNull(tika)) {
            tika = new Tika();
        }
    }
    public static String getFileType(byte[] fileBytes) {
        return tika.detect(fileBytes);
    }

    public static void main(String[] args) {
        File file = new File("C:\\Temp\\TEST.pdf");
        // 把file转化为fileBytes
        byte[] fileBytes;
        try {
            fileBytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileType = getFileType(fileBytes);
        System.out.println("File type: " + fileType);
    }
}