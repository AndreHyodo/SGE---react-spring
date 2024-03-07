import { useState, useEffect } from "react";
import axios from "axios";


function GetIP() {
    //creating IP state
    const [ip, setIP] = useState("");


    const getData = async () => {
        const res = await axios.get("https://api.ipify.org/?format=json");
        setIP(res.data.ip);
    };


    useEffect(() => {
        //passing getData method to the lifecycle method
        getData();
    }, []);



    return (ip);
}

export default GetIP;