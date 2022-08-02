import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: guoqing.chen01@hand-china.com 2022-02-16 15:20
 **/

@Slf4j
public class POITest {

    @Test
    public void HSSFTest() {
        try {
            File file = new File("C:/temp/data.xls");
            // 创建文件
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));

            // 创建工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建sheet
            HSSFSheet sheet = workbook.createSheet("Sheet1");
            // 创建行
            HSSFRow row = sheet.createRow(0);
            // 创建单元格
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("订单号");
            row.createCell(2).setCellValue("下单时间");
            row.createCell(3).setCellValue("个数");
            row.createCell(4).setCellValue("单价");
            row.createCell(5).setCellValue("订单金额");
            // 设置行高
            row.setHeightInPoints(25);

            HSSFRow row1 = sheet.createRow(1);
            row1.setHeightInPoints(18);
            row1.createCell(0).setCellValue("1");
            row1.createCell(1).setCellValue("No00001");

            // 日期格式化
            HSSFCellStyle cellStyle2 = workbook.createCellStyle();
            HSSFCreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle2.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
            // 设置列宽
            sheet.setColumnWidth(2, 20 * 256);

            HSSFCell cell2 = row1.createCell(2);
            cell2.setCellStyle(cellStyle2);
            cell2.setCellValue(new Date());

            row1.createCell(3).setCellValue(2.23786413205627584236511234456);

            // 保留两位小数
            HSSFCellStyle cellStyle3 = workbook.createCellStyle();
            cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            HSSFCell cell4 = row1.createCell(4);
            cell4.setCellStyle(cellStyle3);
            cell4.setCellValue(29.5);

            // 货币格式化
            HSSFCellStyle cellStyle4 = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontName("华文行楷");
            font.setFontHeightInPoints((short) 15);
            cellStyle4.setFont(font);

            HSSFCell cell5 = row1.createCell(5);
            cell5.setCellFormula("D2*E2");
            cell5.setCellStyle(cellStyle4);

            // 获取计算公式的值
            HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(workbook);
            cell5 = e.evaluateInCell(cell5);
            System.out.println(cell5.getNumericCellValue());

            workbook.setActiveSheet(0);
            workbook.write(outputStream);

            // 关闭
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void XSSFTest() {
        try {
            File file = new File("C:/temp/data.xlsx");
            // 创建文件
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));

            
            // 创建工作薄
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 创建sheet
            XSSFSheet sheet = workbook.createSheet("sheet1");

            String[] strings = new String[4];
            strings[0] = "常规格式";
            strings[1] = "整数格式";
            strings[2] = "小数格式";
            strings[3] = "日期格式";
            // 创建行
            XSSFRow row = sheet.createRow(0);
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            XSSFCellStyle cellStyle1 = workbook.createCellStyle();
            XSSFCellStyle cellStyle2 = workbook.createCellStyle();
            XSSFCellStyle cellStyle3 = workbook.createCellStyle();
            XSSFCellStyle cellStyle4 = workbook.createCellStyle();

            int index = 0;
            for (String string : strings) {
                // 创建单元格
                XSSFCell cell = row.createCell(index);
                cellStyle.setDataFormat(0);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(string);
                index++;
            }

            XSSFRow row1 = sheet.createRow(1);

            XSSFCell cell = row1.createCell(0);
            cellStyle1.setDataFormat(0);
            cellStyle1.setAlignment(HorizontalAlignment.LEFT);
            cellStyle1.setIndention((short)2);
            cell.setCellStyle(cellStyle1);
            cell.setCellValue("100");

            XSSFCell cell1 = row1.createCell(1);
            cellStyle2.setDataFormat(1);
            cell1.setCellStyle(cellStyle2);
            cell1.setCellValue(100);

            XSSFCell cell2 = row1.createCell(2);
            cellStyle3.setDataFormat(2);
            cell2.setCellStyle(cellStyle3);
            cell2.setCellValue(100);

            XSSFCell cell3 = row1.createCell(3);
            cellStyle4.setDataFormat(22);
            cell3.setCellStyle(cellStyle4);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/m/d hh:mm");
            Date date = new Date();
            String format1 = format.format(date);
            cell3.setCellValue(format1);


            XSSFDataFormat dataFormat = workbook.createDataFormat();

            workbook.setActiveSheet(0);
            workbook.write(outputStream);


            // 关闭
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载环保类物料导入模板
     */
    @Test
    public void downloadEnvMaterialTemplate() throws FileNotFoundException {

        File file = new File("D:\\temp\\downloadEnvMaterialTemplate.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        try (OutputStream outputStream = fileOutputStream; XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("环保类物料导入");
            XSSFRow firstRow = sheet.createRow(0);
            XSSFRow titleRow = sheet.createRow(1);
            // 设置标题行样式
            CellStyle titleRowStyle = workbook.createCellStyle();
            titleRowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            titleRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleRowStyle.setAlignment(HorizontalAlignment.CENTER);
            titleRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleRow.setHeight((short) 600);
            firstRow.setHeight((short) 600);
            // 字体设置
            XSSFFont font = workbook.createFont();
            font.setFontName("微软雅黑");
            font.setFontHeightInPoints((short) 9);
            font.setColor(IndexedColors.BLACK.getIndex());
            // 粗体显示
            font.setBold(true);
            titleRowStyle.setFont(font);
            // 边框
            titleRowStyle.setBorderBottom(BorderStyle.THIN);
            titleRowStyle.setBorderLeft(BorderStyle.THIN);
            titleRowStyle.setBorderRight(BorderStyle.THIN);
            titleRowStyle.setBorderTop(BorderStyle.THIN);
            // 备注行样式
            XSSFCellStyle normalStyle = getFirstRowNormalStyle(workbook);
            XSSFCellStyle specialStyle = getFirstRowSpecialStyle(workbook);

            Map<Integer, String> columnMap = new LinkedHashMap<>();
            // 计划站点编码和物料编码
            columnMap.put(0, "计划站点编码");
            columnMap.put(1, "物料编码");

            XSSFDrawing drawing = sheet.createDrawingPatriarch();

            for (int i = 0; i < columnMap.size(); i++) {
                XSSFCell firstRowCell = firstRow.createCell(i);

                firstRowCell.setCellValue("导入+校验");
                firstRowCell.setCellStyle(specialStyle);

                sheet.setColumnWidth(i, 5000);
                XSSFCell cell = titleRow.createCell(i);
                cell.setCellValue(columnMap.get(i));
                cell.setCellStyle(titleRowStyle);
                if (2 == i) {
                    continue;
                }
            }
            // 设置列样式
            CellStyle columnStyle = workbook.createCellStyle();
            XSSFDataFormat format = workbook.createDataFormat();
            columnStyle.setDataFormat(format.getFormat("@"));
            // 前3列设置为文本格式
            for (int i = 0; i < 3; i++) {
                sheet.setDefaultColumnStyle(i, columnStyle);
            }
            workbook.write(fileOutputStream);

            // 关闭
            workbook.close();
            fileOutputStream.close();


        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new RuntimeException("获取模板异常，" + e.getMessage());
        }
    }

    public XSSFCellStyle getFirstRowNormalStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 9);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    public XSSFCellStyle getFirstRowSpecialStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 9);
        font.setColor(Font.COLOR_RED);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }
}
