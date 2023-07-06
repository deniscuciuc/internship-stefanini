package org.dcuciuc.report.impl.enums;


public enum GoogleDriveAPIMimeType {


    FOLDER("application/vnd.google-apps.folder"),

    EXCEL_XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");


    private final String value;
    GoogleDriveAPIMimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
