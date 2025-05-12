package ga.tianyuge.poi.util;

import ga.tianyuge.poi.dto.SourceComparePriceTableReportHeaderData;
import ga.tianyuge.poi.dto.SourceComparePriceTableReportItemData;
import ga.tianyuge.poi.dto.SourceComparePriceTableReportSummaryData;
import ga.tianyuge.poi.dto.SourceComparePriceTableReportSupplierPriceData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenerateReport {

    public static void main(String[] args) throws IOException {
        // 模拟报表数据
        SourceComparePriceTableReportHeaderData reportData = createMockReportData();

        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("比价表");

        // 定义字体和样式
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        // 设置水平居中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直居中 (可选)
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        CellStyle dataStyle = workbook.createCellStyle();
        // 设置水平居中
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直居中 (可选)
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);

        CellStyle totalStyle = workbook.createCellStyle();
        Font totalFont = workbook.createFont();
        totalFont.setBold(true);
        // 设置水平居中
        totalStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直居中 (可选)
        totalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        totalStyle.setFont(totalFont);
        totalStyle.setBorderBottom(BorderStyle.THIN);
        totalStyle.setBorderTop(BorderStyle.THIN);
        totalStyle.setBorderLeft(BorderStyle.THIN);
        totalStyle.setBorderRight(BorderStyle.THIN);
        // 供应商列表
        List<String> suppliers = new ArrayList<>();
        for (SourceComparePriceTableReportItemData itemData : reportData.getSourceComparePriceTableReportItemData()) {
            for (SourceComparePriceTableReportSupplierPriceData supplierPriceDatum : itemData.getSupplierPriceData()) {
                suppliers.add(supplierPriceDatum.getSupplierName());
            }
        }
        suppliers = suppliers.stream().distinct().collect(Collectors.toList());
        // 创建主标题
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("比价表");
        titleCell.setCellStyle(headerStyle);
        // 合并标题单元格
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 5 + suppliers.size() * 3 - 1);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);

        // 创建表头
        Row headerRow1 = sheet.createRow(1);
        String[] fixedHeaders = {"序号", "物料名称", "规格", "单位", "数量"};
        for (int i = 0; i < fixedHeaders.length; i++) {
            Cell cell = headerRow1.createCell(i);
            cell.setCellValue(fixedHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        // 合并固定表头单元格 (跨两行)
        cellAddresses = new CellRangeAddress(1, 2, 0, 0);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        cellAddresses = new CellRangeAddress(1, 2, 1, 1);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        cellAddresses = new CellRangeAddress(1, 2, 2, 2);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        cellAddresses = new CellRangeAddress(1, 2, 3, 3);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        cellAddresses = new CellRangeAddress(1, 2, 4, 4);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);

        // 创建供应商表头
        for (int i = 0; i < suppliers.size(); i++) {
            Cell cell = headerRow1.createCell(5 + i * 3);
            cell.setCellValue(suppliers.get(i));
            cell.setCellStyle(headerStyle);
            cellAddresses = new CellRangeAddress(1, 1, 5 + i * 3, 5 + (i + 1) * 3 - 1);
            cellRangeAddressList.add(cellAddresses);
            sheet.addMergedRegion(cellAddresses);
        }

        // 创建供应商细分表头
        Row headerRow2 = sheet.createRow(2);
        String[] supplierSubHeaders = {"含税单价(元/KG)", "含税金额(元)", "折算成现货的单价(按年化5%)"};
        int colIndex = 5;
        for (String supplier : suppliers) {
            for (String subHeader : supplierSubHeaders) {
                Cell cell = headerRow2.createCell(colIndex++);
                cell.setCellValue(subHeader);
                cell.setCellStyle(headerStyle);
            }
        }

        // 填充数据
        int rowIndex = 2;
        for (SourceComparePriceTableReportItemData item : reportData.getSourceComparePriceTableReportItemData()) {
            Row dataRow = sheet.createRow(++rowIndex);
            dataRow.createCell(0).setCellValue(item.getSerialNumber());
            dataRow.createCell(1).setCellValue(item.getMaterialName());
            dataRow.createCell(2).setCellValue(item.getSpecification() == null ? "/" : item.getSpecification());
            dataRow.createCell(3).setCellValue(item.getUnit());
            dataRow.createCell(4).setCellValue(item.getQuantity().setScale(2, RoundingMode.HALF_UP).toPlainString());
            Map<String, SourceComparePriceTableReportSupplierPriceData> dataMap = item.getSupplierPriceData().stream().collect(Collectors.toMap(SourceComparePriceTableReportSupplierPriceData::getSupplierName, Function.identity()));

            int supplierColIndex = 5;
            for (String supplier : suppliers) {
                SourceComparePriceTableReportSupplierPriceData price = dataMap.get(supplier);
                if (price != null) {
                    Cell priceCell1 = dataRow.createCell(supplierColIndex++);
                    priceCell1.setCellValue(price.getUnitPriceTaxIncluded().setScale(2, RoundingMode.HALF_UP).toPlainString());
                    priceCell1.setCellStyle(dataStyle);

                    Cell priceCell2 = dataRow.createCell(supplierColIndex++);
                    priceCell2.setCellValue(price.getAmountTaxIncluded().setScale(2, RoundingMode.HALF_UP).toPlainString());
                    priceCell2.setCellStyle(dataStyle);

                    Cell priceCell3 = dataRow.createCell(supplierColIndex++);
                    priceCell3.setCellValue(price.getUnitPriceDiscounted().setScale(2, RoundingMode.HALF_UP).toPlainString());
                    priceCell3.setCellStyle(dataStyle);


                } else {
                    // 如果没有该供应商的数据，跳过
                    supplierColIndex += 3;
                }
            }
            // 设置固定列数据单元格样式
            for (int i = 0; i < 5; i++) {
                dataRow.getCell(i).setCellStyle(dataStyle);
            }
        }

        Map<String, SourceComparePriceTableReportSummaryData> summaryDataMap = reportData.getSourceComparePriceTableReportSummaryData().stream().collect(Collectors.toMap(SourceComparePriceTableReportSummaryData::getSupplierName, Function.identity()));

        // 填充合计
        colIndex = 0;
        Row totalRow = sheet.createRow(++rowIndex);
        Cell totalLabelCell = totalRow.createCell(colIndex);
        totalLabelCell.setCellValue("合计");
        totalLabelCell.setCellStyle(totalStyle);
        // 合并单元格
        cellAddresses = new CellRangeAddress(rowIndex, rowIndex, 0, colIndex+=4);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        // 初始化为第一个供应商的 "含税单价" 列
        for (String supplier : suppliers) {
            // 金额在供应商的第二列
            Cell cell = totalRow.createCell(++colIndex);
            cell.setCellValue(summaryDataMap.get(supplier).getTotalAmount().setScale(2, RoundingMode.HALF_UP).toPlainString());
            cell.setCellStyle(totalStyle);
            // 移动到下一个供应商的价格列
            cellAddresses = new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex += 2);
            cellRangeAddressList.add(cellAddresses);
            sheet.addMergedRegion(cellAddresses);
        }

        // 填充付款条件
        colIndex = 0;
        Row paymentTermsRow = sheet.createRow(++rowIndex);
        Cell paymentTermsRowCell = paymentTermsRow.createCell(colIndex);
        paymentTermsRowCell.setCellValue("付款条件");
        paymentTermsRowCell.setCellStyle(totalStyle);
        // 合并单元格
        cellAddresses = new CellRangeAddress(rowIndex, rowIndex, 0, colIndex+=4);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        for (String supplier : suppliers) {
            // 付款条件在供应商的第二列
            Cell cell = paymentTermsRow.createCell(++colIndex);
            cell.setCellValue(summaryDataMap.get(supplier).getPaymentTerms());
            cell.setCellStyle(totalStyle);
            // 移动到下一个供应商的付款条件列
            cellAddresses = new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex += 2);
            cellRangeAddressList.add(cellAddresses);
            sheet.addMergedRegion(cellAddresses);
        }

        // 填充付款方式
        colIndex = 0;
        Row paymentMethodRow = sheet.createRow(++rowIndex);
        Cell paymentMethodRowCell = paymentMethodRow.createCell(colIndex);
        paymentMethodRowCell.setCellValue("付款方式");
        paymentMethodRowCell.setCellStyle(totalStyle);
        // 合并单元格
        cellAddresses = new CellRangeAddress(rowIndex, rowIndex, 0, colIndex+=4);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        for (String supplier : suppliers) {
            // 付款条件在供应商的第二列
            Cell cell = paymentMethodRow.createCell(++colIndex);
            cell.setCellValue(summaryDataMap.get(supplier).getPaymentMethod());
            cell.setCellStyle(totalStyle);
            // 移动到下一个供应商的付款条件列
            cellAddresses = new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex += 2);
            cellRangeAddressList.add(cellAddresses);
            sheet.addMergedRegion(cellAddresses);
        }

        // 填充交货周期
        colIndex = 0;
        Row deliveryPeriodRow = sheet.createRow(++rowIndex);
        Cell deliveryPeriodRowCell = deliveryPeriodRow.createCell(colIndex);
        deliveryPeriodRowCell.setCellValue("交货周期");
        deliveryPeriodRowCell.setCellStyle(totalStyle);
        // 合并单元格
        cellAddresses = new CellRangeAddress(rowIndex, rowIndex, 0, colIndex+=4);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        for (String supplier : suppliers) {
            // 付款条件在供应商的第二列
            Cell cell = deliveryPeriodRow.createCell(++colIndex);
            cell.setCellValue(summaryDataMap.get(supplier).getDeliveryPeriod());
            cell.setCellStyle(totalStyle);
            // 移动到下一个供应商的付款条件列
            cellAddresses = new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex += 2);
            cellRangeAddressList.add(cellAddresses);
            sheet.addMergedRegion(cellAddresses);
        }

        // 填充交货周期
        colIndex = 0;
        Row remarkRow = sheet.createRow(++rowIndex);
        Cell remarkRowCell = remarkRow.createCell(colIndex);
        remarkRowCell.setCellValue("交货周期");
        remarkRowCell.setCellStyle(totalStyle);
        // 合并单元格
        cellAddresses = new CellRangeAddress(rowIndex, rowIndex, 0, colIndex+=4);
        cellRangeAddressList.add(cellAddresses);
        sheet.addMergedRegion(cellAddresses);
        for (String supplier : suppliers) {
            // 付款条件在供应商的第二列
            Cell cell = remarkRow.createCell(++colIndex);
            cell.setCellValue(summaryDataMap.get(supplier).getDeliveryPeriod());
            cell.setCellStyle(totalStyle);
            // 移动到下一个供应商的付款条件列
            cellAddresses = new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex += 2);
            cellRangeAddressList.add(cellAddresses);
            sheet.addMergedRegion(cellAddresses);
        }

        // 自动调整列宽
        for (int i = 0; i < 5 + suppliers.size() * 3 +5; i++) {
            double width = 10 * 256;
            int ii = (i - 5) % 3;
            if (i == 1) {
                width *=2;
            }
            // 设置列宽
            switch (ii) {
                case 0:
                case 1: {
                    width *= 1.5;
                    break;
                }
                case 2: {
                    width *= 2.5;
                    break;
                }
            }
            sheet.setColumnWidth(i, (int) width);
            // sheet.autoSizeColumn(i);
        }

        // 使用 RegionUtil 设置合并区域的边框
        cellRangeAddressList.forEach(e -> {
            RegionUtil.setBorderBottom(BorderStyle.THIN, e, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, e, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, e, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, e, sheet);
        });


        // 输出到文件
        try (FileOutputStream outputStream = new FileOutputStream("comparison_report.xlsx")) {
            workbook.write(outputStream);
        }
        workbook.close();

        System.out.println("Excel 报表生成成功!");
    }

    private static SourceComparePriceTableReportHeaderData createMockReportData() {
        SourceComparePriceTableReportHeaderData reportData = new SourceComparePriceTableReportHeaderData();
        List<SourceComparePriceTableReportItemData> items = Arrays.asList(
                new SourceComparePriceTableReportItemData(1, "青铜磷铜", "/", "KG", BigDecimal.valueOf(60),
                        Arrays.asList(
                                new SourceComparePriceTableReportSupplierPriceData("无锡辛科电子材料股份有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(1.00)),
                                new SourceComparePriceTableReportSupplierPriceData("苏州晶晟新材料科技有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(1.00)),
                                new SourceComparePriceTableReportSupplierPriceData("晶端光电科技（江苏）有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(0.99))
                        )),
                new SourceComparePriceTableReportItemData(2, "正面LECO钢板", "/", "KG", BigDecimal.valueOf(150),
                        Arrays.asList(
                                new SourceComparePriceTableReportSupplierPriceData("无锡辛科电子材料股份有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(1.00)),
                                new SourceComparePriceTableReportSupplierPriceData("苏州晶晟新材料科技有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(1.00)),
                                new SourceComparePriceTableReportSupplierPriceData("晶端光电科技（江苏）有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(0.99))
                        )),
                new SourceComparePriceTableReportItemData(3, "长边框", "246*30*28.5", "KG", BigDecimal.valueOf(10),
                        Arrays.asList(
                                new SourceComparePriceTableReportSupplierPriceData("无锡辛科电子材料股份有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(1.00)),
                                new SourceComparePriceTableReportSupplierPriceData("苏州晶晟新材料科技有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(1.00)),
                                new SourceComparePriceTableReportSupplierPriceData("晶端光电科技（江苏）有限公司", BigDecimal.valueOf(1.00), BigDecimal.valueOf(60.00), BigDecimal.valueOf(0.99))
                        ))
        );
        reportData.setSourceComparePriceTableReportItemData(items);

        List<SourceComparePriceTableReportSummaryData> summaryDataList = new ArrayList<>();
        SourceComparePriceTableReportSummaryData summary = new SourceComparePriceTableReportSummaryData();
        summary.setTotalAmount(BigDecimal.valueOf(3196.20));
        summary.setSupplierName("无锡辛科电子材料股份有限公司");
        summary.setPaymentTerms("100%预付");
        summary.setPaymentMethod("电汇或承兑");
        summary.setDeliveryPeriod("确认收到订单5天");
        summary.setRemarks("...");
        summaryDataList.add(summary);
        summary = new SourceComparePriceTableReportSummaryData();
        summary.setTotalAmount(BigDecimal.valueOf(3196.20));
        summary.setSupplierName("苏州晶晟新材料科技有限公司");
        summary.setPaymentTerms("100%预付");
        summary.setPaymentMethod("电汇或承兑");
        summary.setDeliveryPeriod("确认收到订单5天");
        summary.setRemarks("...");
        summaryDataList.add(summary);
        summary = new SourceComparePriceTableReportSummaryData();
        summary.setTotalAmount(BigDecimal.valueOf(3196.20));
        summary.setSupplierName("晶端光电科技（江苏）有限公司");
        summary.setPaymentTerms("100%预付");
        summary.setPaymentMethod("电汇或承兑");
        summary.setDeliveryPeriod("确认收到订单5天");
        summary.setRemarks("...");
        summaryDataList.add(summary);
        reportData.setSourceComparePriceTableReportSummaryData(summaryDataList);
        return reportData;
    }

    private SourceComparePriceTableReportHeaderData queryReportData(Long rfxHeaderId) {
        SourceComparePriceTableReportHeaderData reportData = new SourceComparePriceTableReportHeaderData();
        // 查询物料数据
        List<SourceComparePriceTableReportItemData> itemDataList = new ArrayList<>();
        reportData.setSourceComparePriceTableReportItemData(itemDataList);
        // 查询供应商数据
        itemDataList.forEach(e -> {
            List<SourceComparePriceTableReportSupplierPriceData> supplierPriceDataList = new ArrayList<>();
            e.setSupplierPriceData(supplierPriceDataList);
        });
        // 查询汇总数据
        List<SourceComparePriceTableReportSummaryData> summaryDataList = new ArrayList<>();
        reportData.setSourceComparePriceTableReportSummaryData(summaryDataList);
        return reportData;
    }
}

