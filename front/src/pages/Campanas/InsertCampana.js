import React, { useState } from 'react';

import Button from '@mui/material/Button';
import SendIcon from '@mui/icons-material/Send';
import CancelIcon from '@mui/icons-material/Cancel';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

import {InsertCampana_url} from "../../services/StatusService";
import dayjs from "dayjs";
import {IconButton, MenuItem} from "@mui/material";
import {ClearIcon} from "@mui/x-date-pickers";

const InsertCampana = () => {
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
            dataRevisao: dataRevisao ? dataRevisao.format('YYYY-MM-DD') : null,
            dataEntrada: dataEntrada ? dataEntrada.format('YYYY-MM-DD') : null,
            horaRodagem,
            obs,
            status,
        };

        console.log(dados.nome);
        const hasEmptyName = (dados.nome === null || dados.nome === '');
        const hasEmptyStatus = (dados.status === null || dados.status === '');
        if (hasEmptyName || hasEmptyStatus) {
            alert('O dados de nome e status são origatórios!!');
            return;
        }

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

    const clearDataRevisao = () => setDataRevisao(null);
    const clearDataEntrada = () => setDataEntrada(null);


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
                    label="Nome"
                    value={nome}
                    onChange={(event) => setNome(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    id="outlined-required"
                    label="Local"
                    value={local}
                    onChange={(event) => setLocal(event.target.value)}
                />
            </div>
            <div>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Box sx={{ display: 'flex', alignItems: 'center', width: '27ch'}}>
                        <DatePicker
                            label="Data Revisão"
                            value={dataRevisao}
                            onChange={(newValue) => setDataRevisao(newValue)}
                            renderInput={(params) => <TextField {...params} />}
                            sx={{ width: '20ch' }}
                        />
                        <IconButton
                            color="error"
                            aria-label="clear date"
                            onClick={clearDataRevisao}
                        >
                            <ClearIcon />
                        </IconButton>
                    </Box>
                </LocalizationProvider>
            </div>

            <div>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Box sx={{ display: 'flex', alignItems: 'center', width: '27ch'}}>
                        <DatePicker
                            label="Data Entrada"
                            value={dataEntrada}
                            onChange={(newValue) => setDataEntrada(newValue)}
                            renderInput={(params) => <TextField {...params} />}
                            sx={{ width: '20ch' }}
                        />
                        <IconButton
                            color="error"
                            aria-label="clear date"
                            onClick={clearDataEntrada}
                        >
                            <ClearIcon />
                        </IconButton>
                    </Box>
                </LocalizationProvider>
            </div>
            <div>
                <TextField
                    id="outlined-required"
                    label="HoraRodagem"
                    value={horaRodagem}
                    onChange={(event) => setHoraRodagem(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    id="outlined-required"
                    label="Obs"
                    value={obs}
                    onChange={(event) => setObs(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
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

export default InsertCampana;