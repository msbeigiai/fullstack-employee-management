import {
    AlertDialog, AlertDialogBody,
    AlertDialogContent, AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogOverlay,
    Box,
    Button,
    useDisclosure
} from "@chakra-ui/react";
import {useRef} from "react";
import {deleteEmployee} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";

export default function DeleteCustomerDialog({name, id, fetchEmployee}) {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const cancelRef = useRef()

    return (
        <Box>
            <Button
                variant={"outline"}
                colorScheme="red"
                size={"sm"}
                _focus={{
                    bg: "grey.500"
                }}
                onClick={onOpen}
            >
                Delete Employee
            </Button>
            <AlertDialog leastDestructiveRef={cancelRef} isOpen={isOpen} onClose={onClose}>
                <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize={"lg"} fontWeight={"bold"}>
                            Delete Employee?
                        </AlertDialogHeader>
                        <AlertDialogBody>
                            Are you sure you want to delete employee <b>{name}</b>?
                            You won't be able to undo the action afterwards.
                        </AlertDialogBody>
                        <AlertDialogFooter>
                            <Button ref={cancelRef} onClick={onClose}>Cancel</Button>
                            <Button colorScheme={"red"} onClick={() => {
                                deleteEmployee(id)
                                    .then(res => {
                                        console.log(res);
                                        successNotification(
                                            "Employee delete",
                                            `Employee ${name} delete successfully.`
                                        )
                                        fetchEmployee();
                                    }).catch(e => {
                                    console.log(e)
                                    errorNotification(
                                        e.code,
                                        e.response.data.message
                                    );
                                }).finally(() => {
                                    onClose();
                                });
                            }}
                                    ml={"3"}
                            >
                                Delete
                            </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                </AlertDialogOverlay>
            </AlertDialog>
        </Box>
    );
}