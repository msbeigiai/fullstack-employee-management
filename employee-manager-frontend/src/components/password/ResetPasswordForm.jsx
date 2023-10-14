import {Form, Formik, useField} from "formik";
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Stack} from "@chakra-ui/react";
import * as Yup from "yup";
import {login, resetPassword} from "../../services/client.js";
import {useNavigate} from "react-router-dom";
import {errorNotification, successNotification} from "../../services/notification.js";

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
export const ResetPasswordForm = () => {
    const navigate = useNavigate();
    return (
        <>
            <Formik
                validateOnMount={true}
                initialValues={{email: ""}}
                validationSchema={
                    Yup.object({
                        email: Yup.string()
                            .email("must be a valid email")
                            .required("Email is required")
                    })
                }
                onSubmit={(values, {setSubmitting}) => {
                    setSubmitting(true);
                    resetPassword(values).then(() => {
                        navigate("/")
                        console.log("Reset link sent successfully")
                        successNotification(
                            "Reset password",
                            "Password reset successfully, check your email!"
                        )
                    }).catch(err => {
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
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
                                name={"email"}
                                type={"email"}
                                placeholder={"test@example.com"}
                            />
                            <Button
                                isDisabled={(!isValid || isSubmitting)}
                                type={"submit"}
                            >
                                Send Link
                            </Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    )
}












