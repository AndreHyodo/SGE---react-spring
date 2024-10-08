import '../../App.css';
import './RegistroCausal.css'
import Registro from './Registro';
import Login from './Login';
import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import React, {useEffect, useState} from 'react';
import {ChakraProvider} from "@chakra-ui/react";

const RegistroPage = () => {
    document.title = 'Cause Registry';

    const checkAuth = () =>{
        return sessionStorage.getItem('TOKEN_OK')!=undefined;
    }

    const [pageLoaded, setPageLoaded] = useState(false);
    const [autenticado, setAutenticado] = useState(false);

    useEffect(() => {
        setAutenticado(checkAuth());
        setPageLoaded(true);
    }, []);

    return(
        <>
            <ChakraProvider>
                <HeaderComponent/>
                {autenticado ?
                    <Registro/>
                    :
                    pageLoaded ?
                        <Login/>
                        :
                        null
                }

                <FooterComponent/>
                </ChakraProvider>
        </>
    )
}

export default RegistroPage;
