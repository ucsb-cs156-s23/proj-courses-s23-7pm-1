package edu.ucsb.cs156.courses.services;

 import java.io.Reader;
 import java.util.List;
 import edu.ucsb.cs156.courses.entities.GradeHistory;

 public interface CSVToGradeHistoryService {
     List<GradeHistory> parse(Reader reader) throws Exception;
 }