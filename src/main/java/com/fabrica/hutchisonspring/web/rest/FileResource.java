package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.config.ApplicationProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
public class FileResource {

    private final ApplicationProperties applicationProperties;

    public FileResource(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @GetMapping("/bulk/files")
    public ResponseEntity<Resource> getResourceByUrl(@RequestParam String filename) {
        File file = new File(applicationProperties.getBatchFolder().getPath() + filename);
        try {
            Resource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.TEXT_PLAIN).body(resource);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
