package ga.tianyuge.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/16 13:14
 * @Email: guoqing.chen01@hand-china.com
 */
public class WordToPdf {
    public static void main(String[] args) {
        //word文件路径
        String wordPath = "C:\\Users\\GuoqingChen01\\Desktop\\temp\\test.docx";
        //pdf文件路径
        String pdfPath = "C:\\Users\\GuoqingChen01\\Desktop\\temp\\test.pdf";
        //调用word2pdf方法
        word2pdf(wordPath, pdfPath);
    }

    /**
     * word转pdf
     * @param wordPath word文件路径
     * @param pdfPath pdf文件路径
     */
    public static void word2pdf(String wordPath, String pdfPath) {
        try {
            // 打开word文件
            FileInputStream in = new FileInputStream(wordPath);
            // 创建文档
            Document document = new Document();
            Font font = FontFactory.getFont("wenquanyi micro hei", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // 建立一个书写器
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(pdfPath)));
            // 打开文档
            document.open();
            // 写入word文件内容
            PdfContentByte cb = writer.getDirectContent();
            PdfReader reader = new PdfReader(in);
            int pages = reader.getNumberOfPages();
            for (int i = 1; i <= pages; i++) {
                document.newPage();
                PdfImportedPage page = writer.getImportedPage(reader, i);
                cb.addTemplate(page, 0, 0);
            }
            // 关闭文档
            document.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}