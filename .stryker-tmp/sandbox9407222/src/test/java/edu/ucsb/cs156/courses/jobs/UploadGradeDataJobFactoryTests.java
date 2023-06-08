package edu.ucsb.cs156.courses.jobs;

 import static org.junit.jupiter.api.Assertions.assertEquals;

 import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
 import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
 import org.springframework.boot.test.mock.mockito.MockBean;

 import edu.ucsb.cs156.courses.repositories.GradeHistoryRepository;
 import edu.ucsb.cs156.courses.services.UCSBGradeHistoryService;

 @RestClientTest(UploadGradeDataJobFactory.class)
 @AutoConfigureDataJpa
 public class UploadGradeDataJobFactoryTests {

     @MockBean
     GradeHistoryRepository gradeHistoryRepository;

     @MockBean
     UCSBGradeHistoryService ucsbGradeHistoryService;

     @Autowired
     UploadGradeDataJobFactory UploadGradeDataJobFactory;

     @Test
     void test_create() throws Exception {

         // Act

         UploadGradeDataJob UploadGradeDataJob = UploadGradeDataJobFactory.create();

         // Assert

         assertEquals(ucsbGradeHistoryService,UploadGradeDataJob.getUcsbGradeHistoryService());
         assertEquals(gradeHistoryRepository,UploadGradeDataJob.getGradeHistoryRepository());
     }
 }
