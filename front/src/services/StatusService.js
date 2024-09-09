import axios from "axios";
// import {SPM} from "../pages/StatusSalas_MOTORES";

// const url_base = 'http://192.168.15.5:8080' //Ip home-office

const url_base = 'http://localhost:8080'

// const url_base = 'http://172.28.235.25:8080' //Rede teste Fábrica PC André
// const url_base = 'http://172.28.124.61:8080' //Rede oficial

const Status_Api = '/Status/all';
const List_Causais = '/causaisList'
const Registra_causal = '/causais/insertCausal'
const Last_Causais = '/causais/top3'


export const listStatus = () => {
    return axios.get(url_base + Status_Api);
}

export const dadosList = (testCell) => {
    const url = url_base + Status_Api + testCell;
    return axios.get(url);
};

export const registra = () => {
    const url = url_base + Registra_causal;
    return (url);
}

export function export_causais(SPM){
    const Causais_Api = `/causais/${SPM}`;

    return axios.get(url_base + Causais_Api);
}

export const list_Causais = () => {
    return axios.get(url_base + List_Causais);
}

export const last_Causais = (SPM) => {
    return (url_base + `/causais/top3/${SPM}`);
}

export const getStatusTestCell = (SPM) => {
    const Status_api = `/Status/get/${SPM}`;
    return (url_base + Status_api);
}

export function causais_mes(SPM){
    return axios.get(url_base + `/causais/count/${SPM}`);
}

export function eff_hist_turno(SPM,turno){
    return axios.get(url_base + `/dadosEff/top10/${turno}/${SPM}`);
}

export function causais_date(SPM,date){
    return axios.get(url_base + `/causais/count/${SPM}/${date}`);
}

export function causaisHour_date(SPM,date){
    return axios.get(url_base + `/causais/countHour/${SPM}/${date}`);
}

export function Current_Eff(SPM){
    return axios.get(url_base + `/causais/Eff/${SPM}`);
}

export function Verif_User(user,senha){
    console.log("estou executando: " + url_base + `/users/verificaUser/${user}/${senha}`)
    return axios.get(url_base + `/users/verificaUser/${user}/${senha}`)
}

export function fetchDocs(){
    return axios.get(url_base + `/files/`)
}

