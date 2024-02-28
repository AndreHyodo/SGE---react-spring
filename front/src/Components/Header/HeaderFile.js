import React, {useState} from 'react'
import '../../App.css'
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem} from "reactstrap";
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import logo from '../../img/logoAutomAH.png';
import styled from 'styled-components';

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
            <div className="">
                <Navbar color="fadded" light className="w-100">
                    <NavbarToggler onClick={toggleNavbar} className="me-2" />
                    <NavbarBrand href="/" className="me-auto brand">
                        Stellantis
                    </NavbarBrand>
                    <img src={logo} alt="" className="logo"/>
                    <Collapse isOpen={!collapsed} navbar>
                        <Nav navbar className="" id="menu">
                            <StyledNavItem className="navi-tem">
                                <a href="/" className="me-auto text-decoration-none text-black p-2">Home</a>
                            </StyledNavItem>
                            <StyledNavItem className="navi-tem">
                                <Link to="/registro" className="text-decoration-none text-black p-2">Registro Causal</Link>
                            </StyledNavItem>
                            <StyledNavItem>
                                {/*<Link to="/dashboard">Dashboard salas</Link>*/}
                                <Link to="https://bit.ly/3w1SIEH" className="text-decoration-none text-black p-2">App Tech Dyno</Link>
                            </StyledNavItem>
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        </div>
    )
}

export default HeaderComponent;