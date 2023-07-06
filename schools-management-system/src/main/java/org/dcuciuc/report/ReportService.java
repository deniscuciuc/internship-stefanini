package org.dcuciuc.report;

import org.dcuciuc.report.impl.wrappers.ExcelReportWrapper;

public interface ReportService {

    void generateExcelReport(ExcelReportWrapper excelReportWrapper);
}
