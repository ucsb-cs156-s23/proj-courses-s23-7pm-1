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
public class SearchByInstructorController {

    private final Logger logger = LoggerFactory.getLogger(SearchByInstructorController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ConvertedSectionCollection convertedSectionCollection;

    @ApiOperation(value = "Get a list of courses taught by an instructor")
    @GetMapping(value = "/instructorsearch", produces = "application/json")
    public ResponseEntity<String> search
    (
        @ApiParam
        (
            required = true,
            value = "Quarter in YYYYQ format (1=Winter, 2=Spring, 3=Summer, 4=Fall)",
            example = "20232"
        )
        @RequestParam String startQtr,
        @ApiParam
        (
            required = true,
            value = "Quarter in YYYYQ format (1=Winter, 2=Spring, 3=Summer, 4=Fall)",
            example = "20232"
        )
        @RequestParam String endQtr,
        @ApiParam
        (
            required = true,
            value = "Instructor's name e.g. 'CONRAD' or 'CONRAD P T'",
            example = "CONRAD"
        )
        @RequestParam String instructor
    ) 
    throws JsonProcessingException 
    {
        List<ConvertedSection> courseResults = convertedSectionCollection.findByQuarterRangeAndInstructor
        (
            startQtr,
            endQtr,
            instructor
        );
        String body = mapper.writeValueAsString(courseResults);
        return ResponseEntity.ok().body(body);
    }    
}
