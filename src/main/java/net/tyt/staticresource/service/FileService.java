package net.tyt.staticresource.service;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author tyt
 */
public interface FileService {
    List<String> getFiles(String path);
    List<String> getFolders(String path);
    void createFile(String path, String name, InputStream is);
    void createFolder(String path, String name);
    void deleteFile(String path, String name);
    InputStream getFile(String path, String name);
}