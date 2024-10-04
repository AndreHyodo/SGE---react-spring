import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

import { DataGrid, GridColDef } from '@mui/x-data-grid';
import {Paper} from "@mui/material";
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';

import {DadosSala_url} from "../../services/StatusService";

const columns = [
    { field: 'id', headerName: 'ID', width: 100 },
    { field: 'testCell', headerName: 'TestCell', width: 150 },
    { field: 'dina', headerName: 'Dinamômetro', width: 150 },
    { field: 'carrinho', headerName: 'Carrinho', width: 150 },
    { field: 'campana', headerName: 'Campana', width: 150 },
    { field: 'eixo', headerName: 'Eixo', width: 150 },
    { field: 'operador', headerName: 'Operador', width: 150 },
    { field: 'data', headerName: 'Data', width: 150 },
    { field: 'hora', headerName: 'Hora', width: 150 },
];

const DadosSala = () => {
    const [rows, setRows] = useState([]);
    const [sortModel, setSortModel] = useState([
        {
            field: 'id',
            sort: 'desc',
        },
    ]);

    useEffect(() => {
        DadosSala_url().then((response) => {
            const dados = response.data.map((dado) => ({
                ...dado,
                id: dado.id_dados,
                data: new Date(dado.data).toLocaleDateString('pt-BR')
            }));
            setRows(dados);
        }).catch(error => {
            console.log(error);
        })
    }, [rows])

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '90vh' }}>
            <Paper sx={{ width: '80%', height: '90%', padding: 4 }}>
                <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: 2 }}>
                    <Link to="/insertDadosSala">
                        <Button variant="contained" startIcon={<AddIcon />}>
                            Adicionar
                        </Button>
                    </Link>
                </div>
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSizeOptions={[5, 10]}
                    checkboxSelection
                    sx={{ border: 0 }}
                    sortModel={sortModel}
                    onSortModelChange={(newSortModel) => setSortModel(newSortModel)}
                />
            </Paper>
        </div>
    );
};

export default DadosSala;