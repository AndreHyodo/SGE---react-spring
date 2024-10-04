import React, { useState } from 'react';
import axios from 'axios';
import './Dados_sala.css';

import Button from '@mui/material/Button';
import SendIcon from '@mui/icons-material/Send';
import CancelIcon from '@mui/icons-material/Cancel';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import {InputLabel, MenuItem, Select} from "@mui/material";

import {InsertDadosSala_url} from "../../services/StatusService";

const Insert_Dados_sala = () => {
    const [testCell, setTestCell] = useState(null);
    const [dina, setDina] = useState(null);
    const [carrinho, setCarrinho] = useState(null);
    const [campana, setCampana] = useState(null);
    const [eixo, setEixo] = useState(null);
    const [data, setData] = useState(new Date().toISOString()); // Current date in YYYY-MM-DD format
    const [hora, setHora] = useState(new Date().toLocaleTimeString()); // Current time in HH:MM:SS format
    const [operador, setOperador] = useState(null);

    const handleSubmit = (event) => {
        event.preventDefault();
        const dados = {
            testCell,
            dina,
            carrinho,
            campana,
            eixo,
            data,
            hora,
            operador,
        };

        const hasEmptyValues = Object.values(dados).some(value => value === null || value === '');
        if (hasEmptyValues) {
            alert('Todos os campos são obrigatórios, favor preencher!!');
            return;
        }

        fetch(InsertDadosSala_url(), {
            method: 'POST',
            mode: "cors",
            body: JSON.stringify(dados),
            headers: {
                'Accept': 'application/json, text/plain',
                'Content-Type': 'application/json;charset=UTF-8'
            }

        })
            .then(retorno => {
                window.location.replace("/DadosSala");
                console.log(JSON.stringify(dados));
            })
            .catch(retorno_convertido => {
                alert(retorno_convertido + "\n" + JSON.stringify(dados));
                console.log(JSON.stringify(dados));
                console.log(retorno_convertido);
            });
    };

    const handleCancel = (event) => {
        window.location.replace("/DadosSala");
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
                <InputLabel id="demo-simple-select-label">TestCell *</InputLabel>
                <Select
                    sx={{width: '24ch', mb:1}}
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    label="TestCell"
                    value={testCell}
                    onChange={(event) => setTestCell(event.target.value)}
                >
                    <MenuItem value={'A01'}>A01</MenuItem>
                    <MenuItem value={'A02'}>A02</MenuItem>
                    <MenuItem value={'A03'}>A03</MenuItem>
                    <MenuItem value={'A04'}>A04</MenuItem>
                    <MenuItem value={'A05'}>A05</MenuItem>
                    <MenuItem value={'A06'}>A06</MenuItem>
                    <MenuItem value={'A07'}>A07</MenuItem>
                    <MenuItem value={'A08'}>A08</MenuItem>
                    <MenuItem value={'A09'}>A09</MenuItem>
                    <MenuItem value={'A10'}>A10</MenuItem>
                    <MenuItem value={'A11'}>A11</MenuItem>
                    <MenuItem value={'A12'}>A12</MenuItem>
                    <MenuItem value={'B01'}>B01</MenuItem>
                    <MenuItem value={'B02'}>B02</MenuItem>
                    <MenuItem value={'B03'}>B03</MenuItem>
                    <MenuItem value={'B04'}>B04</MenuItem>
                    <MenuItem value={'B05'}>B05</MenuItem>
                    <MenuItem value={'B06'}>B06</MenuItem>
                </Select>
            </div>
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="Dinamômetro"
                    value={dina}
                    onChange={(event) => setDina(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="Carrinho"
                    value={carrinho}
                    onChange={(event) => setCarrinho(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="Campana"
                    value={campana}
                    onChange={(event) => setCampana(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="Eixo"
                    value={eixo}
                    onChange={(event) => setEixo(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
                    id="outlined-required"
                    label="Operador"
                    value={operador}
                    onChange={(event) => setOperador(event.target.value)}
                />
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

export default Insert_Dados_sala;