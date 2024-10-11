import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import { Paper, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField, MenuItem, Select, FormControl, InputLabel } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import ArticleOutlinedIcon from '@mui/icons-material/ArticleOutlined';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import PauseCircleOutlineIcon from '@mui/icons-material/PauseCircleOutline';
import BuildOutlinedIcon from '@mui/icons-material/BuildOutlined';
import { Campanas_url,atualizaCampana } from "../../services/StatusService";
import axios from 'axios';

const formatDate = (date) => {
    if (!date) return '';
    const d = new Date(date);
    return d.toLocaleDateString('pt-BR');
};

const getStatusIcon = (status) => {
    switch (status) {
        case 0:
            return <CheckCircleIcon sx={{ color: 'green' }} />;
        case 1:
            return <PauseCircleOutlineIcon sx={{ color: 'red' }} />;
        case 2:
            return <BuildOutlinedIcon />;
        default:
            return null;
    }
};

const Campanas = () => {
    const [rows, setRows] = useState([]);
    const [open, setOpen] = useState(false);
    const [selectedRow, setSelectedRow] = useState(null);

    useEffect(() => {
        fetchCampanas();
    }, []);

    const fetchCampanas = () => {
        Campanas_url().then((response) => {
            const dados = response.data.map((dado) => ({
                ...dado,
                dataEntrada: formatDate(dado.dataEntrada),
                dataRevisao: formatDate(dado.dataRevisao),
                dataSaida: dado.dataSaida ? formatDate(dado.dataSaida) : ''
            }));
            setRows(dados);
        }).catch(error => {
            console.log(error);
        });
    };

    const handleEditar = (row) => {
        setSelectedRow({
            ...row,
            dataEntrada: row.dataEntrada.split('/').reverse().join('-'),
            dataRevisao: row.dataRevisao.split('/').reverse().join('-'),
            dataSaida: row.dataSaida ? row.dataSaida.split('/').reverse().join('-') : ''
        });
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setSelectedRow(null);
    };

    const handleSave = () => {
        const formatDateToString = (date) => {
            const d = new Date(date);
            return d.toISOString().slice(0, 10);
        };

        const updatedRow = {
            ...selectedRow,
            dataEntrada: formatDateToString(selectedRow.dataEntrada), // Formata para YYYY-MM-DD
            dataRevisao: formatDateToString(selectedRow.dataRevisao), // Formata para YYYY-MM-DD
            dataSaida: selectedRow.dataSaida ? formatDateToString(selectedRow.dataSaida) : null, // Formata para YYYY-MM-DD
            status: parseInt(selectedRow.status) // Garantindo que o status seja um número
        };

        atualizaCampana(selectedRow.id,updatedRow).then(() => {
            // console.log(JSON.stringify(updatedRow))
            fetchCampanas(); // Atualiza a lista após salvar
            handleClose();
        })
        .catch(error => {
            console.error("Erro ao atualizar a campanha:", error);
        });
    };

    const columns = [
        { field: 'nome', headerName: 'Nome', width: 150 },
        { field: 'local', headerName: 'Local', width: 150 },
        { field: 'dataEntrada', headerName: 'Entrada', width: 150 },
        { field: 'dataSaida', headerName: 'Saida', width: 150 },
        { field: 'dataRevisao', headerName: 'Revisão', width: 150 },
        { field: 'horaRodagem', headerName: 'Rodagem', width: 150 },
        { field: 'obs', headerName: 'Obs', width: 150 },
        {
            field: 'status',
            headerName: 'Status',
            width: 150,
            renderCell: (params) => getStatusIcon(params.value),
        },
        {
            field: 'editar',
            headerName: 'Editar',
            width: 150,
            renderCell: (params) => (
                <Button variant="contained" startIcon={<EditIcon />} onClick={() => handleEditar(params.row)}>
                    Editar
                </Button>
            )
        }
    ];

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '90vh' }}>
            <Paper sx={{ width: '85%', height: '90%', padding: 4 }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 2 }}>
                    <div>
                        <Link to="/DadosSala">
                            <Button variant="contained" startIcon={<ArticleOutlinedIcon />}>
                                Back
                            </Button>
                        </Link>
                    </div>
                    <div>
                        <Link to="/insertCampana">
                            <Button variant="contained" startIcon={<AddIcon />}>
                                Adicionar
                            </Button>
                        </Link>
                    </div>
                </div>
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSizeOptions={[5, 10]}
                    checkboxSelection
                    sx={{ border: 0 }}
                />
            </Paper>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Editar Campana</DialogTitle>
                <DialogContent>
                    {selectedRow && (
                        <>
                            <TextField
                                autoFocus
                                margin="dense"
                                label="Nome"
                                type="text"
                                fullWidth
                                value={selectedRow.nome}
                                onChange={(e) => setSelectedRow({ ...selectedRow, nome: e.target.value })}
                            />
                            <TextField
                                margin="dense"
                                label="Local"
                                type="text"
                                fullWidth
                                value={selectedRow.local}
                                onChange={(e) => setSelectedRow({ ...selectedRow, local: e.target.value })}
                            />
                            <TextField
                                margin="dense"
                                label="Data de Entrada"
                                type="date"
                                fullWidth
                                value={selectedRow.dataEntrada}
                                onChange={(e) => setSelectedRow({ ...selectedRow, dataEntrada: e.target.value })}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                            <TextField
                                margin="dense"
                                label="Data de Saída"
                                type="date"
                                fullWidth
                                value={selectedRow.dataSaida}
                                onChange={(e) => setSelectedRow({ ...selectedRow, dataSaida: e.target.value })}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                            <TextField
                                margin="dense"
                                label="Data de Revisão"
                                type="date"
                                fullWidth
                                value={selectedRow.dataRevisao}
                                onChange={(e) => setSelectedRow({ ...selectedRow, dataRevisao: e.target.value })}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            />
                            <TextField
                                margin="dense"
                                label="Hora de Rodagem"
                                type="text"
                                fullWidth
                                value={selectedRow.horaRodagem}
                                onChange={(e) => setSelectedRow({ ...selectedRow, horaRodagem: e.target.value })}
                            />
                            <TextField
                                margin="dense"
                                label="Observações"
                                type="text"
                                fullWidth
                                value={selectedRow.obs}
                                onChange={(e) => setSelectedRow({ ...selectedRow, obs: e.target.value })}
                            />
                            <TextField
                                margin="dense"
                                label="Status"
                                fullWidth
                                select
                                value={selectedRow.status}
                                onChange={(e) => setSelectedRow({ ...selectedRow, status: e.target.value })}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                            >
                                <MenuItem value={0}>Em uso</MenuItem>
                                <MenuItem value={1}>Guardada para uso</MenuItem>
                                <MenuItem value={2}>Manutenção</MenuItem>
                            </TextField>
                        </>
                    )}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancelar</Button>
                    <Button onClick={handleSave}>Salvar</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default Campanas;
