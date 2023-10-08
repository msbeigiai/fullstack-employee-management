import {
    Button,
    Modal,
    ModalOverlay,
    useDisclosure,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    Stack, Box, FormLabel, Input, Alert, AlertIcon,
} from "@chakra-ui/react";
import React from "react";
import {Formik, Form, useField} from "formik";
import * as Yup from "yup";
import {updateEmployee} from "../services/client";
import {errorNotification, successNotification} from "../services/notification.js";

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

export const EditEmployeeForm = ({employee, fetchEmployee, initialValues}) => {
    const {isOpen, onOpen, onClose} = useDisclosure();

    const initialRef = React.useRef(null);
    const finalRef = React.useRef(null);

    return (
        <>
            <Button
                variant={"outline"}
                colorScheme="green"
                size={"sm"}
                onClick={onOpen}
            >
                Update Employee
            </Button>
            <Modal
                initialFocusRef={initialRef}
                finalFocusRef={finalRef}
                isOpen={isOpen}
                onClose={onClose}
                size={"xl"}
            >
                <ModalOverlay/>
                <ModalContent>
                    <ModalHeader>Update Employee</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody pb={6}>
                        {/*<VStack spacing={"5"} mb={"5"}>
                            <Image
                                borderRadius={"full"}
                                boxSize={"150px"}
                                objectFit={"cover"}
                                src={customerProfilePictureUrl(customerId)}
                            />
                        </VStack>*/}
                        <Formik
                            initialValues={initialValues}
                            validationSchema={Yup.object({
                                name: Yup.string()
                                    .max(30, "Must be 15 characters or less")
                                    .required("Required"),
                                email: Yup.string()
                                    .email("Invalid email address")
                                    .required("Required"),
                            })}
                            onSubmit={(updatedCustomer, {setSubmitting}) => {
                                setSubmitting(true);
                                updateEmployee(employee.id, updatedCustomer)
                                    .then((res) => {
                                        console.log(res);
                                         successNotification(
                                          "Customer updated",
                                          `${updatedCustomer.name} was successfully updated`
                                        );
                                        fetchEmployee();
                                    })
                                    .catch((err) => {
                                        errorNotification(err.code, err.response.data.message);
                                        console.log(err.code, err.response.data.message);
                                    })
                                    .finally(() => {
                                        setSubmitting(false);
                                    });
                            }}
                        >
                            {({isValid, isSubmitting, dirty}) => (
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
                                            placeholder="Software Engineer"
                                        />
                                        <MyTextInput
                                            label="Phone"
                                            name="phone"
                                            type="text"
                                            placeholder="555-555-5555"
                                        />

                                        <Button
                                            isDisabled={!(isValid && dirty) || isSubmitting}
                                            type="submit"
                                        >
                                            Submit
                                        </Button>
                                    </Stack>
                                </Form>
                            )}
                        </Formik>
                    </ModalBody>
                </ModalContent>
            </Modal>
        </>
    );
};
