import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import {ChakraProvider, createStandaloneToast, Heading, Link} from "@chakra-ui/react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import AuthProvider from "./components/context/AuthContext.jsx";
import Login from "./components/login/Login.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import {LinkIcon} from "@chakra-ui/icons";
import {AppContent} from "./components/AppContent.jsx";
import Signup from "./components/signup/Signup.jsx";
import {ResetPasswordForm} from "./components/password/ResetPasswordForm.jsx";
import {ResetPassword} from "./components/password/ResetPassword.jsx";

const {ToastContainer} = createStandaloneToast();

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login/>
    },
    {
        path: "/dashboard",
        element: <ProtectedRoute>
            <Heading>Dashboard</Heading>
            <Link  href={"dashboard/employees"}>Navigate to employees <LinkIcon mx={"2px"} /></Link>
        </ProtectedRoute>
    },
    {
      path: "/signup",
      element: <Signup />
    },
    {
        path: "dashboard/employees",
        element: <ProtectedRoute>
            <AppContent />
        </ProtectedRoute>
    },
    {
        path: "/resetPassword",
        element: <ResetPassword />
    },
    {
        path: "/savePassword",
        element: <h1>Save password</h1>
    }
])

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <ChakraProvider>
            <AuthProvider>
                <RouterProvider router={router}/>
            </AuthProvider>
            <ToastContainer />
        </ChakraProvider>
    </React.StrictMode>
);
