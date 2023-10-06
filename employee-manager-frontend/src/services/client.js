import axios from "axios";

export const getEmployees = async () => {
  // eslint-disable-next-line no-useless-catch
  try {
    console.log(`${import.meta.env.VITE_BASE_URL}/api/v1/employees`);
    return await axios.get(`${import.meta.env.VITE_BASE_URL}/api/v1/employees`);
  } catch (e) {
    throw e;
  }
};
