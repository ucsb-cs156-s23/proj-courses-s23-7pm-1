package edu.ucsb.cs156.courses.repositories;

 import edu.ucsb.cs156.courses.entities.GradeHistory;
 import lombok.extern.slf4j.Slf4j;

 import java.util.ArrayList;
 import java.util.List;

 import org.springframework.data.repository.CrudRepository;
 import org.springframework.stereotype.Repository;

 @Repository
 public interface GradeHistoryRepository extends CrudRepository<GradeHistory, Long> {

 }