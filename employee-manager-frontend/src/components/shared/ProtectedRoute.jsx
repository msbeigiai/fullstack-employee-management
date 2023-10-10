import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const ProtectedRoute = ({children}) => {
    const {isEmployeeAuthenticated} = useAuth()
    const navigate = useNavigate();

    useEffect(() => {
        if (!isEmployeeAuthenticated()) {
            navigate("/")
        }
    });

    return isEmployeeAuthenticated() ? children : "";
}

export default ProtectedRoute;