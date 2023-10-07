import axios from "axios";

const URL = import.meta.env.VITE_BASE_URL;

export const getEmployees = async () => {
  // eslint-disable-next-line no-useless-catch
  try {
    return await axios.get(`${URL}/api/v1/employees`);
  } catch (e) {
    throw e;
  }
};

export const updateEmployee = async (id, updatedEmployee) => {
  // eslint-disable-next-line no-useless-catch
  try {
    return await axios.put(`${URL}/api/v1/employees/${id}`, updatedEmployee);
  } catch (e) {
    throw e;
  }
};
