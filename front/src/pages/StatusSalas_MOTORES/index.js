import React, {useEffect,useState} from "react";
import {listDados, listStatus, listLastCausais} from "../../services/StatusService";
// import {Card} from "../../Components/Card/Card";
import Card from "../../Components/Card/Card"
import './StatusSalas.module.css'
import {getTimeDiffInMilliseconds} from "util";
import configList from "webpack-config/dist/ConfigList";
// import axios from "axios";



const StatusSalas = () => {

    const[Salas, setSalas] = useState([])
    const [Causais, setCausais] = useState([]); // new state to store causais data
    const [loading, setLoading] = useState(true);


    useEffect(() => {
        listStatus().then((response) => {
            setSalas(response.data);
        }).catch(error => {
            console.log(error);
        })
    }, [Salas])

    useEffect(() => {
        listLastCausais().then((response) => {
            setCausais(response.data);
            setLoading(false); // Set loading to false once data is fetched
        }).catch(error => {
            console.log(error);
        })
    }, [Causais])

    const status = (id, status) => {

        if (status===0){
            console.log(id +  " = " + Causais[id].testCell +" = "  + Causais[id].causal)
            return Causais[id].causal;
        }else if(status===1){
            return "Running";
        }else if(status===2){
            return "Cooling";
        }else if(status===3){
            return "Sala inativa";
        }
    }

    if (loading) {
        return <div>Loading...</div>; // Render a loading indicator
    }

    console.log(Causais[6].causal)


    return(
        <div className="my-2">
            <div className='row row-cols-6 gx-4 gy-2 m-0 p-0'>
                {
                    Salas.map(sala =>
                            <div className={"col-2 col-md-2 col-sm-4 col-xs-6"} key={sala.id}>
                                {/*<Card testCell={sala.testCell} causalParada={causal(sala.time, sala.status, sala.causal)} status={status(sala.causal, sala.status)} status_actual={sala.status} motor={sala.motor} projeto={sala.projeto} teste={sala.teste} eff={sala.eff} paradaAtual={sala.parada_atual} paradaTotal={sala.parada_total} />*/}
                                <Card
                                    testCell={sala.testCell}
                                    status={status(sala.id-1, sala.status)}
                                    status_actual={sala.status}
                                    motor={sala.motor}
                                    projeto={sala.projeto}
                                    teste={sala.teste}
                                    eff={sala.eff}
                                    paradaAtual={sala.parada_atual}
                                    paradaTotal={sala.parada_total} />
                            </div>
                    )
                }
            </div>
        </div>
    )
}

export default StatusSalas;

