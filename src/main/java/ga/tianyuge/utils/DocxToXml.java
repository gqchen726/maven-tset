package ga.tianyuge.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/06 13:07
 * @Email: guoqing.chen01@hand-china.com
 */
public class DocxToXml {

    public static void main(String[] args) throws IOException, XWPFConverterException {
        //docx文件路径
        String docxPath = "C:\\temp\\test.docx";
        //xml文件路径
        String xmlPath = "C:\\temp\\test.xml";
        //docx文件转换为xml文件
        docxToXml(docxPath, xmlPath);
        //过滤xml文件中的特殊字符
        filterXml(xmlPath);
        //xml文件转换为docx文件
        String path = "C:\\temp";
        XmlToDocx.xmlToDocx(path, "text.xml", "test.html", "test.docx");
    }

    /**
     * docx文件转换为xml文件
     *
     * @param docxPath docx文件路径
     * @param xmlPath  xml文件路径
     * @throws IOException
     * @throws XWPFConverterException
     */
    public static void docxToXml(String docxPath, String xmlPath) throws IOException, XWPFConverterException {
        InputStream in = new FileInputStream(new File(docxPath));
        XWPFDocument document = new XWPFDocument(in);
        XHTMLOptions options = XHTMLOptions.create();
        OutputStream out = new FileOutputStream(new File(xmlPath));
        XHTMLConverter.getInstance().convert(document, out, options);
    }

    /**
     * 过滤xml文件中的特殊字符
     *
     * @param xmlPath xml文件路径
     * @throws IOException
     */
    public static void filterXml(String xmlPath) throws IOException {
        File file = new File(xmlPath);
        InputStream in = new FileInputStream(file);
        StringBuffer sb = new StringBuffer();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = in.read(b)) > 0) {
            sb.append(new String(b, 0, len));
        }
        in.close();
        //过滤特殊字符
        String content = sb.toString();
        Pattern p = Pattern.compile("&#\\d+;");
        Matcher m = p.matcher(content);
        while (m.find()) {
            String str = m.group();
            content = content.replace(str, "");
        }
        OutputStream out = new FileOutputStream(file);
        out.write(content.getBytes("utf-8"));
        out.close();
    }
}
