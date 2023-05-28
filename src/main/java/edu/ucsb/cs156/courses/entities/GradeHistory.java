package edu.ucsb.cs156.courses.entities;

 import javax.persistence.Entity;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 import javax.persistence.UniqueConstraint;
 import javax.persistence.GeneratedValue;

 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;
 import lombok.AllArgsConstructor;
 import lombok.Builder;

 /**
  * GradeHistory - Entity for grade history data.  Each object represents one
  * row from the CSV files located in this repository:
  * <a href="https://github.com/ucsb-cs156/UCSB_Grades.git">https://github.com/ucsb-cs156/UCSB_Grades.git</a>
  * 
  * There is a unique constraint on the combination of  
  * quarter, level, course, instructor, and grade, since we do not want
  * duplicate rows of data for the same course.
  */

 @Data
 @Builder
 @AllArgsConstructor
 @NoArgsConstructor
 @Entity(name = "gradehistory")
 @Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueGradeHistory", columnNames = { "quarter",
  "level","course","instructor","grade" }) })
 public class GradeHistory {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String quarter;
     private String level;
     private String course;
     private String instructor;
     private String grade;
     private int count;
 }