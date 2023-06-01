package edu.ucsb.cs156.courses.controllers;

import java.util.List;

import edu.ucsb.cs156.courses.collections.ConvertedSectionCollection;
import edu.ucsb.cs156.courses.documents.ConvertedSection;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/public/courseovertime")
public class InstructorClassesOverTimeController {

    private final Logger logger = LoggerFactory.getLogger(InstructorClassesOverTimeController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ConvertedSectionCollection convertedSectionCollection;

    @ApiOperation(value = "Get a list of courses taught by a specific instructor over some period")
    @GetMapping(value = "/instructorsearch", produces = "application/json")
    public ResponseEntity<String> search(
        @ApiParam(
            name = "startQuarter",
            type = "String",
            value = "Starting quarter in yyyyq format, e.g. 20231 for W23, 20232 for S23, etc. (1=Winter, 2=Spring, 3=Summer, 4=Fall)",
            example = "20231",
            required = true
        )
        @RequestParam String startQuarter,
        @ApiParam(
            name =  "endQuarter",
            type = "String",
            value = "Ending quarter in yyyyq format, e.g. 20231 for W23, 20232 for S23, etc. (1=Winter, 2=Spring, 3=Summer, 4=Fall)",
            example = "20231",
            required = true
        )
        @RequestParam String endQuarter,
        @ApiParam(
            name =  "instructor",
            type = "String",
            value = "Instructor name; e.g. 'yang' or 'YANG' or 'YANG T H'",
            example = "YANG",
            required = true
        )
        @RequestParam String instructor
    ) throws JsonProcessingException {
        List<ConvertedSection> courseResults = convertedSectionCollection.findByQuarterRangeAndInstructor(
            startQuarter,
            endQuarter,
            instructor
        );
        String body = mapper.writeValueAsString(courseResults);
        return ResponseEntity.ok().body(body);
    }    
}