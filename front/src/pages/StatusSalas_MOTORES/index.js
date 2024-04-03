import React, {useEffect,useState} from "react";
import { listStatus} from "../../services/StatusService";
// import {Card} from "../../Components/Card/Card";
import Card from "../../Components/Card/Card"
import './StatusSalas.module.css'
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

    const causal =(causal) => {
        if(causal === "Aguardando Causal"){
            return "Aguardando Causal"
        }
    }

    return(
        <div className="my-2">
            <div className='container-fluid row m-0 p-0'>
                {
                    Salas.map(sala =>
                            <div className={sala.id<=12 ? "col-2 mb-3": "col-2 mb-2"} key={sala.id}>
                                <Card testCell={sala.testCell} waitTime={causal(sala.causal)} status={status(sala.causal, sala.status)} status_actual={sala.status} motor={sala.motor} projeto={sala.projeto} teste={sala.teste} eff={sala.eff} paradaAtual={sala.parada_atual} paradaTotal={sala.parada_total} />
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

