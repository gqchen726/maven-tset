package ga.tianyuge.poi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SourceComparePriceTableReportSummaryData {
    private String supplierName;
    private BigDecimal totalAmount;
    private String paymentTerms;
    private String paymentMethod;
    private String deliveryPeriod;
    private String remarks;
}