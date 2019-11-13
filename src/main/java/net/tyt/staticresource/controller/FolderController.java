package net.tyt.staticresource.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import net.tyt.staticresource.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tyt
 */
@RestController
@RequestMapping("/api/folders")
@Api(value="Static Resource Folder Management", tags = {"FolderAPI"})
public class FolderController {
    @Autowired
    private FileService fileService;

    @GetMapping("")
    @ApiOperation(value = "View a list of subfolders in the folder", response = List.class)
    public List<String> getFolders(@RequestParam String path) {
        return fileService.getFolders(path);
    }

    @DeleteMapping("{name}")
    @ApiOperation(value = "Delete a subfolder in the folder")
    public void deleteFolder(@PathVariable String name,
            @RequestParam("path") String path) {
        fileService.deleteFile(path, name);
    }

    @PostMapping("{name}")
    @ApiOperation(value = "Create a subfolder in the folder")
    public void createFolder(@PathVariable String name,
            @RequestParam("path") String path) {
        fileService.createFolder(path, name);
    }
}
