import axios from "axios";
import {addMethod} from "yup";

const URL = import.meta.env.VITE_BASE_URL;

function getAuthConfig() {
    return {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("access_token")}`
        }
    }
}

export const getEmployees = async () => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.get(`${URL}/api/v1/employees/allEmployees`, getAuthConfig())
    } catch (e) {
        throw e;
    }
};

export const updateEmployee = async (id, updatedEmployee) => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.put(`${URL}/api/v1/employees/${id}`, updatedEmployee, getAuthConfig());
    } catch (e) {
        throw e;
    }
};

export async function deleteEmployee(id) {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.delete(`${URL}/api/v1/employees/${id}`, getAuthConfig());
    } catch (e) {
        throw e;
    }
}

export const saveEmployee = async (employee) => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.post(`${URL}/api/v1/employees`, employee)
    } catch (e) {
        throw e;
    }
}

export const changePassword = async (employee) => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.post(`${URL}/api/v1/employee/changePassword`, employee);
    } catch (e) {
        throw e;
    }
}

export const resetPassword = async (email) => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.post(`${URL}/api/v1/employees/resetPassword`, email);
    } catch (e) {
        throw e;
    }
}

export const getPasswordReset = async (token) => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.post(`${URL}/api/v1/employees/savePassword?token=${token}`);
    } catch (e) {
        throw e;
    }
}

export const login = async (usernameAndPassword) => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.post(`${URL}/api/v1/auth/login`, usernameAndPassword);
    } catch (e) {
        throw e;
    }
}
  


















