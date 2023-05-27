package edu.ucsb.cs156.courses.controllers;

import edu.ucsb.cs156.courses.repositories.UserRepository;
import edu.ucsb.cs156.courses.testconfig.TestConfig;
import edu.ucsb.cs156.courses.ControllerTestCase;
import edu.ucsb.cs156.courses.documents.Course;
import edu.ucsb.cs156.courses.entities.CourseGradeHistory;
import edu.ucsb.cs156.courses.entities.User;
import edu.ucsb.cs156.courses.repositories.CourseGradeHistoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jca.endpoint.GenericMessageEndpointFactory;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebMvcTest(controllers = CourseGradeHistoryController.class)
@Import(TestConfig.class)
@AutoConfigureDataJpa
public class CourseGradeHistoryControllerTests extends ControllerTestCase {

        @MockBean
        CourseGradeHistoryRepository courseGradeHistoryRepository;

        @MockBean
        UserRepository userRepository;

        // Authorization tests for /api/coursegradehistory/admin/all

        @Test
        public void logged_out_admin_cannot_get_all_coursegradehistory() throws Exception {
                mockMvc.perform(get("/api/coursegradehistory/admin/all"))
                                .andExpect(status().is(403)); // logged out admin can't get all
        }

        @WithMockUser(roles = { "ADMIN" })
        @Test
        public void logged_in_admin_can_get_all_coursegradehistory() throws Exception {
                mockMvc.perform(get("/api/coursegradehistory/admin/all"))
                                .andExpect(status().is(200)); // logged in admin can get all
        }

        @WithMockUser(roles = { "ADMIN" })
        @Test
        public void admin_can_get_all_coursegradehistory() throws Exception {

                // arrange

                CourseGradeHistory courseGradeHistory1 = CourseGradeHistory.builder()
                                .quarter("F21")
                                .courseLevel("Undergraduate")
                                .course("ANTH 5")
                                .instructor("GAULIN S J")
                                .gradeGiven("C-")
                                .sumOfStudentsByGradeGiven(26)
                                .build();

                CourseGradeHistory courseGradeHistory2 = CourseGradeHistory.builder()
                                .quarter("S17")
                                .courseLevel("Undergraduate")
                                .course("ANTH 121")
                                .instructor("MCCOOL W C")
                                .gradeGiven("A-")
                                .sumOfStudentsByGradeGiven(8)
                                .build();

                ArrayList<CourseGradeHistory> expectedCourseGradeHistory = new ArrayList<>();
                expectedCourseGradeHistory.addAll(Arrays.asList(courseGradeHistory1, courseGradeHistory2));

                when(courseGradeHistoryRepository.findAll()).thenReturn(expectedCourseGradeHistory);

                // act
                MvcResult response = mockMvc.perform(get("/api/coursegradehistory/admin/all"))
                                .andExpect(status().isOk()).andReturn();

                // assert

                verify(courseGradeHistoryRepository, times(1)).findAll();
                String expectedJson = mapper.writeValueAsString(expectedCourseGradeHistory);
                String responseString = response.getResponse().getContentAsString();
                assertEquals(expectedJson, responseString);
        }

        // Authorization tests for /api/coursegradehistory/post

        @Test
        public void logged_out_admin_cannot_post() throws Exception {
                mockMvc.perform(post("/api/coursegradehistory/admin/post"))
                                .andExpect(status().is(403));
        }

        @WithMockUser(roles = { "USER" })
        @Test
        public void logged_in_regular_users_cannot_post() throws Exception {
                mockMvc.perform(post("/api/coursegradehistory/admin/post"))
                                .andExpect(status().is(403)); // only admins can post
        }

        @WithMockUser(roles = { "ADMIN", "USER" })
        @Test
        public void an_admin_user_can_post_a_new_coursegradehistory() throws Exception {
                // arrange

                CourseGradeHistory courseGradeHistory1 = CourseGradeHistory.builder()
                                .quarter("S22")
                                .courseLevel("Undergraduate")
                                .course("ANTH 3")
                                .instructor("KENNETT D J")
                                .gradeGiven("A-")
                                .sumOfStudentsByGradeGiven(36)
                                .build();

                when(courseGradeHistoryRepository.save(eq(courseGradeHistory1))).thenReturn(courseGradeHistory1);

                // act
                MvcResult response = mockMvc.perform(
                                post("/api/coursegradehistory/admin/post?quarter=S22&courseLevel=Undergraduate&course=ANTH 3&instructor=KENNETT D J&gradeGiven=A-&sumOfStudentsByGradeGiven=36")
                                                .with(csrf()))
                                .andExpect(status().isOk()).andReturn();

                // assert
                verify(courseGradeHistoryRepository, times(1)).save(courseGradeHistory1);
                String expectedJson = mapper.writeValueAsString(courseGradeHistory1);
                String responseString = response.getResponse().getContentAsString();
                assertEquals(expectedJson, responseString);
        }

}
