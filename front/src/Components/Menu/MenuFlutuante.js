import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css';

const MenuFlutuante = () => {
    return (
        <div className="menu-flutuante">
            <ul>
                <li>
                    <Link to="/causais-mes">Causais por Mês</Link>
                </li>
                <li>
                    <Link to="/causais-ano">Causais por Ano</Link>
                </li>
                <li>
                    <Link to="/eficiencia">Eficiência</Link>
                </li>
                <li>
                    <Link to="/outro-topico">Outro Tópico</Link>
                </li>
            </ul>
        </div>
    );
};

export default MenuFlutuante;