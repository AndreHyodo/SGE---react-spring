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

    return(
        <div className="my-2">
            <div className='container-fluid row m-0 p-0'>
                {
                    Salas.map(sala =>
                            <div className={sala.id<=12 ? "col-2 mb-3": "col-2 mb-2"} key={sala.id}>
                                <Card testCell={sala.testCell} status={sala.status ? "Running" : sala.causal} statusbool={sala.status} motor={sala.motor} projeto={sala.projeto} teste={sala.teste} eff={sala.eff} paradaAtual={sala.parada_atual} paradaTotal={sala.parada_total} />
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

