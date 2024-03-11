import React, {useEffect,useState} from "react";
import { listStatus} from "../../services/StatusService";

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
            Aqui está na testCell Detail
        </div>
    )
}

export default StatusSalas;

