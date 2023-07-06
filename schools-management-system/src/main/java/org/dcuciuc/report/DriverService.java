package org.dcuciuc.report;

import org.dcuciuc.report.impl.enums.GoogleDriveAPIMimeType;
import org.springframework.stereotype.Service;

import java.io.File;

public interface DriverService {

    String createFolder(String folderName);

    String findFolderIdByName(String folderName);

    String saveFile(File file, String filePath, String folderId, GoogleDriveAPIMimeType mimeType);

    void deleteFile(String fileId);

    File findFileById(String fileId);
}
