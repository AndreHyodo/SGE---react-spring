import logo from './logo.svg';
import './App.css';
import StatusSalas from './pages/StatusSalas_MOTORES/index.js';
import HeaderComponent from "./Components/Header/HeaderFile";
import FooterComponent from "./Components/Footer/FooterFile";
import React, { Component }  from 'react';

function App() {
  return (
      <>
        <HeaderComponent/>
        <StatusSalas/>
        <FooterComponent/>
      </>
  )
}


export default App;
