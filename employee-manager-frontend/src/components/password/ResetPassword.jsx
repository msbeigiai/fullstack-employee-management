import {Flex, Heading, Link, Stack} from "@chakra-ui/react";
import {ResetPasswordForm} from "./ResetPasswordForm.jsx";
import {ChevronLeftIcon} from "@chakra-ui/icons";

export const ResetPassword = () => {

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Heading fontSize={'2xl'} mb={"15"}>Reset your password</Heading>
                    <ResetPasswordForm/>
                    <Link color={"blue.600"} href={"/"}><ChevronLeftIcon />Back to the login page</Link>
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