import { render} from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "react-query";
import { MemoryRouter } from "react-router-dom";
import axios from "axios";
import AxiosMockAdapter from "axios-mock-adapter";
import { apiCurrentUserFixtures } from "fixtures/currentUserFixtures";
import { systemInfoFixtures } from "fixtures/systemInfoFixtures";
import AdminLastUpdatedPage from "main/pages/AdminLastUpdatedPage";

const mockToast = jest.fn();
jest.mock('react-toastify', () => {
    const originalModule = jest.requireActual('react-toastify');
    return {
        __esModule: true,
        ...originalModule,
        toast: (x) => mockToast(x)
    };
});

describe("AdminLastUpdatedPage tests", () => {
    const axiosMock = new AxiosMockAdapter(axios);
    const setupAdminUser = () => {
        axiosMock.reset();
        axiosMock.resetHistory();
        axiosMock.onGet("/api/currentUser").reply(200, apiCurrentUserFixtures.adminUser);
        axiosMock.onGet("/api/systemInfo").reply(200, systemInfoFixtures.showingNeither);
    };

    test("renders without crashing for admin user", () => {
        setupAdminUser();
        const queryClient = new QueryClient();

        render(
            <QueryClientProvider client={queryClient}>
                <MemoryRouter>
                    <AdminLastUpdatedPage />
                </MemoryRouter>
            </QueryClientProvider>
        );
    });
});
