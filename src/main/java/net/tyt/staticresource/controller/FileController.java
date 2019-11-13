package net.tyt.staticresource.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.tyt.staticresource.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tyt
 */
@RestController
@RequestMapping("/api/files")
@Api(value="Static Resource File Management", tags = {"FileAPI"})
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("")
    @ApiOperation(value = "View a list of files in the folder", response = List.class)
    public List<String> getFiles(@RequestParam String path) {
        return fileService.getFiles(path);
    }


    @PostMapping("{name}")
    @ApiOperation(value = "Upload a file in the folder")
    public void createFile(@RequestParam(value="file", required=true) MultipartFile file ,
                               @RequestParam(value="path", required=true) String path, 
                               @PathVariable String name) throws IOException {
        fileService.createFile(path, name, file.getInputStream());
    }

    @DeleteMapping("{name}")
    @ApiOperation(value = "Delete a file in the folder")
    public void deleteFile(@PathVariable String name,
            @RequestParam(value="path", required=true) String path) {
        fileService.deleteFile(path, name);
    }

    @GetMapping("{name}")
    @ApiOperation(value = "Download a file from the folder")
    public void getFile(@PathVariable String name,
            @RequestParam(value="path", required=true) String path,
            HttpServletResponse response) throws IOException {
        try (InputStream is = fileService.getFile(path, name)) {
                IOUtils.copy(is, response.getOutputStream());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"");
                response.flushBuffer();
        }

    }
}
