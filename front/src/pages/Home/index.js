import '../../App.css';
import StatusSalas from '../StatusSalas_MOTORES';
import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import React, {useState} from 'react';
import logo from '../../img/logoStellantis.png';

const Home = () => {

    document.title = 'Status';
    return(
        <>
            <HeaderComponent/>
            <StatusSalas/>
            <FooterComponent/>
        </>
    )
}

export default Home;
