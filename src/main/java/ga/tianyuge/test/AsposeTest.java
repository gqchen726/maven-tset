/*
package ga.tianyuge.test;

import com.aspose.words.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

*/
/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/02/03 17:24
 * @Email: guoqing.chen01@hand-china.com
 *//*

public class AsposeTest {

    private String docPath = "C:\\Users\\GuoqingChen01\\Desktop\\temp\\test.docx";
    private PdfSaveOptions DEFAULT_PDF_SAVE_OPTIONS = new PdfSaveOptions();
    private String outPath = "C:\\Users\\GuoqingChen01\\Documents\\Output.pdf";

    {
        DEFAULT_PDF_SAVE_OPTIONS.setCompliance(3);
        DEFAULT_PDF_SAVE_OPTIONS.setPrettyFormat(true);
    }

    @Test
    public void wordConvertPdfOfFileTest() throws Exception {
        Document doc = new Document(docPath);
        doc.save(outPath, DEFAULT_PDF_SAVE_OPTIONS);
    }

    @Test
    public void wordConvertPdfOfStreamTest() throws Exception {
        FontSettings.getDefaultInstance().getSubstitutionSettings().getDefaultFontSubstitution().setDefaultFontName("WenQuanYi Micro Hei");
        FileInputStream fileInputStream = new FileInputStream(docPath);
        Document doc = new Document(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        doc.save(fileOutputStream, DEFAULT_PDF_SAVE_OPTIONS);
    }
}
*/
