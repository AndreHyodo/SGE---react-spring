import React, { useState } from 'react';

import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import Campanas from "./Campanas";

const indexCampanas = () => {

    return (
        <>
            <HeaderComponent />
            <Campanas />
            <FooterComponent />
        </>
    );
};

export default indexCampanas;