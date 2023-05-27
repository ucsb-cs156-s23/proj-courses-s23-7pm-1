package edu.ucsb.cs156.courses.repositories;

 import edu.ucsb.cs156.courses.entities.GradeHistory;
 import lombok.extern.slf4j.Slf4j;

 import java.util.ArrayList;
 import java.util.List;

 import org.springframework.data.repository.CrudRepository;
 import org.springframework.stereotype.Repository;

 @Repository
 public interface GradeHistoryRepository extends CrudRepository<GradeHistory, Long> {
     public List<GradeHistory> findByYearAndQuarterAndSubjectAreaAndCourseAndInstructorAndGrade(String year,
             String quarter, String subjectArea, String course, String instructor, String grade);

     public List<GradeHistory> findBySubjectAreaAndCourse(String subjectArea, String course);

     public default List<GradeHistory> upsertAll(List<GradeHistory> gradeHistories) {
         List<GradeHistory> result = new ArrayList<GradeHistory>();
         for (GradeHistory gradeHistory : gradeHistories) {
             List<GradeHistory> query = findByYearAndQuarterAndSubjectAreaAndCourseAndInstructorAndGrade(
                     gradeHistory.getYear(), gradeHistory.getQuarter(), gradeHistory.getSubjectArea(),
                     gradeHistory.getCourse(), gradeHistory.getInstructor(), gradeHistory.getGrade());
             if (query.size() == 0) {
                 gradeHistory = save(gradeHistory);
                 result.add(gradeHistory);
             } else {
                 GradeHistory existing = query.get(0);
                 existing.setCount(gradeHistory.getCount());
                 existing = save(existing);
                 result.add(existing);
             }
         }
         return result;
     }
 }