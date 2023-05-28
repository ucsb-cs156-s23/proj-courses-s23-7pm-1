package edu.ucsb.cs156.courses.controllers;

 import edu.ucsb.cs156.courses.entities.GradeHistory;
 import edu.ucsb.cs156.courses.entities.UCSBSubject;
 import edu.ucsb.cs156.courses.errors.EntityNotFoundException;
 import edu.ucsb.cs156.courses.repositories.GradeHistoryRepository;
 import edu.ucsb.cs156.courses.repositories.UCSBSubjectRepository;
 import edu.ucsb.cs156.courses.services.CSVToGradeHistoryService;
 import edu.ucsb.cs156.courses.services.UCSBSubjectsService;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import io.swagger.annotations.ApiParam;
 import lombok.extern.slf4j.Slf4j;

 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.ObjectMapper;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.dao.DuplicateKeyException;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.security.access.prepost.PreAuthorize;
 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;

 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RequestPart;

 import org.springframework.web.bind.annotation.RestController;
 import org.springframework.web.multipart.MultipartFile;
 import org.springframework.web.server.ResponseStatusException;

 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.Reader;
 import java.util.ArrayList;
 import java.util.List;

 @Slf4j
 @Api(description = "API for grade history data")
 @RequestMapping("/api/gradehistory")
 @RestController
 public class GradeHistoryController extends ApiController {
     @Autowired
     GradeHistoryRepository gradeHistoryRepository;

     @Autowired
     ObjectMapper mapper;

     @Autowired
     CSVToGradeHistoryService csvToGradeHistoryService;

     @ApiOperation(value = "Get all Grade History")
     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @GetMapping("admin/all")
     public Iterable<GradeHistory> allHistory() {
         Iterable<GradeHistory> gradeHistoryRows = gradeHistoryRepository.findAll();
         return gradeHistoryRows;
     }

     @ApiOperation(value = "Load grade history into database from uploaded CSV")
     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PostMapping(value = "upload", produces = "application/json")
     public ResponseEntity<String> uploadCSV(@RequestPart MultipartFile file) throws IOException{
       log.info("Starting upload CSV");
       try {
         Reader reader = new InputStreamReader(file.getInputStream());
         List<GradeHistory> uploadedRows = csvToGradeHistoryService.parse(reader);
         List<GradeHistory> savedCourse = (List<GradeHistory>) gradeHistoryRepository.saveAll(uploadedRows);
         String body = mapper.writeValueAsString(savedCourse);
         return ResponseEntity.ok().body(body);
       } catch(Exception e){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed CSV", e);
       }
     }


 }