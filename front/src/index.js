import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Registro from '../../front/src/pages/Registro_Causal/index'
import reportWebVitals from './reportWebVitals';
import Home from './pages/Home/index'
import Detail from './pages/TestCell_Details/index'

import {
    createBrowserRouter, RouterProvider,
} from "react-router-dom";
import UploadFiles from "./pages/uploadFiles";
import InsertIndex from "./pages/Dados_sala/insertIndex";
import IndexDadosSala from "./pages/Dados_sala/IndexDadosSala";
import Campanas from "./pages/Campanas/Campanas";
import IndexCampanas from "./pages/Campanas/indexCampanas";
import IndexInsertCampanas from "./pages/Campanas/indexInsertCampanas";
import IndexEditCampanas from "./pages/Campanas/indexEditCampanas";


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
    },
    {
        path: "/uploadFiles",
        element: <UploadFiles />,
    },
    {
        path: "/insertDadosSala",
        element: <InsertIndex />,
    },
    {
        path: "/DadosSala",
        element: <IndexDadosSala />,
    },
    {
        path: "/DadosCampanas",
        element: <IndexCampanas />,
    },
    {
        path: "/insertCampana",
        element: <IndexInsertCampanas />,
    },
    {
        path: "/editCampana",
        element: <IndexEditCampanas />,
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

