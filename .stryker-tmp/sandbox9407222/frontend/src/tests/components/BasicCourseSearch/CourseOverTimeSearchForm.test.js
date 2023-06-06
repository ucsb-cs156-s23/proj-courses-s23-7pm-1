import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { toast } from "react-toastify";
import { QueryClient, QueryClientProvider } from "react-query";
import { MemoryRouter } from "react-router-dom";
import { allTheSubjects } from "fixtures/subjectFixtures";

import { apiCurrentUserFixtures } from "fixtures/currentUserFixtures";
import { systemInfoFixtures } from "fixtures/systemInfoFixtures";
import axios from "axios";
import AxiosMockAdapter from "axios-mock-adapter";

import CourseOverTimeSearchForm from "main/components/BasicCourseSearch/CourseOverTimeSearchForm";

jest.mock("react-toastify", () => ({
  toast: jest.fn(),
}));

describe("CourseOverTimeSearchForm tests", () => {

  const axiosMock = new AxiosMockAdapter(axios);

  const queryClient = new QueryClient();
  const addToast = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    jest.spyOn(console, 'error')
    console.error.mockImplementation(() => null);

    axiosMock
      .onGet("/api/currentUser")
      .reply(200, apiCurrentUserFixtures.userOnly);
    axiosMock
      .onGet("/api/systemInfo")
      .reply(200, {
        ...systemInfoFixtures.showingNeither,
        "startQtrYYYYQ": "20201",
        "endQtrYYYYQ": "20214"
      });

    toast.mockReturnValue({
      addToast: addToast,
    });
  });


  test("renders without crashing", () => {
    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm />
        </MemoryRouter>
      </QueryClientProvider>
    );
  });

  test("when I select a start quarter, the state for start quarter changes", () => {
    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm />
        </MemoryRouter>
      </QueryClientProvider>
    );
    const selectStartQuarter = screen.getByLabelText("Start Quarter");
    userEvent.selectOptions(selectStartQuarter, "20201");
    expect(selectStartQuarter.value).toBe("20201");
  });

  test("when I select an end quarter, the state for end quarter changes", () => {
    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm />
        </MemoryRouter>
      </QueryClientProvider>
    );
    const selectEndQuarter = screen.getByLabelText("End Quarter");
    userEvent.selectOptions(selectEndQuarter, "20204");
    expect(selectEndQuarter.value).toBe("20204");
  });

  test("when I select a subject, the state for subject changes", async () => {
    axiosMock.onGet("/api/UCSBSubjects/all").reply(200, allTheSubjects);

    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm />
        </MemoryRouter>
      </QueryClientProvider>
    );

    const expectedKey = "CourseOverTimeSearch.Subject-option-MATH";
    await waitFor(() => expect(screen.getByTestId(expectedKey).toBeInTheDocument));

    const selectSubject = screen.getByLabelText("Subject Area");
    userEvent.selectOptions(selectSubject, "MATH");

    expect(selectSubject.value).toBe("MATH");
  });

  test("when I select a course number without suffix, the state for course number changes", () => {
    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm />
        </MemoryRouter>
      </QueryClientProvider>
    );
    const selectCourseNumber = screen.getByLabelText("Course Number (Try searching '16' or '130A')");
    userEvent.type(selectCourseNumber, "24");
    expect(selectCourseNumber.value).toBe("24");
  });

  test("when I select a course number with suffix, the state for course number changes", () => {
    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm />
        </MemoryRouter>
      </QueryClientProvider>
    );
    const selectCourseNumber = screen.getByLabelText("Course Number (Try searching '16' or '130A')");
    userEvent.type(selectCourseNumber, "130A");
    expect(selectCourseNumber.value).toBe("130A");
  });

  test("when I select a course number without number, the state for course number changes", () => {
    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm />
        </MemoryRouter>
      </QueryClientProvider>
    );
    const selectCourseNumber = screen.getByLabelText("Course Number (Try searching '16' or '130A')");
    userEvent.type(selectCourseNumber, "A");
    expect(selectCourseNumber.value).toBe("A");
  });

  test("when I click submit, the right stuff happens", async () => {
    axiosMock.onGet("/api/UCSBSubjects/all").reply(200, allTheSubjects);
    const sampleReturnValue = {
      sampleKey: "sampleValue",
    };

    const fetchJSONSpy = jest.fn();

    fetchJSONSpy.mockResolvedValue(sampleReturnValue);

    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm fetchJSON={fetchJSONSpy} />
        </MemoryRouter>
      </QueryClientProvider>
    );

    const expectedFields = {
      startQuarter: "20211",
      endQuarter: "20214",
      subject: "CMPSC",
      courseNumber: "130",
      courseSuf: "A"
    };

    const expectedKey = "CourseOverTimeSearch.Subject-option-CMPSC";
    await waitFor(() => expect(screen.getByTestId(expectedKey).toBeInTheDocument));

    const selectStartQuarter = screen.getByLabelText("Start Quarter");
    userEvent.selectOptions(selectStartQuarter, "20211");
    const selectEndQuarter = screen.getByLabelText("End Quarter");
    userEvent.selectOptions(selectEndQuarter, "20214");
    const selectSubject = screen.getByLabelText("Subject Area");
    expect(selectSubject).toBeInTheDocument();
    userEvent.selectOptions(selectSubject, "CMPSC");
    const selectCourseNumber = screen.getByLabelText("Course Number (Try searching '16' or '130A')");
    userEvent.type(selectCourseNumber, "130A");
    const submitButton = screen.getByText("Submit");
    userEvent.click(submitButton);

    await waitFor(() => expect(fetchJSONSpy).toHaveBeenCalledTimes(1));

    expect(fetchJSONSpy).toHaveBeenCalledWith(
      expect.any(Object),
      expectedFields
    );
  });

  test("when I click submit when JSON is EMPTY, setCourse is not called!", async () => {
    axiosMock.onGet("/api/UCSBSubjects/all").reply(200, allTheSubjects);

    const sampleReturnValue = {
      sampleKey: "sampleValue",
      total: 0,
    };

    const fetchJSONSpy = jest.fn();

    fetchJSONSpy.mockResolvedValue(sampleReturnValue);

    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm fetchJSON={fetchJSONSpy} />
        </MemoryRouter>
      </QueryClientProvider>
    );

    const expectedKey = "CourseOverTimeSearch.Subject-option-CMPSC";
    await waitFor(() => expect(screen.getByTestId(expectedKey).toBeInTheDocument));

    const selectStartQuarter = screen.getByLabelText("Start Quarter");
    userEvent.selectOptions(selectStartQuarter, "20204");
    const selectEndQuarter = screen.getByLabelText("End Quarter");
    userEvent.selectOptions(selectEndQuarter, "20204");
    const selectSubject = screen.getByLabelText("Subject Area");
    userEvent.selectOptions(selectSubject, "CMPSC");
    const selectCourseNumber = screen.getByLabelText("Course Number (Try searching '16' or '130A')");
    userEvent.type(selectCourseNumber, "130A");
    const submitButton = screen.getByText("Submit");
    userEvent.click(submitButton);
  });


  test("renders without crashing when fallback values are used", async () => {

    axiosMock
      .onGet("/api/systemInfo")
      .reply(200, {
        "springH2ConsoleEnabled": false,
        "showSwaggerUILink": false,
        "startQtrYYYYQ": null, // use fallback value
        "endQtrYYYYQ": null  // use fallback value
      });

    render(
      <QueryClientProvider client={queryClient}>
        <MemoryRouter>
          <CourseOverTimeSearchForm />
        </MemoryRouter>
      </QueryClientProvider>
    );

    // Make sure the first and last options 
    expect(await screen.findByTestId(/CourseOverTimeSearch.StartQuarter-option-0/)).toHaveValue("20211")
    expect(await screen.findByTestId(/CourseOverTimeSearch.StartQuarter-option-3/)).toHaveValue("20214")

  });

});
