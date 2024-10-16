import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { Paper, CircularProgress, Typography } from "@mui/material";
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import ArticleOutlinedIcon from '@mui/icons-material/ArticleOutlined';
import RefreshIcon from '@mui/icons-material/Refresh';

import { DadosSala_url } from "../../services/StatusService";

const columns= [
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
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Função para buscar dados da sala, memoizada para evitar recriações
    const fetchDadosSala = useCallback(async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await DadosSala_url();
            const dados = response.data.map((dado) => ({
                ...dado,
                id: dado.id_dados,
                data: new Date(dado.data).toLocaleDateString('pt-BR'),
                hora: new Date(dado.data).toLocaleTimeString('pt-BR'), // Supondo que 'data' inclui a hora
            }));
            setRows(dados);
        } catch (err) {
            console.error("Erro ao buscar DadosSala:", err);
            setError("Falha ao carregar os dados. Tente novamente.");
        } finally {
            setLoading(false);
        }
    }, []);

    // useEffect para buscar dados inicialmente
    useEffect(() => {
        fetchDadosSala();
    }, [fetchDadosSala]);

    return (
        <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '90vh' }}>
            <Paper className="w-80 p-4" style={{ overflow: 'hidden' }}>
                {/* Cabeçalho com Botões */}
                <div className="d-flex justify-content-between mb-3">
                    <Link to="/DadosCampanas" className="text-decoration-none">
                        <Button variant="contained" startIcon={<ArticleOutlinedIcon />}>
                            Lista Campanas
                        </Button>
                    </Link>
                    <div className="d-flex">
                        <Link to="/insertDadosSala" className="text-decoration-none me-2">
                            <Button variant="contained" startIcon={<AddIcon />}>
                                Adicionar
                            </Button>
                        </Link>
                        <Button
                            variant="outlined"
                            startIcon={<RefreshIcon />}
                            onClick={fetchDadosSala}
                            size="small"
                        >
                            Refresh
                        </Button>
                    </div>
                </div>

                {/* Conteúdo Principal */}
                {loading ? (
                    <div className="d-flex justify-content-center align-items-center" style={{ height: '80%' }}>
                        <CircularProgress />
                    </div>
                ) : error ? (
                    <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: '80%' }}>
                        <Typography variant="h6" color="error" gutterBottom>
                            {error}
                        </Typography>
                        <Button variant="contained" color="primary" onClick={fetchDadosSala}>
                            Tentar Novamente
                        </Button>
                    </div>
                ) : (
                    <div style={{ height: '75vh', width: '100%', overflow: 'auto' }}>
                        <DataGrid
                            rows={rows}
                            columns={columns}
                            pageSizeOptions={[5, 10, 20]}
                            checkboxSelection
                            disableSelectionOnClick
                            sx={{ border: 0 }}
                            sortModel={sortModel}
                            onSortModelChange={(newSortModel) => setSortModel(newSortModel)}
                            pagination
                        />
                    </div>
                )}
            </Paper>
        </div>
    );
};

export default DadosSala;
