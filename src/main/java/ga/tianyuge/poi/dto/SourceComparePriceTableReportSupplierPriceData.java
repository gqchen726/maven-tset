package ga.tianyuge.poi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SourceComparePriceTableReportSupplierPriceData {
    private String supplierName;
    private BigDecimal unitPriceTaxIncluded;
    private BigDecimal amountTaxIncluded;
    private BigDecimal unitPriceDiscounted;
}