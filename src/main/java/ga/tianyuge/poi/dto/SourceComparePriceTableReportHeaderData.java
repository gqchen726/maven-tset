package ga.tianyuge.poi.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SourceComparePriceTableReportHeaderData {
    private List<SourceComparePriceTableReportItemData> sourceComparePriceTableReportItemData;
    private List<SourceComparePriceTableReportSummaryData> sourceComparePriceTableReportSummaryData;
}