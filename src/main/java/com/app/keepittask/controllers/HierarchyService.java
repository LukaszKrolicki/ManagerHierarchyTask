package com.app.keepittask.controllers;

import com.app.keepittask.models.Employee;
import com.app.keepittask.services.HierarchyBuildService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/hierarchy")
public class HierarchyService {
    private final HierarchyBuildService buildService;

    public HierarchyService(HierarchyBuildService buildService) {
        this.buildService = buildService;
    }

    @GetMapping("/build")
    public String generateOrgChart(@RequestPart("file") MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("temp", ".xml");
        file.transferTo(tempFile);

        File xmlFile = new File(String.valueOf(tempFile));
        Employee root = buildService.buildHierarchyJSON(xmlFile);

        ObjectMapper mapper = new ObjectMapper();
        File outputFile = new File("output_build.json");
        mapper.writeValue(outputFile, root);
        String jsonHierarchy = mapper.writeValueAsString(root);
        return "Hierarchy build saved to " + outputFile.getAbsolutePath() + "\nHierarchy: " + jsonHierarchy;    }
}

