package edu.ucsb.cs156.courses.repositories;

import edu.ucsb.cs156.courses.entities.CourseGradeHistory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseGradeHistoryRepository extends CrudRepository<CourseGradeHistory, Long> {
    
}
