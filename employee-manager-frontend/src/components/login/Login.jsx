import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {Flex, Heading, Link, Stack} from "@chakra-ui/react";
import LoginForm from "./LoginForm.jsx";

export default function Login () {
    const {employee} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (employee) {
            navigate("/dashboard/employees")
        }
    }, []);

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Heading fontSize={'2xl'} mb={"15"}>Sign in to your account</Heading>
                    <LoginForm/>
                    <Link color={"blue.500"} href={"/signup"}>
                        Don't have an account? Signup now
                    </Link>
                    <Link color={"blue.600"} href={"/resetPassword"} >forgot your password?</Link>
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