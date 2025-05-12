package ga.tianyuge.poi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class SourceComparePriceTableReportItemData {
    private int serialNumber;
    private String materialName;
    private String specification;
    private String unit;
    private BigDecimal quantity;

    private List<SourceComparePriceTableReportSupplierPriceData> supplierPriceData;
}