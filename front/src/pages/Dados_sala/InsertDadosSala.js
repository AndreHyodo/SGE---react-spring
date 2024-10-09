import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Dados_sala.css';

import Button from '@mui/material/Button';
import SendIcon from '@mui/icons-material/Send';
import CancelIcon from '@mui/icons-material/Cancel';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { InputLabel, MenuItem, Select, FormControl, CircularProgress, FormHelperText } from "@mui/material";

import { InsertDadosSala_url, Campanas_url } from "../../services/StatusService";

const Insert_Dados_sala = () => {
    const [testCell, setTestCell] = useState('');
    const [dina, setDina] = useState('');
    const [carrinho, setCarrinho] = useState('');
    const [campana, setCampana] = useState('');
    const [eixo, setEixo] = useState('');
    const [data, setData] = useState(new Date().toISOString().split('T')[0]); // YYYY-MM-DD
    const [hora, setHora] = useState(new Date().toLocaleTimeString('en-GB')); // HH:MM:SS
    const [operador, setOperador] = useState('');

    const [campanasList, setCampanasList] = useState([]);
    const [loadingCampanas, setLoadingCampanas] = useState(true);
    const [errorCampanas, setErrorCampanas] = useState(null);

    useEffect(() => {
        fetchCampanas();
    }, []);
    const fetchCampanas = () => {
        Campanas_url().then((response) => {
            const dados = response.data.map((dado) => ({
                ...dado
            }));
            setCampanasList(dados);
        }).catch(error => {
            console.error('Erro ao buscar campanas:', error);
            setErrorCampanas('Erro ao carregar campanas.');
        });
    };

    const handleSubmit = async (event) => {
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

        try {
            await axios.post(InsertDadosSala_url(), dados, {
                headers: {
                    'Accept': 'application/json, text/plain',
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            });
            console.log('Dados enviados:', JSON.stringify(dados));
            axios.put(`/campanas/update/${dados.campana}`, dados)
                .then(() => {
                    window.location.replace("/DadosSala");
                })
                .catch(error => {
                    console.error("Erro ao atualizar a campanha:", error);
                });
        } catch (error) {
            alert(`Erro ao enviar dados: ${error}\n${JSON.stringify(dados)}`);
            console.error('Erro ao enviar dados:', error);
        }

        // try{
        //     await axios.put()
        // }
    };

    const handleCancel = () => {
        window.location.replace("/DadosSala");
    };

    return (
        <Box
            component="form"
            className="form-insertDados"
            sx={{ '& .MuiTextField-root': { m: 1, width: '25ch' } }}
            noValidate
            autoComplete="off"
            onSubmit={handleSubmit}
        >
            <div>
                <FormControl sx={{ m: 1, minWidth: 220 }}>
                    <InputLabel id="testcell-label">TestCell *</InputLabel>
                    <Select
                        labelId="testcell-label"
                        id="testcell-select"
                        value={testCell}
                        label="TestCell *"
                        onChange={(event) => setTestCell(event.target.value)}
                        required
                    >
                        {/* Lista de TestCell */}
                        {['A01', 'A02', 'A03', 'A04', 'A05', 'A06', 'A07', 'A08', 'A09', 'A10', 'A11', 'A12', 'B01', 'B02', 'B03', 'B04', 'B05', 'B06'].map(cell => (
                            <MenuItem key={cell} value={cell}>{cell}</MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </div>
            <div>
                <TextField
                    required
                    id="outlined-dinamometro"
                    label="Dinamômetro"
                    value={dina}
                    onChange={(event) => setDina(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
                    id="outlined-carrinho"
                    label="Carrinho"
                    value={carrinho}
                    onChange={(event) => setCarrinho(event.target.value)}
                />
            </div>
            <div>
                <FormControl sx={{ width: '25ch', mb: 1 }}>
                    <InputLabel id="campana-select-label">Campana *</InputLabel>
                    <Select
                        labelId="campana-select-label"
                        id="campana-select"
                        label="Campana"
                        value={campana}
                        onChange={(event) => setCampana(event.target.value)}
                    >
                        {campanasList.map((campana) => (
                            <MenuItem key={campana.id} value={campana.nome}>
                                {campana.nome}
                            </MenuItem>
                        ))}
                    </Select>
                    {errorCampanas && <div style={{ color: 'red' }}>{errorCampanas}</div>}
                </FormControl>
            </div>
            <div>
                <TextField
                    required
                    id="outlined-eixo"
                    label="Eixo"
                    value={eixo}
                    onChange={(event) => setEixo(event.target.value)}
                />
            </div>
            <div>
                <TextField
                    required
                    id="outlined-operador"
                    label="Operador"
                    value={operador}
                    onChange={(event) => setOperador(event.target.value)}
                />
            </div>
            <div>
                <Button
                    variant="contained"
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
