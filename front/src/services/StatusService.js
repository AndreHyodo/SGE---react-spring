import axios from "axios";
// import {SPM} from "../pages/StatusSalas_MOTORES";

const url_base = 'http://172.28.243.18:8080'
// const url_base = 'http://192.168.0.167:3000'

// const url_base = 'http://localhost:8080'
// const url_base = 'http://192.168.15.5:8080'


const Status_Api = '/Status/all';
const List_Causais = '/causaisList'


export const listStatus = () => {
    return axios.get(url_base + Status_Api);
}

// export const dadosList = (testCell) => {
//     const url = url_base + Status_Api + testCell;
//     return axios.get(url);
// };



export function export_causais(SPM){
    const Causais_Api = `/causais/${SPM}`;

    return axios.get(url_base + Causais_Api);
}

export const list_Causais = () => {
    return axios.get(url_base + List_Causais);
}


