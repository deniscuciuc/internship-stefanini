/*
package org.dcuciuc.report.impl;

import org.dcuciuc.report.impl.enums.GoogleDriveAPIMimeType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DriverServiceImplTest {

    private DriverServiceImpl googleServerDriverService;

    @BeforeAll
    void init() {
        googleServerDriverService = new DriverServiceImpl();
    }


    @Test
    void shouldUploadFileToDrive() {
        String folderId = googleServerDriverService.findFolderIdByName("English");

        if (folderId == null) {
            folderId = googleServerDriverService.createFolder("English");
        }

        String fileId = googleServerDriverService.saveFile(new File("C:\\Users\\DCUCIUC\\Documents\\reports\\English\\Anna Ivanovna 9a 1-2023.xlsx"),
                "Anna Ivanovna 9a 1-2023.xlsx", folderId, GoogleDriveAPIMimeType.EXCEL_XLSX);

        // TODO: extract data before deleting and verify
        googleServerDriverService.deleteFile(fileId);
        googleServerDriverService.deleteFile(folderId);
    }

    @Test
    @Disabled
    void shouldReturnFolderIdByNameIfExists() {

    }

    @Test
    @Disabled
    void shouldReturnNullIfFolderWithSuchNameNotFound() {

    }

    @Test
    @Disabled
    void shouldDeleteFileByIdIfExists() {

    }

    @Test
    @Disabled
    void shouldThrowIOExceptionsIfFileToDeleteByIdNotFound() {

    }
}*/
