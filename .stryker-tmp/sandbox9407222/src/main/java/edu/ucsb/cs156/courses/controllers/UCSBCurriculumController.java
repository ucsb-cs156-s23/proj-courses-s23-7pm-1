package edu.ucsb.cs156.courses.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.cs156.courses.repositories.UserRepository;
import edu.ucsb.cs156.courses.services.UCSBCurriculumService;

@RestController
@RequestMapping("/api/public")
public class UCSBCurriculumController {
    private final Logger logger = LoggerFactory.getLogger(UCSBCurriculumController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserRepository userRepository;

    @Autowired
    UCSBCurriculumService ucsbCurriculumService;

    @GetMapping(value = "/basicsearch", produces = "application/json")
    public ResponseEntity<String> basicsearch(@RequestParam String qtr, @RequestParam String dept,
            @RequestParam String level) throws JsonProcessingException {

        String body = ucsbCurriculumService.getJSON(dept, qtr, level);
        
        return ResponseEntity.ok().body(body);
    }      
}
