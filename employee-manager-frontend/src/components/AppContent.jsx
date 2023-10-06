import { useEffect, useState } from "react";
import { getEmployees } from "../services/client";
import { Header } from "./Header";
import { EmployeeCard } from "./EmployeeCard";
import {
  Box,
  Container,
  Flex,
  Grid,
  GridItem,
  SimpleGrid,
  Stack,
} from "@chakra-ui/react";

export const AppContent = () => {
  const [employees, setEmployees] = useState([]);

  const fetchEmployees = () => {
    getEmployees()
      .then((res) => {
        console.log(res.data);
        setEmployees(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  return (
    <Container maxW={"container.xl"}>
      <Header />
      <SimpleGrid columns={3} spacing={10}>
        {employees.map((employee, index) => (
          <EmployeeCard {...employee} key={index} />
        ))}
      </SimpleGrid>
    </Container>
  );
};
