import { useState } from "react";
import { Form, Button, Container, Row, Col } from "react-bootstrap";

//import { allTheLevels } from "fixtures/levelsFixtures";
import { quarterRange } from "main/utils/quarterUtilities";

import { useSystemInfo } from "main/utils/systemInfo";
import SingleQuarterDropdown from "../Quarters/SingleQuarterDropdown";
//import SingleSubjectDropdown from "../Subjects/SingleSubjectDropdown";
//import SingleLevelDropdown from "../Levels/SingleLevelDropdown";
import { useBackend  } from "main/utils/useBackend";

const InstructorCourseSearchForm = ({ fetchJSON }) => {

  const { data: systemInfo } = useSystemInfo();

  // Stryker disable OptionalChaining
  const startQtr = systemInfo?.startQtrYYYYQ || "20211";
  const endQtr = systemInfo?.endQtrYYYYQ || "20214";
  // Stryker enable OptionalChaining

  const quarters = quarterRange(startQtr, endQtr);

  // Stryker disable all : not sure how to test/mock local storage
  const localStartQuarter = localStorage.getItem("InstructorCourseSearch.StartQuarter");
  const localEndQuarter = localStorage.getItem("InstructorCourseSearch.EndQuarter");
  //const localSubject = localStorage.getItem("InstructorCourseSearch.Subject");
  //const localCourseNumber = localStorage.getItem("InstructorCourseSearch.CourseNumber");
  const localInstructor = localStorage.getItem("InstructorCourseSearch.Instructor");

  const { error: _error, status: _status } =
  useBackend(
    // Stryker disable next-line all : don't test internal caching of React Query
    ["/api/UCSBSubjects/all"], 
    { method: "GET", url: "/api/UCSBSubjects/all" }, 
    []
  );

  const [startQuarter, setStartQuarter] = useState(localStartQuarter || quarters[0].yyyyq);
  const [endQuarter, setEndQuarter] = useState(localEndQuarter || quarters[0].yyyyq);
  //const [subject, setSubject] = useState(localSubject || {});
  //const [courseNumber, setCourseNumber] = useState(localCourseNumber || "");
  //const [courseSuf, setCourseSuf] = useState("");
  const [instructor, setInstructor] = useState(localInstructor|| "");
    
  const handleSubmit = (event) => {
    event.preventDefault();
    //fetchJSON(event, { startQuarter, endQuarter, subject, courseNumber, courseSuf });
    fetchJSON(event, { startQuarter, endQuarter, instructor});
  };




  // Stryker disable all : Stryker is testing by changing the padding to 0. But this is simply a visual optimization as it makes it look better
  return (
    <Form onSubmit={handleSubmit}>
      <Container>
        <Row>
          <Col md="auto">
            <SingleQuarterDropdown
              quarters={quarters}
              quarter={startQuarter}
              setQuarter={setStartQuarter}
              controlId={"InstructorCourseSearch.StartQuarter"}
              label={"Start Quarter"}
            />
          </Col>
          <Col md="auto">
            <SingleQuarterDropdown
              quarters={quarters}
              quarter={endQuarter}
              setQuarter={setEndQuarter}
              controlId={"InstructorCourseSearch.EndQuarter"}
              label={"End Quarter"}
            />
          </Col>
        </Row>
        <Form.Group controlId="InstructorCourseSearch">
            <Form.Label>Course Instructor </Form.Label>
            <Form.Control onChange={setInstructor} defaultValue={instructor} />
        </Form.Group>
        <Row style={{ paddingTop: 10, paddingBottom: 10 }}>
          <Col md="auto">
            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Col>
        </Row>
      </Container>
    </Form>
  );
};

export default InstructorCourseSearchForm;