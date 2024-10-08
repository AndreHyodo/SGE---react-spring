import React, {useEffect, useState} from 'react';

import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import Dados_sala from "./DadosSala";
import Login from "../Registro_Causal/Login";
const IndexDadosSala = () => {

    return (
        <>
            <HeaderComponent />
            <Dados_sala/>
            <FooterComponent />
        </>
    );
};

export default IndexDadosSala;