import React, { useEffect, useState, useCallback, useMemo } from "react";
import { listStatus, listLastCausais } from "../../services/StatusService";
import Card from "../../Components/Card/Card";
import './StatusSalas.module.css';

const StatusSalas = () => {
    const [salas, setSalas] = useState([]);
    const [causais, setCausais] = useState([]);
    const [loadingSalas, setLoadingSalas] = useState(true);
    const [loadingCausais, setLoadingCausais] = useState(true);

    // Função para buscar Salas
    const fetchSalas = useCallback(async () => {
        try {
            const response = await listStatus();
            setSalas(response.data);
        } catch (error) {
            console.error("Erro ao buscar Salas:", error);
        } finally {
            setLoadingSalas(false);
        }
    }, []);

    // Função para buscar Causais
    const fetchCausais = useCallback(async () => {
        try {
            const response = await listLastCausais();
            setCausais(response.data);
        } catch (error) {
            console.error("Erro ao buscar Causais:", error);
        } finally {
            setLoadingCausais(false);
        }
    }, []);

    // useEffect para buscar dados inicialmente e configurar polling
    useEffect(() => {
        fetchSalas();
        fetchCausais();

        const intervalId = setInterval(() => {
            fetchSalas();
            fetchCausais();
        }, 5000); // Atualiza a cada 5 segundos

        return () => clearInterval(intervalId); // Limpa o intervalo ao desmontar
    }, [fetchSalas, fetchCausais]);

    // Cria um mapa para acesso rápido aos Causais
    const causaisMap = useMemo(() => {
        const map = new Map();
        causais.forEach(causal => {
            map.set(causal.id, causal);
        });
        return map;
    }, [causais]);

    // Função otimizada para determinar o status
    const getStatus = useCallback((id, status) => {
        if (status === 0) {
            const causal = causaisMap.get(id);
            return causal ? causal.causal : "Desconhecido";
        } else if (status === 1) {
            return "Running";
        } else if (status === 2) {
            return "Cooling";
        } else if (status === 3) {
            return "Sala inativa";
        }
        return "Status desconhecido";
    }, [causaisMap]);

    // Verifica se está carregando
    const isLoading = loadingSalas || loadingCausais;

    if (isLoading) {
        return <div>Loading...</div>; // Renderiza um indicador de carregamento
    }

    return (
        <div className="my-2">
            <div className='row row-cols-6 gx-4 gy-2 m-0 p-0'>
                {
                    salas.map(sala => (
                        <div className="col-2 col-md-2 col-sm-4 col-xs-6" key={sala.id}>
                            <Card
                                testCell={sala.testCell}
                                status={getStatus(sala.id, sala.status)}
                                status_actual={sala.status}
                                motor={sala.motor}
                                projeto={sala.projeto}
                                teste={sala.teste}
                                eff={sala.eff < 0 ? 0 : sala.eff}
                                paradaAtual={sala.parada_atual}
                                paradaTotal={sala.parada_total}
                            />
                        </div>
                    ))
                }
            </div>
        </div>
    );
};

export default StatusSalas;
