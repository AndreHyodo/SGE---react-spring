// import {export_causais} from "../../services/StatusService";
//
// import React, {useEffect,useState} from "react";
// // import {Card} from "../../Components/Card/Card";
// import Card from "../../Components/Card/Card"
// import './StatusSalas.module.css'
// // import axios from "axios";
//
// import styles from "./StatusSalas.module.css";
// import {set} from "express/lib/application";
//
// export function CausalAtual (statusAtual, salaAtual) {
//
//     if(statusAtual){
//         return 'Running'
//     }else{
//         const[Causal, setCausal] = useState([])
//
//         useEffect(() => {
//             export_causais().then((response) => {
//                 setCausal(response.data);
//             }).catch(error => {
//                 console.log(error);
//             })
//         }, [Causal])
//
//
//
//         return(
//             <div className="my-2">
//                 <div className='container-fluid row '>
//                     {
//                         Salas.map(sala =>
//                             <div className="col-2 mb-4">
//                                 <Card sala={sala.sala} status={sala.status ? "Running" : "Stopped"} status_bool={sala.status} />
//                             </div>
//                         )
//                     }
//                 </div>
//             </div>
//         )
//     }
//
//
// }
//
// export default CausalAtual;
//
//
