import React, {useEffect,useState} from "react";
import { listStatus} from "../../services/StatusService";
// import {Card} from "../../Components/Card/Card";
import Card from "../../Components/Card/Card"
import './StatusSalas.module.css'
import {getTimeDiffInMilliseconds} from "util";
// import axios from "axios";



const StatusSalas = () => {

    const[Salas, setSalas] = useState([])
    // const[Dados, setDados] = useState([])

    useEffect(() => {
        listStatus().then((response) => {
            setSalas(response.data);
        }).catch(error => {
            console.log(error);
        })
    }, [Salas])

    const status = (causal, status) => {

        if (status===0){
            return causal;
        }else if(status===1){
            return "Running";
        }else if(status===2){
            return "Cooling";
        }else if(status===3){
            return "Sala inativa";
        }
    }

    // const causal =(hora, status, causalAtual) => {
    //     const date = new Date();
    //     const hora_atual = date.getTime();
    //     const inicio_parada = new Date(hora).getTime();
    //     const parada_diff = hora_atual - inicio_parada;
    //     if (causalAtual===null || causalAtual===""){
    //         if(status===0 && parada_diff<300000){
    //             console.log("obs: "+ hora + " -> diff = " + hora_atual + " - " + inicio_parada + " = " + parada_diff + " e causal = " + "Aguardando Causal ----1----");
    //             // return "Aguardando causal"
    //         }else{
    //             console.log("obs: "+ hora + " -> diff = " + hora_atual + " - " + inicio_parada + " = " + parada_diff + " e causal = " + "Ausência de operador ----2----");
    //             // return "Ausência de Operador";
    //         }
    //     }else{
    //         console.log("obs: "+ hora + " -> diff = " + hora_atual + " - " + inicio_parada + " = " + parada_diff + " e causal = " + causalAtual + "----3----");
    //         return causalAtual;
    //     }
    //
    // }

    return(
        <div className="my-2">
            <div className='row row-cols-6 gx-4 gy-2 m-0 p-0'>
                {
                    Salas.map(sala =>
                            // <div className={sala.id<=12 ? "col-2 mb-3": "col-2 mb-2"} key={sala.id}>
                            <div className={"col-2 col-md-2 col-sm-4 col-xs-6"} key={sala.id}>
                                {/*<Card testCell={sala.testCell} causalParada={causal(sala.time, sala.status, sala.causal)} status={status(sala.causal, sala.status)} status_actual={sala.status} motor={sala.motor} projeto={sala.projeto} teste={sala.teste} eff={sala.eff} paradaAtual={sala.parada_atual} paradaTotal={sala.parada_total} />*/}
                                <Card testCell={sala.testCell} status={status(sala.causal, sala.status)} status_actual={sala.status} motor={sala.motor} projeto={sala.projeto} teste={sala.teste} eff={sala.eff} paradaAtual={sala.parada_atual} paradaTotal={sala.parada_total} />
                            </div>
                        // Causais.map(causal =>
                        //
                        // )
                    )
                }
            </div>
        </div>
    )
}

export default StatusSalas;

