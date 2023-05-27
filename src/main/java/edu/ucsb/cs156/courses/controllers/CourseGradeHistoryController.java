package edu.ucsb.cs156.courses.controllers;

import edu.ucsb.cs156.courses.entities.CourseGradeHistory;
import edu.ucsb.cs156.courses.entities.User;
import edu.ucsb.cs156.courses.errors.EntityNotFoundException;
import edu.ucsb.cs156.courses.models.CurrentUser;

import edu.ucsb.cs156.courses.repositories.CourseGradeHistoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(description = "CourseGradeHistory")
@RequestMapping("/api/coursegradehistory")
@RestController
@Slf4j

public class CourseGradeHistoryController extends ApiController{

    @Autowired
    CourseGradeHistoryRepository courseGradeHistoryRepository;

    @ApiOperation(value = "List all course grade history")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/all")
    public Iterable<CourseGradeHistory> allCourseGradeHistory() {
        Iterable<CourseGradeHistory> courseGradeHistory = courseGradeHistoryRepository.findAll();
        return courseGradeHistory;
    }

    @ApiOperation(value = "Create a new course grade history")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("admin/post")
    public CourseGradeHistory postCourseGradeHistory(
            @ApiParam("quarter") @RequestParam String quarter,
            @ApiParam("courseLevel") @RequestParam String courseLevel,
            @ApiParam("course") @RequestParam String course,
            @ApiParam("instructor") @RequestParam String instructor,
            @ApiParam("gradeGiven") @RequestParam String gradeGiven,
            @ApiParam("sumOfStudentsByGradeGiven") @RequestParam int sumOfStudentsByGradeGiven)
            throws JsonProcessingException {

        CourseGradeHistory courseGradeHistory = new CourseGradeHistory();
        courseGradeHistory.setQuarter(quarter);
        courseGradeHistory.setCourseLevel(courseLevel);
        courseGradeHistory.setCourse(course);
        courseGradeHistory.setInstructor(instructor);
        courseGradeHistory.setGradeGiven(gradeGiven);
        courseGradeHistory.setSumOfStudentsByGradeGiven(sumOfStudentsByGradeGiven);




        CourseGradeHistory savedCourseGradeHistory = courseGradeHistoryRepository.save(courseGradeHistory);

        return savedCourseGradeHistory;
    }

}
