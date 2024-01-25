import React, {useEffect,useState} from "react";
import {export_causais} from "../../services/StatusService";
import Card from "../../Components/Card/Card"


const CausalAtual = () => {

    const[Causal, setCausal] = useState([])

    useEffect(() => {
        export_causais().then((response) => {
            setCausal(response.data);
        }).catch(error => {
            console.log(error);
        })
    }, [Causal])



    return(Causal)
}

export default CausalAtual;

