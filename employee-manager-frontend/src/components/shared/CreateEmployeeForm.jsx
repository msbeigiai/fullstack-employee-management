import {Form, Formik, useField} from "formik";
import * as Yup from 'yup';
import {saveEmployee} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Stack} from "@chakra-ui/react";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
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
};


export const CreateEmployeeForm = ({onSuccess}) => {
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    email: '',
                    password: '',
                    jobTitle: '',
                    phone: ''
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(30, 'Must be 15 characters or less')
                        .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required'),
                    password: Yup.string()
                        .min(6, 'Must be at least 6 chars or more.')
                        .max(20, 'Must be 20 characters or less')
                        .required('Required'),
                    jobTitle: Yup.string()
                        .max(100)
                        .required('Required'),
                    phone: Yup.string()
                        .max(20)
                        .required('Required'),
                })}
                onSubmit={(employee, {setSubmitting}) => {
                    setSubmitting(true);
                    saveEmployee(employee)
                        .then(res => {
                            console.log(res)
                            successNotification(
                                "Employee saved",
                                `${employee.name} was successfully saved`
                            )
                            onSuccess(res.headers["authorization"]);
                        }).catch(err => {
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}>
                {({isValid, isSubmitting}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Jane"
                            />
                            <MyTextInput
                                label="Email Address"
                                name="email"
                                type="email"
                                placeholder="jane@formik.com"
                            />
                            <MyTextInput
                                label="Job Title"
                                name="jobTitle"
                                type="text"
                                placeholder="Software engineer"
                            />
                            <MyTextInput
                                label="Phone"
                                name="phone"
                                type="text"
                                placeholder="123-5555-666"
                            />
                            <MyTextInput
                                label="Password"
                                name="password"
                                type="password"
                                placeholder="Pick a secure password"
                            />
                            <Button
                                isDisabled={(!isValid || isSubmitting)}
                                type="submit"
                            >
                                Submit
                            </Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    )
}