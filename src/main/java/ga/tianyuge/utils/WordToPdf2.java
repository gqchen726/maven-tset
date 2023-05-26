/*
package ga.tianyuge.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

*/
/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/16 17:39
 * @Email: guoqing.chen01@hand-china.com
 *//*



public class WordToPdf2 {
    public static void main(String[] args) throws IOException, DocumentException {
        //word文件路径
        String wordPath = "D:\\test.docx";
        //pdf文件路径
        String pdfPath = "D:\\test.pdf";
        //创建文档
        Document document = new Document();
        //建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        //打开文件
        document.open();
        //word内容
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(wordPath));
        //关闭文档
        document.close();
    }
}*/
