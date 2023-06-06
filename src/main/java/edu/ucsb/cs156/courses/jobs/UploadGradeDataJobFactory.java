package edu.ucsb.cs156.courses.jobs;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import edu.ucsb.cs156.courses.repositories.GradeHistoryRepository;
 import edu.ucsb.cs156.courses.services.UCSBGradeHistoryService;
 import lombok.extern.slf4j.Slf4j;

 @Service
 public class UploadGradeDataJobFactory {

     @Autowired
     UCSBGradeHistoryService ucsbGradeHistoryService;

     @Autowired
     GradeHistoryRepository gradeHistoryRepository;

     public UploadGradeDataJob create() {
         return new UploadGradeDataJob(
                 ucsbGradeHistoryService,
                 gradeHistoryRepository);
     }
 }