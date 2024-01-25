import '../../App.css';
import StatusSalas from '../StatusSalas_MOTORES/index';
import HeaderComponent from "../../Components//Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import React, { Component }  from 'react';

const Home = () => {
    return(
        <>
            <HeaderComponent/>
            <StatusSalas/>
            <FooterComponent/>
        </>
    )
}

export default Home;
