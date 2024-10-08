import React, { useState } from 'react';

import HeaderComponent from "../../Components/Header/HeaderFile";
import FooterComponent from "../../Components/Footer/FooterFile";
import InsertCampana from "./InsertCampana";

const indexInsertCampanas = () => {

    return (
        <>
            <HeaderComponent />
            <InsertCampana />
            <FooterComponent />
        </>
    );
};

export default indexInsertCampanas;