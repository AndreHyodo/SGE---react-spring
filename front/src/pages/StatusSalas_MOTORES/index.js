import React, {useEffect,useState} from "react";
import {listDados, listStatus} from "../../services/StatusService";
// import {Card} from "../../Components/Card/Card";
import Card from "../../Components/Card/Card"
import './StatusSalas.module.css'
import {getTimeDiffInMilliseconds} from "util";
// import axios from "axios";



const StatusSalas = () => {

    const[Salas, setSalas] = useState([])

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

    return(
        <div className="my-2">
            <div className='row row-cols-6 gx-4 gy-2 m-0 p-0'>
                {
                    Salas.map(sala =>
                            <div className={"col-2 col-md-2 col-sm-4 col-xs-6"} key={sala.id}>
                                {/*<Card testCell={sala.testCell} causalParada={causal(sala.time, sala.status, sala.causal)} status={status(sala.causal, sala.status)} status_actual={sala.status} motor={sala.motor} projeto={sala.projeto} teste={sala.teste} eff={sala.eff} paradaAtual={sala.parada_atual} paradaTotal={sala.parada_total} />*/}
                                <Card testCell={sala.testCell} status={status(sala.causal, sala.status)} status_actual={sala.status} motor={sala.motor} projeto={sala.projeto} teste={sala.teste} eff={sala.eff} paradaAtual={sala.parada_atual} paradaTotal={sala.parada_total} />
                            </div>
                    )
                }
            </div>
        </div>
    )
}

export default StatusSalas;

