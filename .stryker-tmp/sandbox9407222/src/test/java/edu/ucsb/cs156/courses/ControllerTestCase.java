package edu.ucsb.cs156.courses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import edu.ucsb.cs156.courses.services.CurrentUserService;
import edu.ucsb.cs156.courses.services.GrantedAuthoritiesService;
import edu.ucsb.cs156.courses.testconfig.TestConfig;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@ActiveProfiles("test")
@Import(TestConfig.class)
public abstract class ControllerTestCase {
  @Autowired
  public CurrentUserService currentUserService;

  @Autowired
  public GrantedAuthoritiesService grantedAuthoritiesService;

  @Autowired
  public MockMvc mockMvc;

  @Autowired
  public ObjectMapper mapper;

  protected Map<String, Object> responseToJson(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
    String responseString = result.getResponse().getContentAsString();
    return mapper.readValue(responseString, Map.class);
  }
}
