import React, { useState } from 'react';

import Button from '@mui/material/Button';
import SendIcon from '@mui/icons-material/Send';
import CancelIcon from '@mui/icons-material/Cancel';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

import {InsertCampana_url} from "../../services/StatusService";
import dayjs from "dayjs";
import {MenuItem} from "@mui/material";

const EditCampana = () => {
    const [nome, setNome] = useState(null);
    const [local, setLocal] = useState(null);
    const [dataRevisao, setDataRevisao] = useState(dayjs());
    const [dataEntrada, setDataEntrada] = useState(dayjs());
    const [horaRodagem, setHoraRodagem] = useState(null);
    const [obs, setObs] = useState(null);
    const [status, setStatus] = useState(null);

    const handleSubmit = (event) => {
        event.preventDefault();
        const dados = {
            nome,
            local,
            dataRevisao: dataRevisao.format('YYYY-MM-DD'),
            dataEntrada: dataEntrada.format('YYYY-MM-DD'),
            horaRodagem,
            obs,
            status,
        };

        const hasEmptyValues = Object.values(dados).some(value => value === null || value === '');
        if (hasEmptyValues) {
            alert('Todos os campos são obrigatórios, favor preencher!!');
            return;
        }

        alert(JSON.stringify(dados))

        fetch(InsertCampana_url(), {
            method: 'POST',
            mode: "cors",
            body: JSON.stringify(dados),
            headers: {
                'Accept': 'application/json, text/plain',
                'Content-Type': 'application/json;charset=UTF-8'
            }

        })
            .then(retorno => {
                window.location.replace("/DadosCampanas");
                console.log(JSON.stringify(dados));
            })
            .catch(retorno_convertido => {
                alert(retorno_convertido + "\n" + JSON.stringify(dados));
                console.log(JSON.stringify(dados));
                console.log(retorno_convertido);
            });
    };

    const handleCancel = (event) => {
        window.location.replace("/DadosCampanas");
    }


    return (
        <Box
            component="form"
            className="form-insertDados"
            sx={{ '& .MuiTextField-root': { m: 1, width: '25ch'} }}
            noValidate
            autoComplete="off"
        >
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="TEsteeeeeee"
                    value={nome}
                    onChange={(event) => setNome(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="Local"
                    value={local}
                    onChange={(event) => setLocal(event.target.value)}
                />
            </div>
            <div>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DatePicker
                        label="DataRevisão"
                        value={dayjs(dataRevisao)}
                        onChange={(newValue) => setDataRevisao(newValue)}
                        renderInput={(params) => <TextField {...params} />}
                    />
                </LocalizationProvider>
            </div>

            <div>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DatePicker
                        label="DataEntrada"
                        value={dayjs(dataEntrada)}
                        onChange={(newValue) => setDataEntrada(newValue)}
                        renderInput={(params) => <TextField {...params} />}
                    />
                </LocalizationProvider>
            </div>
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="HoraRodagem"
                    value={horaRodagem}
                    onChange={(event) => setHoraRodagem(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="Obs"
                    value={obs}
                    onChange={(event) => setObs(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    label="Status"
                    sx={{ m: 1, width: '25ch' }}
                    onChange={(event) => setStatus(parseInt(event.target.value))}
                    select
                >
                    <MenuItem value={0}>Em uso</MenuItem>
                    <MenuItem value={1}>Guardada para uso</MenuItem>
                    <MenuItem value={2}>Manutenção</MenuItem>
                </TextField>
            </div>
            <div>
                <Button variant="contained"
                        color="error"
                        endIcon={<CancelIcon />}
                        onClick={handleCancel}
                        sx={{ marginRight: 2 }}
                >
                    Cancel
                </Button>
                <Button variant="contained"
                        endIcon={<SendIcon />}
                        onClick={handleSubmit}
                >
                    Send
                </Button>
            </div>
        </Box>
    );
};

export default EditCampana;