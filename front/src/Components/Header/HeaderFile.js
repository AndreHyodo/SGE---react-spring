import React, {useState} from 'react'
import '../../App.css'
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler} from "reactstrap";
import { Link } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import logo from '../../img/logoAutomAH_3.png';
import home from '../../img/icons/home.png';
import registry from '../../img/icons/registry.png';
import app from '../../img/icons/app.png';
import stellantisLogo from '../../img/logoStellantis.png';
import styled from "styled-components";


const HeaderComponent = () => {
    const [collapsed, setCollapsed] = useState(true);

    const toggleNavbar = () => setCollapsed(!collapsed);

    const StyledNavItem = styled.li`
      &:hover {
        padding: 2px;
        background-color: rgba(128, 128, 128, 0.5);
        width: 10vw;
        border-radius: 4px;
      }
    `;

    return(
        <div>
            <div className="w-100">
                <Navbar color="fadded" light className="w-100 justify-content-center">
                    <NavbarToggler onClick={toggleNavbar} className="me-2" />
                    <NavbarBrand href="/" className="me-50 brand w-auto ">
                        Efficiency Management System
                    </NavbarBrand>
                    <img src={logo} alt="" className="logo"/>
                    <Collapse isOpen={!collapsed} navbar>
                        <Nav navbar className="menu-select" id="menu">
                            <StyledNavItem className="navi-tem my-2 ">
                                <img src={home} alt="" className="logo-icon me-auto"/>
                                <a href="/" className="me-auto text-decoration-none text-black p-2 text-start">Home</a>
                            </StyledNavItem>
                            <StyledNavItem className="navi-tem mb-2">
                                <img src={registry} alt="" className="logo-icon me-auto"/>
                                <Link to="/registro" className="text-decoration-none text-black p-2">Registro Causal</Link>
                            </StyledNavItem>
                            <StyledNavItem className="navi-tem mb-2">
                                <img src={registry} alt="" className="logo-icon "/>
                                <Link to="/DadosSala" className="text-decoration-none text-black p-2" target="_blank">Dados Sala</Link>
                            </StyledNavItem>
                            <StyledNavItem className="navi-tem mb-2">
                                <img src={app} alt="" className="logo-icon "/>
                                <Link to="https://apps.powerapps.com/play/e/default-d852d5cd-724c-4128-8812-ffa5db3f8507/a/c254677a-09d2-4366-87ec-61017937b8a4?tenantId=d852d5cd-724c-4128-8812-ffa5db3f8507&hint=28b87822-59ed-472c-80b0-e1cc6ff27d94&sourcetime=1712574618050&hidenavbar=true" className="text-decoration-none text-black p-2" target="_blank">App Tech Dyno</Link>
                            </StyledNavItem>
                            <StyledNavItem className="navi-tem mb-2">
                                <img src={app} alt="" className="logo-icon "/>
                                <Link to="https://apps.powerapps.com/play/e/default-d852d5cd-724c-4128-8812-ffa5db3f8507/a/3ea313f8-db19-454b-94a0-6c6143b8d8ab?tenantId=d852d5cd-724c-4128-8812-ffa5db3f8507&hint=fc2b5f23-898e-4deb-a447-fdd8873dc2d1&sourcetime=1712574527046&hidenavbar=true" className="text-decoration-none text-black p-2" target="_blank">ToolControl</Link>
                            </StyledNavItem>
                            {/*TESTE DE UPLOAD DE ARQUIVOS*/}
                            {/*<StyledNavItem className="navi-tem mb-2">*/}
                            {/*    <img src={registry} alt="" className="logo-icon me-auto"/>*/}
                            {/*    <Link to="/uploadFiles" className="text-decoration-none text-black p-2">Upload Files</Link>*/}
                            {/*</StyledNavItem>*/}
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        </div>
    )
}

export default HeaderComponent;