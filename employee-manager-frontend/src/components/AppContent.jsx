import {useEffect, useState} from "react";
import {getEmployees} from "../services/client";
import Header from "./Header.jsx";
import {EmployeeCard} from "./EmployeeCard";
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
        console.log("before = " + loading);
        getEmployees()
            .then((res) => {
                setEmployees(res.data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.respone.data.message);
                setLoading(false);
            });
    };

    useEffect(() => {
        fetchEmployees();
    }, []);

    if (loading) {
        console.log("loading...");
        return (
            <Stack>
                <Spinner mt={30} size={"lg"}/>
            </Stack>
        );
    }

    if (err) {
        return <Heading>Ooops! there was an error!: {err}</Heading>;
    }

    if (employees.length <= 0) {
        return (
            <Stack direction={"row"} justify={"center"}>
                <Heading>No employee available</Heading>
            </Stack>
        );
    }

    return (
        <Stack align={"center"}>
            <Header/>
            <Divider/>
            <SimpleGrid columns={3} spacing={10}>
                {employees.map((employee, index) => (
                    <EmployeeCard
                        employee={employee}
                        key={index}
                        fetchEmployee={fetchEmployees}
                    />
                ))}
            </SimpleGrid>
            <Box></Box>
        </Stack>
    );
};
