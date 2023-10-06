import {
  Button,
  ButtonGroup,
  Card,
  CardBody,
  CardFooter,
  Divider,
  Heading,
  Image,
  Stack,
  Text,
} from "@chakra-ui/react";

export const EmployeeCard = ({ ...employee }) => {
  return (
    <Card maxW={"sm"}>
      <CardBody>
        <Image src={employee.imageUrl} />
        <Stack mt={"6"} spacing={"3"}>
          <Heading size={"md"}>{employee.name}</Heading>
          <Text color={"blue.400"} fontSize={"2xl"}>
            {employee.email}
          </Text>
          <Text fontSize={"2xl"}>{employee.jobTitle}</Text>
        </Stack>
        <Text>{employee.phone}</Text>
      </CardBody>
      <Divider />
      <CardFooter>
        <ButtonGroup spacing={2}>
          <Button variant={"solid"} colorScheme="blue">
            View Employee
          </Button>
          <Button variant={"ghost"} colorScheme="green">
            Edit Employee
          </Button>
        </ButtonGroup>
      </CardFooter>
    </Card>
  );
};
