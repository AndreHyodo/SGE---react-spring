import React, {useState} from 'react'
import '../../App.css'
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink} from "reactstrap";
import { Link } from 'react-router-dom';

const HeaderComponent = () => {
    const [collapsed, setCollapsed] = useState(true);

    const toggleNavbar = () => setCollapsed(!collapsed);

    return(
        <div>
            <div>
                <Navbar color="fadded" light>
                    <NavbarToggler onClick={toggleNavbar} className="me-2" />
                    <NavbarBrand href="/" className="me-auto">
                        Stellantis
                    </NavbarBrand>
                    <Collapse isOpen={!collapsed} navbar>
                        <Nav navbar>
                            <NavItem>
                                <a href="/" className="me-auto">Home</a>
                            </NavItem>
                            <NavItem>
                                <Link to="/registro">Registro Causal</Link>
                            </NavItem>
                            <NavItem>
                                {/*<Link to="/dashboard">Dashboard salas</Link>*/}
                                <Link to="https://brainpower.fcagroup.com.br/pbi/powerbi/PWT%20Engineer/PropulsionSystems/testing%20analysis/bi_controle_componentes/Controle%20de%20Componentes_15024?rs:embed=true&rc:toolbar=false">Relatorios das salas</Link>
                            </NavItem>
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        </div>
    )
}

export default HeaderComponent;