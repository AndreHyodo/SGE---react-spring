import React, { useState } from 'react';

import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import Dados_sala from "./DadosSala";

const indexDadosSala = () => {

    return (
        <>
            <HeaderComponent />
            <Dados_sala />
            <FooterComponent />
        </>
    );
};

export default indexDadosSala;