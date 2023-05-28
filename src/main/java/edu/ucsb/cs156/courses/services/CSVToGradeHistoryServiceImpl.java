package edu.ucsb.cs156.courses.services;

 import edu.ucsb.cs156.courses.entities.GradeHistory;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.stereotype.Service;

 import java.io.Reader;
 import java.util.ArrayList;
 import java.util.List;

import javax.swing.JPopupMenu.Separator;
import javax.validation.valueextraction.Unwrapping.Skip;

import java.io.FileReader;
 import com.opencsv.CSVReaderBuilder;
 import com.opencsv.CSVReader;
 

 @Slf4j
 @Service
 public class CSVToGradeHistoryServiceImpl implements CSVToGradeHistoryService {

     @Override
     public List<GradeHistory> parse(Reader reader) throws Exception {
         List<GradeHistory> gradeHistoryList = new ArrayList<GradeHistory>();
         log.info("Parsing CSV file with grade history... ");
         CSVReader csvReader = new CSVReader(reader);
         csvReader.skip(1);
         List<String[]> myEntries = csvReader.readAll();
         for (String[] row : myEntries) {
             GradeHistory gradeHistory =  GradeHistory.builder()
             .quarter(row[0])
             .level(row[1])
             .course(row[2])
             .instructor(row[3])
             .grade(row[4])
             .count(Integer.parseInt(row[5]))
             .build();
             log.info("Parsed: " + gradeHistory.toString());
             gradeHistoryList.add(gradeHistory);
         }
         csvReader.close();
         return gradeHistoryList;
     }

 }