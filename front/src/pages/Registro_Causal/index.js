import '../../App.css';
import './RegistroCausal.css'
import Registro from './Registro';
import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import React  from 'react';
import {ChakraProvider} from "@chakra-ui/react";

const RegistroPage = () => {
    document.title = 'Cause Registry';
    return(
        <>
            <ChakraProvider>
                <HeaderComponent/>
                <Registro/>
                <FooterComponent/>
            </ChakraProvider>
        </>
    )
}

export default RegistroPage;
