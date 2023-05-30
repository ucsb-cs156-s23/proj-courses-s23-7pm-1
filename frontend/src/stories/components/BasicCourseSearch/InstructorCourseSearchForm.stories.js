import React from "react";

import InstructorCourseSearchForm from "main/components/BasicCourseSearch/InstructorCourseSearchForm";
import { allTheSubjects } from "fixtures/subjectFixtures";
import { systemInfoFixtures } from "fixtures/systemInfoFixtures";

export default {
  title: "components/InstructorCourseSearch/InstructorCourseSearchForm",
  component: InstructorCourseSearchForm,
  parameters: {
    mockData: [
      {
        url: '/api/UCSBSubjects/all',
        method: 'GET',
        status: 200,
        response: allTheSubjects
      },
      {
        url: '/api/systemInfo',
        method: 'GET',
        status: 200,
        response: systemInfoFixtures.showingBothStartAndEndQtr
      },
    ],
  },
};

const Template = (args) => {
  return <InstructorCourseSearchForm {...args} />;
};

export const Default = Template.bind({});

Default.args = {
  submitText: "Create",
  fetchJSON: (_event, data) => {
    console.log("Submit was clicked, data=", data);
  }
};
