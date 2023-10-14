import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {Flex, Heading, Link, Stack} from "@chakra-ui/react";
import {CreateEmployeeForm} from "../shared/CreateEmployeeForm.jsx";

export default function Signup() {
    const {employee, setEmployeeFromToken} = useAuth();
    const navigate = useNavigate()

    useEffect(() => {
        if (employee) {
            navigate("/dashboard/employees")
        }
    }, []);

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Heading fontSize={'2xl'} mb={"15"}>Register for an account</Heading>
                    <CreateEmployeeForm onSuccess={(token) => {
                        localStorage.getItem("access_token", token);
                        setEmployeeFromToken();
                        navigate("/dashboard")
                    }}/>
                    <Link color={"blue.500"} href={"/"}>
                        Have an account? Login now.
                    </Link>
                </Stack>
            </Flex>
            <Flex
                flex={1}
                p={10}
                flexDirection={"column"}
                alignItems={"center"}
                justifyItems={"center"}
                justifyContent={"center"}
                bgGradient='linear(to-r, green.200, pink.500)'
            >
            </Flex>
        </Stack>
    )


}