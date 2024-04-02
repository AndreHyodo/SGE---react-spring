import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
// import App from './App';
import Registro from '../../front/src/pages/Registro_Causal/index'
import reportWebVitals from './reportWebVitals';
import Home from './pages/Home/index'
import Detail from './pages/TestCell_Details/index'
// import {render} from "@testing-library/react";

import {
    createBrowserRouter, RouterProvider,
} from "react-router-dom";

// import {
//     BrowserRouter as Router,
//     Routes,
//     Route, Redirect,Navigate
// } from "react-router-dom";

const router = createBrowserRouter([
    {
        path: "*",
        element: <Home />,
    },
     {
         path: "/registro",
         element: <Registro />,
    },
    {
        path: "/detail/:testCell",
        element: <Detail />,
    }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
);


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

