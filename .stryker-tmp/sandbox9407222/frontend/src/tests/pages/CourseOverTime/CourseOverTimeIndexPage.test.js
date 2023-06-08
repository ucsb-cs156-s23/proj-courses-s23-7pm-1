import { render, screen, waitFor } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import { MemoryRouter } from "react-router-dom";
import axios from "axios";
import AxiosMockAdapter from "axios-mock-adapter";

import CourseOverTimeIndexPage from "main/pages/CourseOverTime/CourseOverTimeIndexPage";
import { apiCurrentUserFixtures } from "fixtures/currentUserFixtures";
import { systemInfoFixtures } from "fixtures/systemInfoFixtures";
import { threeSections } from "fixtures/sectionFixtures";
import { allTheSubjects } from "fixtures/subjectFixtures";
import userEvent from "@testing-library/user-event";


const mockToast = jest.fn();
jest.mock('react-toastify', () => {
    const originalModule = jest.requireActual('react-toastify');
    return {
        __esModule: true,
        ...originalModule,
        toast: (x) => mockToast(x)
    };
});

describe("CourseOverTimeIndexPage tests", () => {

    const axiosMock =new AxiosMockAdapter(axios);
    
    beforeEach(() => {
        axiosMock.resetHistory();
        axiosMock
            .onGet("/api/currentUser")
            .reply(200, apiCurrentUserFixtures.userOnly);
        axiosMock
            .onGet("/api/systemInfo")
            .reply(200, systemInfoFixtures.showingNeither);
    });

    const queryClient = new QueryClient();
    test("renders without crashing", () => {
        render(
        <QueryClientProvider client={queryClient}>
            <MemoryRouter>
                <CourseOverTimeIndexPage />
            </MemoryRouter>
        </QueryClientProvider>
        );
    });

    test("calls UCSB Course over time search api correctly with 3 section response", async () => {
        axiosMock.onGet("/api/UCSBSubjects/all").reply(200, allTheSubjects);
        axiosMock
            .onGet("/api/public/courseovertime/search")
            .reply(200, threeSections);

        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <CourseOverTimeIndexPage />
                </MemoryRouter>
            </QueryClientProvider>
        );

        const selectStartQuarter = screen.getByLabelText("Start Quarter");
        userEvent.selectOptions(selectStartQuarter, "20222");
        const selectEndQuarter = screen.getByLabelText("End Quarter");
        userEvent.selectOptions(selectEndQuarter, "20222");
        const selectSubject = screen.getByLabelText("Subject Area");
        

        const expectedKey = "CourseOverTimeSearch.Subject-option-ANTH";
        await waitFor(() => expect(screen.getByTestId(expectedKey).toBeInTheDocument));
        
        userEvent.selectOptions(selectSubject, "ANTH");
        const enterCourseNumber = screen.getByLabelText("Course Number (Try searching '16' or '130A')")
        userEvent.type(enterCourseNumber, "130A");

        const submitButton = screen.getByText("Submit");
        expect(submitButton).toBeInTheDocument();
        userEvent.click(submitButton);

        axiosMock.resetHistory();

        await waitFor(() => {
            expect(axiosMock.history.get.length).toBeGreaterThanOrEqual(1);
        });

        expect(axiosMock.history.get[0].params).toEqual({
            startQtr: "20222",
            endQtr: "20222",
            subjectArea: "ANTH",
            courseNumber: "130A",
        });

        expect(screen.getByText("ECE 1A")).toBeInTheDocument();
    });
});