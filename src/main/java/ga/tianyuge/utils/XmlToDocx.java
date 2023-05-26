package ga.tianyuge.utils;

import java.io.*;

import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/06 13:10
 * @Email: guoqing.chen01@hand-china.com
 */
public class XmlToDocx {

    public static void main(String[] args) throws IOException {
        String path = "D:\\test\\";
        String file = "test.xml";
        String htmlFile = path + "test.html";
        String docFile = path + "test.docx";
        // 1) 将xml文件转换为html文件
        convertXmlToHtml(path + file, htmlFile);
        // 2) 将html文件转换为docx文件
        convertHtmlToDocx(htmlFile, docFile);
    }

    public static void xmlToDocx(String path, String xmlFileName, String htmlFileName, String docFileName) throws IOException {
        // 1) 将xml文件转换为html文件
        convertXmlToHtml(path + xmlFileName, htmlFileName);
        // 2) 将html文件转换为docx文件
        convertHtmlToDocx(htmlFileName, docFileName);
    }

    /**
     * 将xml文件转换为html文件
     *
     * @param xmlFile
     * @param htmlFile
     * @throws IOException
     */
    public static void convertXmlToHtml(String xmlFile, String htmlFile) throws IOException {
        XWPFDocument document = new XWPFDocument(new FileInputStream(xmlFile));
        XHTMLOptions options = XHTMLOptions.create().indent(4);
        // 导出图片
        File imageFolder = new File(htmlFile.substring(0, htmlFile.lastIndexOf("/")));
        options.setExtractor(new FileImageExtractor(imageFolder));
        // URI resolver
        options.URIResolver(new FileURIResolver(imageFolder));
        OutputStream out = new FileOutputStream(htmlFile);
        XHTMLConverter.getInstance().convert(document, out, options);
    }

    /**
     * 将html文件转换为docx文件
     *
     * @param htmlFile
     * @param docFile
     * @throws IOException
     */
    public static void convertHtmlToDocx(String htmlFile, String docFile) throws IOException {
        InputStream in = new FileInputStream(new File(htmlFile));
        XWPFDocument document = new XWPFDocument(in);
        // 2) 解析XHTML配置（这里设置IURIResolver来设置图片存放的目录）
        File imageFolderFile = new File("D:\\image");
        XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
        options.setExtractor(new FileImageExtractor(imageFolderFile));
        // 3) 将XWPFDocument转换成XHTML
        FileOutputStream out = new FileOutputStream(docFile);
        XHTMLConverter.getInstance().convert(document, out, options);
        document.write(out);
        out.close();
    }
}
