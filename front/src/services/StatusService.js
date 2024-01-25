import axios from "axios";
import {SPM} from "../pages/StatusSalas_MOTORES/index";

// const url_base = 'http://172.28.226.246:8080'
const url_base = 'http://192.168.2.101:8080'

const Status_Api = '/Status/all';
const List_Causais = '/causaisList'


export const listStatus = () => {
    return axios.get(url_base + Status_Api);
}

export function export_causais(SPM){
    const Causais_Api = `/causais/${SPM}`;

    return axios.get(url_base + Causais_Api);
}

export const list_Causais = () => {
    return axios.get(url_base + List_Causais);
}



