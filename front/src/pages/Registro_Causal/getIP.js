import { useState, useEffect } from "react";
import axios from "axios";
import salas from './salas.json'

function GetIP() {
    //creating IP state
    const [ip, setIP] = useState("");
    const [salaDetails, setSalaDetails] = useState(null);

    const getData = async () => {
        const res = await axios.get("https://api.ipify.org/?format=json");
        setIP(res.data.ip);

        // Search for the IP in the salas.json file
        // const matchingSala = salas.find(sala => sala.ip === res.data.ip);
        // setSalaDetails(matchingSala);
    };

    useEffect(() => {
        //passing getData method to the lifecycle method
        getData();
    }, []);



    return (ip);
}

export default GetIP;