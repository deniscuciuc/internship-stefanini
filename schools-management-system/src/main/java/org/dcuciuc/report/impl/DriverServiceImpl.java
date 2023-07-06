package org.dcuciuc.report.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcuciuc.report.DriverService;
import org.dcuciuc.report.impl.enums.GoogleDriveAPIMimeType;
import org.dcuciuc.util.PropertiesReader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.dcuciuc.report.impl.enums.GoogleDriveAPIMimeType.*;

@Service
public class DriverServiceImpl implements DriverService {

    private static final Logger logger = LogManager.getLogger(DriverServiceImpl.class);

    private final String APPLICATION_NAME = "School Management System";
    private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private final String TOKENS_DIRECTORY_PATH = "credentials";
    private final Set<String> SCOPES = DriveScopes.all();
    private final String CREDENTIALS_FILE_PATH = "/credentials/google-drive-api-credentials.json";
    private final NetHttpTransport httpTransport;
    private final Drive drive;
    private PropertiesReader propertiesReader;

    public DriverServiceImpl() {
        propertiesReader = new PropertiesReader();
        try {
            this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            this.drive = new Drive
                    .Builder(httpTransport, jsonFactory, getCredentials(httpTransport))
                    .setApplicationName(APPLICATION_NAME).build();

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String createFolder(String folderName) {
        com.google.api.services.drive.model.File folderMetaData = new com.google.api.services.drive.model.File();
        folderMetaData.setName(folderName);
        folderMetaData.setMimeType(FOLDER.getValue());
        folderMetaData.setParents(Collections.singletonList(getRootFolderId()));

        com.google.api.services.drive.model.File folder = null;
        try {
            folder = drive.files().create(folderMetaData)
                    .setFields("id")
                    .execute();

            logger.info("Folder created with id : " + folder.getId());
        } catch (IOException e) {
            logger.error("Error occurred while creating folder");
        }

        return folder.getId();
    }

    @Override
    public String findFolderIdByName(String folderName) {
        List<com.google.api.services.drive.model.File> files = getAllFilesByNameAndMimeType(folderName, FOLDER);

        String folderId = null;
        if (files != null) {
            if (!files.isEmpty()) {
                folderId = files.get(0).getId();
            }
        }

        return folderId;
    }

    @Override
    public String saveFile(File file, String fileName, String folderId, GoogleDriveAPIMimeType mimeType) {
        com.google.api.services.drive.model.File uploadedFile = null;
        boolean fileExists = getAllFilesByNameAndMimeType(fileName, EXCEL_XLSX) != null;

        if (fileExists) {
            uploadedFile = updateFile(uploadedFile, file, fileName, mimeType);
        } else {
            uploadedFile = createFile(uploadedFile, file, fileName, folderId, mimeType);
        }

        return uploadedFile.getId();
    }

    @Override
    public void deleteFile(String fileId) {
        try {
            drive.files().delete(fileId).execute();
        } catch (IOException e) {
            logger.error("File to delete not found");
        }
    }

    @Override
    public File findFileById(String fileId) {
        return null;
    }

    private String getRootFolderId() {
        return getAllFilesByNameAndMimeType("Reports", FOLDER).get(0).getId();
    }

    private com.google.api.services.drive.model.File updateFile(com.google.api.services.drive.model.File uploadedFile,
                                                                File file, String fileName, GoogleDriveAPIMimeType mimeType) {
        try {
            String fileId = drive.files().get(getAllFilesByNameAndMimeType(fileName, mimeType).get(0).getId()).execute().getId();
            com.google.api.services.drive.model.File fileToSave = new com.google.api.services.drive.model.File();
            fileToSave.setName(fileName);

            FileContent mediaContent = new FileContent(mimeType.getValue(), file);

            uploadedFile = drive.files().update(fileId, fileToSave, mediaContent).execute();

            logger.info("File Updated. " + uploadedFile.getId());
        } catch (IOException e) {
            logger.error("Error occurred while updating file. " + e.getMessage());
        }
        return uploadedFile;
    }

    private List<com.google.api.services.drive.model.File> findAllFiles() {
        FileList fileList = null;
        try {
            fileList = drive.files().list().setFields("files(id, name)").execute();
        } catch (IOException e) {
            logger.error("Files not found");
        }
        return fileList.getFiles();
    }

    private com.google.api.services.drive.model.File createFile(com.google.api.services.drive.model.File uploadedFile,
                                                                File file, String fileName, String folderId, GoogleDriveAPIMimeType mimeType) {
        com.google.api.services.drive.model.File fileToSave = new com.google.api.services.drive.model.File();
        fileToSave.setName(fileName);
        fileToSave.setParents(Collections.singletonList(folderId));

        FileContent fileContent = new FileContent(mimeType.getValue(), file);

        try {
            uploadedFile = drive.files().create(fileToSave, fileContent).setFields("id").execute();

            logger.info("New file created");
        } catch (IOException e) {
            logger.error("Error occurred while creating file. " + e.getMessage());
        }

        return uploadedFile;
    }

    private Credential getCredentials(NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream inputStream = DriverServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);

        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found : " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(inputStream));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(HTTP_TRANSPORT, jsonFactory, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        String username = propertiesReader.readProperty("google.drive.api.username");
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver)
                .authorize(username);

        return credential;
    }

    private List<com.google.api.services.drive.model.File> getAllFilesByNameAndMimeType(String fileName,
                                                                                        GoogleDriveAPIMimeType mimeType) {
        FileList result = null;
        try {
            result = drive.files().list()
                    .setQ("mimeType='" + mimeType.getValue() + "'")
                    .setQ("name = '" + fileName + "'")
                    .setSpaces("drive")
                    .setFields("files(id, name)")
                    .execute();
        } catch (IOException e) {
            logger.error("Not found");
        }

        if (result.getFiles().size() != 0) {
            return new ArrayList<>(result.getFiles());
        } else {
            return null;
        }
    }
}
