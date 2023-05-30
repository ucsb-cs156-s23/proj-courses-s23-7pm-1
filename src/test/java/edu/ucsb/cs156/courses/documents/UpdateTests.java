package edu.ucsb.cs156.courses.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(ObjectMapper.class)
@ContextConfiguration
public class UpdateTests {

    @Autowired
    ObjectMapper mapper;

    // @Test
    // public void test_clone() throws JsonProcessingException, CloneNotSupportedException {
    //    List<Update> update = mapper.readValue(CoursePageFixtures.UPDATE_JSON_MATH5B, 
    //    new TypeReference<List<Update>>() {});

    //    Update update1 = update.get(0);
    //    update1.set_id(new ObjectId());
    //    Update update2 = (Update) update1.clone();
    //    assertEquals(update1, update2);
    // }
}
