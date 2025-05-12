package ga.tianyuge.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class iTextRegenerate {

    public static void main(String[] args) {
        try {
            // 打开 PDF 文档
            PdfReader reader = new PdfReader("C:\\Users\\sa\\Documents\\origin.pdf");

            // 创建新的 PDF 文档
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, new FileOutputStream("C:\\Users\\sa\\Documents\\reprint_itext.pdf"));
            document.open();

            // 复制每一页到新的 PDF 文档
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                copy.addPage(copy.getImportedPage(reader, i));
            }

            // 关闭文档
            document.close();
            reader.close();

            System.out.println("PDF 文档已重新生成并保存为 output_itext.pdf");

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}