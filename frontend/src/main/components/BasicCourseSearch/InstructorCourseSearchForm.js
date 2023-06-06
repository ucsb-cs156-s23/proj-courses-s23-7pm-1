import { useState } from "react";
import { Form, Button, Container, Row, Col } from "react-bootstrap";
import { quarterRange } from "main/utils/quarterUtilities";

import { useSystemInfo } from "main/utils/systemInfo";
import SingleQuarterDropdown from "../Quarters/SingleQuarterDropdown";
import { useBackend  } from "main/utils/useBackend";

const InstructorCourseSearchForm = ({ fetchJSON }) => {

  const { data: systemInfo } = useSystemInfo();

  // Stryker disable OptionalChaining
  const startQtr = systemInfo?.startQtrYYYYQ || "20211";
  const endQtr = systemInfo?.endQtrYYYYQ || "20214";
  // Stryker restore OptionalChaining

  const quarters = quarterRange(startQtr, endQtr);

  // Stryker disable all : not sure how to test/mock local storage
  const localStartQuarter = localStorage.getItem("InstructorCourseSearch.StartQuarter");
  const localEndQuarter = localStorage.getItem("InstructorCourseSearch.EndQuarter");
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
  const [instructor, setInstructor] = useState(localInstructor || "");
    
  const handleSubmit = (event) => {
    event.preventDefault();
    fetchJSON(event, { startQuarter, endQuarter, instructor});
    window.location.reload();
  };

  const handleInstructorOnChange = (event) => {
    const input = event.target.value;
    const lowercaseInput = input.toLowerCase();
    setInstructor(lowercaseInput);
  };

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
        <Form.Group controlId="InstructorCourseSearch.Instructor">
            <Form.Label>Course Instructor </Form.Label>
            <Form.Control onChange={handleInstructorOnChange} defaultValue={instructor} />
        </Form.Group>
        <Row style={{ paddingTop: 10, paddingBottom: 10 }}
          data-testid="submit-button-row">
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