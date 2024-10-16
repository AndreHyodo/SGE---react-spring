import React, { useState, useEffect, useMemo, useCallback } from 'react';
import { list_Causais, registra, last_Causais, getStatusTestCell } from "../../services/StatusService";
import { Button } from "reactstrap";
import { useDisclosure, Modal, ModalOverlay, ModalContent, ModalHeader, ModalBody, ModalCloseButton } from '@chakra-ui/react';

const App = () => {
    // Estados principais
    const [causais, setCausais] = useState([]);
    const [lastCausais, setLastCausais] = useState([]);
    const [selectedTestCell, setSelectedTestCell] = useState('');
    const [searchQuery, setSearchQuery] = useState('');
    const [checkedState, setCheckedState] = useState({});
    const [formData, setFormData] = useState({
        testCell: '',
        code: '',
        causal: '',
        obs: ''
    });
    const [toggleGroups, setToggleGroups] = useState({});

    // Controle do Modal
    const { isOpen, onOpen, onClose } = useDisclosure();

    // Fetch Causais no mount
    useEffect(() => {
        const fetchCausais = async () => {
            try {
                const response = await list_Causais();
                setCausais(response.data);
            } catch (error) {
                console.error("Erro ao buscar Causais:", error);
            }
        };
        fetchCausais();
    }, []);

    // Fetch lastCausais quando selectedTestCell muda
    useEffect(() => {
        if (selectedTestCell) {
            const fetchLastCausais = async () => {
                try {
                    const response = await last_Causais(selectedTestCell);
                    setLastCausais(response.data);
                } catch (error) {
                    console.error("Erro ao buscar lastCausais:", error);
                }
            };
            fetchLastCausais();
        }
    }, [selectedTestCell]);

    // Recupera TestCell do sessionStorage no mount
    useEffect(() => {
        const storedTestCell = sessionStorage.getItem('TestCell');
        if (storedTestCell) {
            setSelectedTestCell(storedTestCell);
            setFormData(prev => ({ ...prev, testCell: storedTestCell }));
        }
    }, []);

    // Handle search input change
    const handleSearchChange = useCallback((event) => {
        const query = event.target.value.toLowerCase();
        setSearchQuery(query);
    }, []);

    // Filtra causais com base na pesquisa
    const filteredData = useMemo(() => {
        if (searchQuery.trim() === '') {
            return [];
        }
        return causais.filter(item => item.causal.toLowerCase().includes(searchQuery));
    }, [searchQuery, causais]);

    // Agrupa dados por tipo
    const groupedData = useMemo(() => {
        return causais.reduce((groups, item) => {
            if (!groups[item.type]) {
                groups[item.type] = [];
            }
            groups[item.type].push(item);
            return groups;
        }, {});
    }, [causais]);

    // Handler para seleção de TestCell
    const handleSelectTestCell = useCallback((option) => {
        setSelectedTestCell(option);
        setFormData(prev => ({ ...prev, testCell: option }));
        sessionStorage.setItem('TestCell', option);
        onClose();
    }, [onClose]);

    // Handler para mudanças nos inputs
    const handleInputChange = useCallback((e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    }, []);

    // Handler para checkbox
    const handleOnChange = useCallback((code, causal, id) => {
        setCheckedState(prev => {
            const newCheckedState = { ...prev, [id]: !prev[id] };
            if (newCheckedState[id]) {
                setFormData(prevData => ({
                    ...prevData,
                    code: code,
                    causal: causal
                }));
            } else {
                setFormData(prevData => ({
                    ...prevData,
                    code: '',
                    causal: ''
                }));
            }
            return newCheckedState;
        });
    }, []);

    // Handler para toggle dos grupos
    const handleToggleGroup = useCallback((type) => {
        setToggleGroups(prev => ({
            ...prev,
            [type]: !prev[type]
        }));
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const { testCell, code, causal, obs } = formData;

        if (testCell !== "Select" && testCell && code && causal) {
            try {
                sessionStorage.setItem('TestCell', testCell);
                const response = await getStatusTestCell(testCell);
                const statusSala = response.data; // assumindo resposta JSON

                if (statusSala.status === 0) {
                    const currentTime = new Date();
                    const hora_inicio = `${currentTime.getHours()}:${currentTime.getMinutes()}:${currentTime.getSeconds().toString().padStart(2, '0')}`;
                    const objCausal = {
                        testCell,
                        code,
                        causal,
                        hora_inicio,
                        data: Date.now(),
                        obs
                    };

                    await registra(objCausal);
                    alert(`Registrado com sucesso \n\n${testCell}\n${code}\n${causal}\n${obs}`);
                    // Resetar formulário
                    setFormData({
                        testCell,
                        code: '',
                        causal: '',
                        obs: ''
                    });
                    setCheckedState({});
                    // Atualizar lastCausais
                    const updatedLastCausais = await last_Causais(testCell);
                    setLastCausais(updatedLastCausais.data);
                } else {
                    alert("Sala em FUNCIONAMENTO, NÃO foi possível Registrar!!!");
                }
            } catch (error) {
                console.error("Erro ao registrar causal:", error);
                alert("Ocorreu um erro ao registrar. Por favor, tente novamente.");
            }
        } else {
            alert("ERROR!!!!\n\n Dados Faltantes, favor conferir");
        }
    };

    return (
        <div id="data-container">
            <div className="w-100 m-3 text-center w-50">
                <input
                    className="w-25 text-center border-black border-1"
                    type="text"
                    id="busca"
                    placeholder="Pesquise o Causal aqui..."
                    onChange={handleSearchChange}
                />
                {filteredData.length > 0 && (
                    <div className="w-100 text-center">
                        {filteredData.map((item) => (
                            <div key={item.id}>
                                <input
                                    id={`checkboxCausal-${item.id}`}
                                    type="checkbox"
                                    name="checkboxCausal"
                                    checked={checkedState[item.id] || false}
                                    onChange={() => handleOnChange(item.code, item.causal, item.id)}
                                    className="m-1"
                                />
                                <span className="text-center">{`${item.code} - ${item.causal}`}</span>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            {/* Grupos de Causais */}
            <div className="row m-3">
                {Object.entries(groupedData).map(([type, items]) => (
                    <div key={type} className="col-md-3" id="card-causal">
                        <div className="card mb-3">
                            <div className="card-body">
                                <h5 className="card-title-causal ms-2 d-flex justify-content-between align-items-center">
                                    {type}
                                    {/* Botão de Toggle */}
                                    <button
                                        className="btn btn-primary btn-sm toggle-data-btn m-xl-2"
                                        onClick={() => handleToggleGroup(type)}
                                    >
                                        {toggleGroups[type] ? '-' : '+'}
                                    </button>
                                </h5>
                                <ul style={{ display: toggleGroups[type] ? 'block' : 'none' }}>
                                    {items.map((item) => (
                                        <li key={item.id}>
                                            <input
                                                id={`checkboxCausal-${item.id}`}
                                                type="checkbox"
                                                value={item.causal}
                                                name="checkboxCausal"
                                                checked={checkedState[item.id] || false}
                                                onChange={() => handleOnChange(item.code, item.causal, item.id)}
                                                className="m-1"
                                            />
                                            <span>{` ${item.code} ${item.causal} `}</span>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {/* Últimos Causais */}
            <div className="lastCausais mb-4">
                {lastCausais.map((item) => (
                    <Button
                        key={item.id}
                        className="LastCausais m-1"
                        onClick={() => {
                            setFormData(prev => ({
                                ...prev,
                                causal: item.causal,
                                code: item.code
                            }));
                            setCheckedState({});
                        }}
                    >
                        {`${item.code} : ${item.causal}`}
                    </Button>
                ))}
            </div>

            {/* Formulário */}
            <form onSubmit={handleSubmit} className="form" id="formElementId">
                <div className="form-input">
                    <div className="row">
                        {/* TestCell */}
                        <div className="col-md-2 ">
                            <div className="row text-light" id="texts">
                                <label htmlFor="testCell">TestCell:</label>
                            </div>
                            <div className="row d-flex text-center" id="inputs" >
                                <div className="w-100 d-flex">
                                    <Button
                                        type="button"
                                        onClick={onOpen}
                                        className="h-auto mx-2 text-center w-75"
                                    >
                                        {selectedTestCell || 'Select'}
                                    </Button>
                                    <input
                                        type="hidden"
                                        name="testCell"
                                        value={formData.testCell}
                                    />
                                    <input
                                        type="hidden"
                                        name="tcList"
                                        value={formData.testCell}
                                    />
                                </div>
                            </div>
                        </div>

                        {/* Código */}
                        <div className="col-md-2">
                            <div className="row text-light" id="texts">
                                <label htmlFor="Code">Código:</label>
                            </div>
                            <div className="row" id="inputs">
                                <input
                                    type="text"
                                    name="Code"
                                    id="Code"
                                    className="form-control"
                                    value={formData.code}
                                    onChange={handleInputChange}
                                />
                            </div>
                        </div>

                        {/* Causal */}
                        <div className="col-md-4">
                            <div className="row text-light" id="texts">
                                <label htmlFor="causal">Causal:</label>
                            </div>
                            <div className="row" id="inputs">
                                <input
                                    type="text"
                                    name="causal"
                                    id="causal"
                                    className="form-control"
                                    value={formData.causal}
                                    onChange={handleInputChange}
                                />
                            </div>
                        </div>

                        {/* Observações */}
                        <div className="col-md-4">
                            <div className="row text-light" id="texts">
                                <label htmlFor="obs">Obs:</label>
                            </div>
                            <div className="row" id="inputs">
                                <textarea
                                    name="obs"
                                    id="obs"
                                    cols="30"
                                    rows="1"
                                    className="form-control obs"
                                    value={formData.obs}
                                    onChange={handleInputChange}
                                ></textarea>
                            </div>
                        </div>
                    </div>

                    <div className="row">
                        <Button
                            mt={4}
                            type='submit'
                            className="col-md-12 botao-enviar"
                        >
                            Submit
                        </Button>
                    </div>
                </div>
            </form>

            {/* Modal para Seleção de TestCell */}
            <Modal isCentered isOpen={isOpen} onClose={onClose}>
                <ModalOverlay
                    bg='none'
                    backdropFilter='auto'
                    backdropInvert='80%'
                    backdropBlur='2px'
                />
                <ModalContent>
                    <ModalHeader>Select TestCell</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <div>
                            <h3>Dev</h3>
                            {['A01', 'A02', 'A03', 'A04', 'A05', 'A10', 'A11', 'A12'].map(option => (
                                <Button
                                    key={option}
                                    className='m-2'
                                    onClick={() => handleSelectTestCell(option)}
                                >
                                    {option}
                                </Button>
                            ))}
                        </div>
                        <div>
                            <h3>Durability</h3>
                            {['B01', 'B02', 'B03', 'B04', 'B05', 'B06'].map(option => (
                                <Button
                                    key={option}
                                    className='m-2'
                                    onClick={() => handleSelectTestCell(option)}
                                >
                                    {option}
                                </Button>
                            ))}
                        </div>
                        <div>
                            <h3>StartCart</h3>
                            {['A06', 'A07', 'A08', 'A09'].map(option => (
                                <Button
                                    key={option}
                                    className='m-2'
                                    onClick={() => handleSelectTestCell(option)}
                                >
                                    {option}
                                </Button>
                            ))}
                        </div>
                        <div>
                            <h3>Outros</h3>
                            {['SPT'].map(option => (
                                <Button
                                    key={option}
                                    className='m-2'
                                    onClick={() => handleSelectTestCell(option)}
                                >
                                    {option}
                                </Button>
                            ))}
                        </div>
                    </ModalBody>
                </ModalContent>
            </Modal>
        </div>
    );
};

export default App;
