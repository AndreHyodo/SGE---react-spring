import '../../App.css';
import StatusSalas from '../StatusSalas_MOTORES';
import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import React from 'react';

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
