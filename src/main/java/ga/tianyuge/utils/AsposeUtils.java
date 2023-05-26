/*
package ga.tianyuge.utils;

import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.License;
import com.aspose.words.PdfSaveOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.io.OutputStream;

*/
/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/10 10:32
 * @Email: guoqing.chen01@hand-china.com
 *//*

public class AsposeUtils {
    private static final PdfSaveOptions DEFAULT_PDF_SAVE_OPTIONS = new PdfSaveOptions();
    private static final Logger LOGGER = LoggerFactory.getLogger(AsposeUtils.class);
    private static boolean licensed = false;

    public AsposeUtils() {
    }

    private static void loadLicense() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("Aspose.Words.lic");
            InputStream inputStream = classPathResource.getInputStream();
            License license = new License();
            license.setLicense(inputStream);
            licensed = true;
            LOGGER.info("Aspose.Words license loaded");
            DEFAULT_PDF_SAVE_OPTIONS.setCompliance(3);
            DEFAULT_PDF_SAVE_OPTIONS.setPrettyFormat(true);
            FontSettings.getDefaultInstance().getSubstitutionSettings().getDefaultFontSubstitution().setDefaultFontName("WenQuanYi Micro Hei");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private static void checkLicense() {
        if (!licensed) {
            loadLicense();
        }

    }

    public static void convertWords2Pdf(InputStream inputStream, OutputStream outputStream) {
        checkLicense();
        Assert.notNull(inputStream, "pdf输入空指针");

        try {
            Document doc = new Document(inputStream);
            doc.save(outputStream, DEFAULT_PDF_SAVE_OPTIONS);
        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException("Aspose 工作异常");
        }
    }
}
*/
