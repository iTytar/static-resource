package net.tyt.staticresource.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import lombok.extern.java.Log;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author tyt
 */
@Service
@Log
public class FileServiceImpl implements FileService {
    private final File root;
    
    public FileServiceImpl(@Value("${spring.resources.static-locations}") final String locations) {
        root = getRoot(locations);
        log.log(Level.FINE, "init with root '{0}'", root);
    }
    private File getRoot(String ls) {
        for(String l : ls.split(",")) {
            if (l.startsWith("file:")) {
                return new File(l.substring(5));
            }
        }
        throw new IllegalArgumentException("'spring.resources.static-locations' doesn't contain 'file:...' value");
    }

    @Override
    public List<String> getFiles(String path) {
        if (path == null) path = "";
        File p = new File(root,path);
        log.log(Level.FINE, "get files '{0}'", p);
        if (!p.exists()) {
            throw new IllegalArgumentException("path '"+p+"' doesn't exist");
        }
        if (!p.isDirectory()) {
            throw new IllegalArgumentException("path '"+p+"' isn't a folder");
        }
        return Arrays.stream(p.listFiles(ff -> ff.isFile()))
                .map(f -> f.getName())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFolders(String path) {
        File p = new File(root,path);
        log.log(Level.FINE, "get folders '{0}'", p);
        return Arrays.stream(p.listFiles(ff -> ff.isDirectory()))
                .map(f -> f.getName())
                .collect(Collectors.toList());
    }

    @Override
    public void createFile(String path, String name, InputStream is) {
        File f = getLocalFile(path, name); 
        log.log(Level.FINE, "create file '{0}'", f);
        try (FileOutputStream fos = new FileOutputStream(f)) {
            IOUtils.copy(is, fos);
        } catch (IOException ex) {
            throw new IllegalArgumentException("can't create file '"+f+"'", ex);
        }
    }

    @Override
    public void deleteFile(String path, String name) {
        File f = getLocalFile(path, name); 
        log.log(Level.FINE, "delete file '{0}'", f);
        if (!f.delete()) {
            throw new IllegalArgumentException("can't delete file '"+f+"'");
        }
    }

    @Override
    public InputStream getFile(String path, String name) {
        File f = getLocalFile(path, name);
        log.log(Level.FINE, "read file '{0}'", f);
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("can't read file '"+f+"'",ex);
        }
    }
    
    @Override
    public void createFolder(String path, String name) {
        File f = getLocalFile(path, name);
        log.log(Level.FINE, "create folder '{0}'", f);
        if (!f.mkdirs()) {
            throw new IllegalArgumentException("can't create folder '"+f+"'");
        }
    }
    
    private File getLocalFile(String path, String name) {
        return new File(root,path.replace('/', File.separatorChar)+File.separator+name);
    }

}
