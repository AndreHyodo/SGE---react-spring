import React, {useEffect,useState} from "react";
import { listStatus} from "../../services/StatusService";
// import {Card} from "../../Components/Card/Card";
import Card from "../../Components/Card/Card"
import './StatusSalas.module.css'
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

        return(
            <div className="my-2">
                <div className='container-fluid row '>
                    {
                        Salas.map(sala =>
                                <div className="col-2 mb-4">
                                    <Card testCell={sala.testCell} status={sala.status ? "Running" : sala.causal} status_bool={sala.status}/>
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

