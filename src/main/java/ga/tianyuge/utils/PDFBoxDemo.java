package ga.tianyuge.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFBoxDemo {

    public static void main(String[] args) {
        String pdfFilePath = "path/to/your/document.pdf"; // 替换为你的 PDF 文件路径
        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath));

            // 提取文本内容
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(document);
            System.out.println("提取的文本内容：\n" + text);

            // 获取文档元数据
            PDDocumentInformation info = document.getDocumentInformation();
            System.out.println("\n文档元数据：");
            System.out.println("标题：" + info.getTitle());
            System.out.println("作者：" + info.getAuthor());
            System.out.println("主题：" + info.getSubject());
            System.out.println("创建日期：" + info.getCreationDate());

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}