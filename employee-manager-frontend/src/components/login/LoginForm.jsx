import {Alert, AlertIcon, Box, Button, FormLabel, Heading, Input, Stack} from "@chakra-ui/react";
import {Form, Formik, useField} from "formik";
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import * as Yup from 'yup';
import {errorNotification} from "../../services/notification.js";


const MyTextInput = ({label, ...props}) => {
    const [field, meta] = useField(props);

    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
}
export default function LoginForm() {
    const {login} = useAuth();
    const navigate = useNavigate();

    return (
        <>
            <Formik
                validateOnMount={true}
                validationSchema={
                    Yup.object({
                        username: Yup.string()
                            .email("must be a valid email")
                            .required("Email is required"),
                        password: Yup.string()
                            .max(20, "Password can not be more that 20 characters")
                            .required("Password is required")
                    })
                }
                initialValues={{username: '', password: ''}}
                onSubmit={(values, {setSubmitting}) => {
                    setSubmitting(true);
                    login(values).then(() => {
                        navigate("/dashboard")
                        console.log("Successfully logged in");
                    }).catch(err => {
                        errorNotification(
                            err.code,
                            err.response.data.message
                        );
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}

            >
                {({isValid, isSubmitting}) => (
                    <Form>
                        <Stack spacing={"17px"}>
                            <MyTextInput
                                label={"Email"}
                                name={"username"}
                                type={"email"}
                                placeholder={"msbeigi83@gmail.com"}
                            />
                            <MyTextInput
                                label={"Password"}
                                name={"password"}
                                type={"password"}
                                placeholder={"Type your password"}
                            />
                            <Button
                                isDisabled={(!isValid || isSubmitting)}
                                type={"submit"}
                            >
                                Login
                            </Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    )
}











