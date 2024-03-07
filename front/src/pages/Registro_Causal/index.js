import '../../App.css';
import './RegistroCausal.css'
import Registro from './Registro';
import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import React  from 'react';

const RegistroPage = () => {
    document.title = 'Cause Registry';
    return(
        <>
            <HeaderComponent/>
            <Registro/>
            <FooterComponent/>
        </>
    )
}

export default RegistroPage;
