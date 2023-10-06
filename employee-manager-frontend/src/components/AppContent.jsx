import { useEffect, useState } from "react";
import { getEmployees } from "../services/client";
import { Header } from "./Header";
import { EmployeeCard } from "./EmployeeCard";
import {
  SimpleGrid,
  Stack,
  Divider,
  Spinner,
  Heading,
  Box,
} from "@chakra-ui/react";

export const AppContent = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(false);
  const [err, setError] = useState("");

  const fetchEmployees = () => {
    setLoading(true);
    getEmployees()
      .then((res) => {
        console.log(res.data);
        setEmployees(res.data);
      })
      .catch((err) => {
        setError(err.respone.data.message);
      })
      .finally(setLoading(false));
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  if (loading) {
    console.log("loading.....");
    return <Spinner />;
  }

  if (err) {
    return <Heading>Ooops! there was an error!</Heading>;
  }

  if (employees.length <= 0) {
    return <Heading>No employee available</Heading>;
  }

  return (
    <Stack align={"center"}>
      <Header />
      <Divider />
      <SimpleGrid columns={3} spacing={10}>
        {employees.map((employee, index) => (
          <EmployeeCard
            employee={employee}
            key={index}
            fetchEmployees={fetchEmployees}
          />
        ))}
      </SimpleGrid>
      <Box></Box>
    </Stack>
  );
};
