import { Heading, Stack } from "@chakra-ui/react";

export const Header = () => {
  return (
    <Stack spacing={6}>
      <Heading as={"h1"} size={"2xl"} m={4}>
        Employee Management
      </Heading>
    </Stack>
  );
};
