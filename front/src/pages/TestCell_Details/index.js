import React from "react";
import "./details.css"
import CausaisMes from "./causaisMes";
import FooterComponent from "../../Components/Footer/FooterFile";
import HeaderComponent from "../../Components/Header/HeaderFile";
import {ChakraProvider, ThemeProvider} from "@chakra-ui/react";

function causaisDetails() {
    return (
        <>
            <ChakraProvider>
                <HeaderComponent/>
                <CausaisMes/>
                <FooterComponent/>
            </ChakraProvider>
        </>
    );
}

export default causaisDetails;