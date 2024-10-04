import React from 'react';

import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import InsertDadosSala from "./InsertDadosSala";

const insertIndex = () => {

    return (
        <>
            <HeaderComponent />
            <InsertDadosSala />
            <FooterComponent />
        </>
    );
};

export default insertIndex;