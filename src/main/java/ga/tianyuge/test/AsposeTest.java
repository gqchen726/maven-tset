package ga.tianyuge.test;

import com.aspose.words.DocSaveOptions;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/02/03 17:24
 * @Email: guoqing.chen01@hand-china.com
 */
public class AsposeTest {

    @Test
    public void wordConvertPdfOfFileTest() throws Exception {
        Document doc = new Document("C:\\Users\\GuoqingChen01\\Documents\\采购协议测试-CYQ-1020.docx1675410565909.docx");
        doc.save("C:\\Users\\GuoqingChen01\\Documents\\Output.pdf");
    }

    @Test
    public void wordConvertPdfOfStreamTest() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\GuoqingChen01\\Documents\\采购协议测试-CYQ-1020.docx1675410565909.docx");
        Document doc = new Document(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\GuoqingChen01\\Documents\\Output.pdf");
        doc.save(fileOutputStream, SaveFormat.PDF);
    }
}
