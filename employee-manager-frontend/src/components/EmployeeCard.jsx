import {
    Box,
    Button,
    Card,
    CardBody,
    CardFooter,
    Divider,
    Heading,
    Image,
    Stack,
    Text,
} from "@chakra-ui/react";
import {EditEmployeeForm} from "./EditEmployeeForm";

export const EmployeeCard = ({employee, fetchEmployee}) => {
    return (
        <Card maxW={"xl"}>
            <CardBody>
                <Image src={employee.imageUrl}/>
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
                <Box>
                    {employee.employeeCode}
                </Box>
            </CardBody>
            <CardFooter>
                <Stack direction={"row"} justify={"center"} spacing={3} p={2}>
                    <Stack>
                        <EditEmployeeForm employee={employee} fetchEmployee={fetchEmployee} initialValues={{
                            name: employee.name,
                            email: employee.email,
                            jobTitle: employee.jobTitle,
                            phone: employee.phone,
                            employeeCode: employee.employeeCode
                        }}/>
                    </Stack>
                    <Stack>
                        <Button variant={"outline"} colorScheme="red" size={"sm"}>
                            Delete Employee
                        </Button>
                    </Stack>
                </Stack>
            </CardFooter>
        </Card>
    );
};
