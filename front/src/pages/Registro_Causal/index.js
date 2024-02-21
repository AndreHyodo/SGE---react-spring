import '../../App.css';
import Registro from './Registro';
import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import React  from 'react';

const Home = () => {
    return(
        <>
            <HeaderComponent/>
            <Registro/>
            <FooterComponent/>
        </>
    )
}

export default Home;
