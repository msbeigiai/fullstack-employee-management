import {
  Box,
  Button,
  ButtonGroup,
  Card,
  CardBody,
  CardFooter,
  Center,
  Divider,
  Heading,
  Image,
  Stack,
  Text,
} from "@chakra-ui/react";
import { EditEmployeeForm } from "./EditEmployeeForm";

export const EmployeeCard = ({ employee }) => {
  return (
    <Card maxW={"xl"}>
      <CardBody>
        <Image src={employee.imageUrl} />
        <Stack mt={"6"} spacing={"3"}>
          <Heading size={"md"}>
            <span>{employee.id}. </span>
            {employee.name}
          </Heading>
          <Text color={"blue.400"} fontSize={"2xl"}>
            {employee.email}
          </Text>
          <Text fontSize={"2xl"}>{employee.jobTitle}</Text>
        </Stack>
        <Text>{employee.phone}</Text>
      </CardBody>
      <Divider />
      <CardFooter>
        <Center>
          <Box w={"full"}>
            <ButtonGroup spacing={1}>
              <Button variant={"solid"} colorScheme="blue" size={"xs"}>
                View Employee
              </Button>
              <Button variant={"ghost"} colorScheme="green" size={"xs"}>
                <EditEmployeeForm employee={employee} />
              </Button>
              <Button variant={"outline"} colorScheme="red" size={"xs"}>
                Delete Employee
              </Button>
            </ButtonGroup>
          </Box>
        </Center>
      </CardFooter>
    </Card>
  );
};
