import React, {useState, useEffect, useMemo} from 'react';
import {list_Causais, registra, last_Causais, getStatusTestCell} from "../../services/StatusService";
import {Button} from "reactstrap";
import {position, useDisclosure} from "@chakra-ui/react";
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    AlertIcon,
    Alert,
} from '@chakra-ui/react'
import axios from "axios";


const App = () => {
    const [data, setData] = useState([]);
    const [dropdown, setDropdown] = useState("");


    useEffect(() => {
        list_Causais().then((response) => {
            setData(response.data);
        }).catch(error => {
            console.log(error);
        })
    }, [data])

    const [lastCausais, setLast] = useState([]);
    const [selectedTestCell, setSelectedTestCell] = useState('');

    const handleButtonClick = (newTestCell) => {
        setSelectedTestCell(newTestCell);
    };

    useEffect(() => {
        (async () => {
            try {
                const response = await fetch(last_Causais(selectedTestCell));
                const data = await response.json();
                setLast(data);
            } catch (error) {
                console.error(error);
            }
        })();
    }, [selectedTestCell]);

    const handleToggleButtonClick = (event) => {
        if (event.target.matches('.toggle-data-btn')) {
            const cardBody = event.target.parentNode.nextElementSibling;
            const toggleBtnText = event.target.textContent;

            if (toggleBtnText === '+') {
                event.target.textContent = '-';
                cardBody.style.display = 'block';
            } else {
                event.target.textContent = '+';
                cardBody.style.display = 'none';
            }
        }
    };

    // Group data by type
    const groupedData = useMemo(() => {
        return data.reduce((groups, item) => {
            const existingGroup = groups.find(g => g.type === item.type);
            if (existingGroup) {
                existingGroup.items.push(item);
            } else {
                groups.push({ type: item.type, items: [item] });
            }
            return groups;
        }, []);
    }, [data]);

    const [searchQuery, setSearchQuery] = useState('');
    const [filteredData, setFilteredData] = useState([]);

    const handleSearchChange = (event) => {
        const query = event.target.value.toLowerCase();
        setSearchQuery(query);

        // Filter data based on search query
        const filtered = data.filter((item) => {
            if(query !== ""){
                return item.causal.toLowerCase().includes(query);
            }
        });

        setFilteredData(filtered);
    };


    const [checkedState, setCheckedState] = useState(
        new Array(30).fill(false)
    );

    const handleOnChange = (code,causal,position) => {

        console.log(position + " - "+ checkedState[position] + " e " + causal + "------" + checkedState)


        for (let index in checkedState)
            if(index == position){
                checkedState[index] = !checkedState[index]
            }

        if(checkedState[position]){
            document.getElementById('causal').value = causal
            document.getElementById('Code').value = code
        }else{
            document.getElementById('causal').value = ""
            document.getElementById('Code').value = ""
        }

    }


    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log('Submitting form');

        var data = new Date(),
            hora =  data.getHours(),
            minuto = data.getMinutes(),
            segundos = data.getSeconds() <= 9 ? "0"+data.getSeconds() : data.getSeconds()



        // Corrected: Access form data using event.target.elements
        const formElement = document.getElementById('formElementId'); // Substitua 'formElementId' pelo ID do seu formulário
        const formData = new FormData(formElement);


        const date = Date.now();
        const hora_inicio = `${hora}:${minuto}:${segundos}`;
        const testCell = formData.get('testCell');
        const code = formData.get('Code');
        const causal = formData.get('causal');
        const obs = formData.get('obs');


        const objCausal = {
            testCell: testCell,
            code: code,
            causal: causal,
            hora_inicio: hora_inicio,
            data:date,
            obs: obs
        };

        console.log(testCell + " -- " + code + " -- " + causal);

        if(testCell!=="Select" && testCell!=="" && code!=="" && causal!==""){
            const response = await fetch(getStatusTestCell(testCell));
            const statusSala = await response.json();

            console.log(objCausal.testCell + " -- " + objCausal.code + " -- " + objCausal.causal);

            if(statusSala.status===0){
                fetch(registra(), {
                    method: 'POST',
                    mode: "cors",
                    body: JSON.stringify(objCausal),
                    // headers: { 'Content-Type': 'application/json' },
                    headers: {
                        'Accept': 'application/json, text/plain',
                        'Content-Type': 'application/json;charset=UTF-8'
                    }

                })
                    .then(retorno => {
                        alert("Registrado com sucesso \n\n" + testCell + "\n" + code + "\n" + causal + "\n" + obs);
                        document.getElementById('causal').value = ""
                        document.getElementById('Code').value = ""
                        document.getElementById('obs').value = ""
                        window.location.reload();
                        retorno.json()
                    })
                    .catch(retorno_convertido => {
                        alert(retorno_convertido + "\n" + JSON.stringify(objCausal));
                        console.log(retorno_convertido);
                    });
            }else{
                alert("Sala em FUNCIONAMENTO, NÃO foi possível Registrar!!!");
            }
        }else{
            alert("ERROR!!!! \n\n Dados Faltantes, favor conferir");
        }



    };

    const OverlayOne = () => (
        <ModalOverlay
            bg='none'
            backdropFilter='auto'
            backdropInvert='80%'
            backdropBlur='2px'
        />
    )

    const { isOpen, onOpen, onClose } = useDisclosure()
    const [overlay, setOverlay] = useState(<OverlayOne />)

    const [selectedOption, setSelectedOption] = useState('Select');

    const handleOpenModal = () => {
        onOpen();
    };

    const handleSelectOption = (option) => {
        setSelectedOption(option);
    };

    const handleCloseModal = () => {
        onClose();
    };




    return (
        <div id="data-container">
            <div className="w-100 m-3 text-center w-50">
                <input className="w-25 text-center border-black border-1" type="text" id="busca" placeholder="Pesquise o Causal aqui..." onChange={handleSearchChange}/>
                {filteredData.length > 0 && (
                    <div className="w-100 text-center">
                        {filteredData.map((item,index) => (
                            <div>
                                <input id="checkboxCausal"
                                       type="checkbox"
                                       name="checkboxCausal"
                                       checked={checkedState[index]}
                                       onChange={() => handleOnChange(item.code,item.causal,index)} className="m-1"/>
                                <span className="text-center" key={item.id}>{item.code + " - " +item.causal}</span>
                            </div>
                        ))}
                    </div>
                )}
            </div>
            {groupedData.map((groupData, index) => (
                <div key={index} className="col-md-3" id="card-causal">
                    <div className="card mb-3">
                        <div className="card-body">
                            <h5 className="card-title-causal">
                                <button
                                    className="btn btn-primary btn-sm toggle-data-btn m-xl-2"
                                    onClick={handleToggleButtonClick}
                                >
                                    +
                                </button>
                                {groupData.type}
                            </h5>
                            <ul style={{ display: 'none' }}>

                                {groupData.items.map((item, itemIndex) => (
                                    <li key={itemIndex}>
                                        <input
                                            id="checkboxCausal"
                                            type="checkbox"
                                            value={item.causal}
                                            name="checkboxCausal"
                                            checked={checkedState[item]}
                                            onChange={() => handleOnChange(item.code,item.causal,itemIndex)}
                                        />
                                        <span>{` ${item.code} ${item.causal} `}</span>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    </div>
                </div>
            ))}
            <div className="lastCauais" >
                {lastCausais.map((item) => (
                    <Button
                        key={item.id}
                        className="LastCausais"
                        onClick={() => {
                            document.getElementById('causal').value = item.causal;
                            document.getElementById('Code').value = item.code;
                        }}
                    >
                        {item.code + " : " + item.causal}
                    </Button>
                ))}
            </div>
            <form action="/causais/insertCausal" method="POST" className="form" id="formElementId"  onSubmit={handleSubmit}>
                <div className="form-input">
                    <div className="row">
                        <div className="col-md-2 ">
                            <div className="row text-light" id="texts">
                                <label htmlFor="TestCell">TestCell:</label>
                            </div>
                            <div className="row d-flex text-center" id="inputs" >
                                <div className="w-100 d-flex">
                                    <Button
                                        onClick={() => {
                                            setOverlay(<OverlayOne />)
                                            onOpen()
                                        }}
                                        className="h-auto mx-2 text-center w-75"
                                        // name="testCell"
                                        value="Select"
                                    >
                                        {selectedOption}

                                    </Button>
                                    <input hidden={true} type="text" name="testCell" onChange={handleSubmit} value={selectedOption}/>
                                    <input hidden={true} type="text" name="tcList" onChange={handleButtonClick} value={selectedOption}/>
                                </div>

                                {/*<input type="text" name="TestCell" id="TestCell" value={GetIP()}/>*/}
                            </div>
                        </div>
                        <div className="col-md-2">
                            <div className="row text-light" id="texts">
                                <label htmlFor="Code">Código:</label>
                            </div>
                            <div className="row" id="inputs">
                                <input type="text" name="Code" id="Code" />
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="row text-light" id="texts">
                                <label htmlFor="causal">Causal:</label>
                            </div>
                            <div className="row" id="inputs">
                                <input type="text"  name="causal" id="causal" />
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="row text-light" id="texts">
                                <label htmlFor="obs">Obs:</label>
                            </div>
                            <div className="row" id="inputs">
                                <textarea name="obs" id="obs" cols="30" rows="2" className="obs"></textarea>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        {/*<div className="col-md-12 botao-enviar">*/}
                        {/*    <div className="row" id="texts">*/}
                        {/*        /!*<input type="submit" value="Enviar dados" onClick="alterLocalStorage(document.getElementById('TestCell').value, document.getElementById('causal').value)"/>*!/*/}
                        {/*        <input type="submit" value="Enviar dados"/>*/}

                        {/*    </div>*/}
                        {/*</div>*/}
                        <Button
                            mt={4}
                            type='submit'
                            className="col-md-12 botao-enviar"
                            onClick={handleSubmit}
                        >
                            Submit
                        </Button>
                    </div>
                </div>
            </form>
            <Modal isCentered isOpen={isOpen} onClose={onClose}>
                {overlay}
                <ModalContent>
                    <ModalHeader>Select TestCell</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <div>
                            <h3>Dev</h3>
                            <Button className='m-2' onClick={() => { handleSelectOption('A01'); handleCloseModal(); handleButtonClick('A01')}}>A01</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A02'); handleCloseModal(); handleButtonClick('A02') }}>A02</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A03'); handleCloseModal(); handleButtonClick('A03') }}>A03</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A04'); handleCloseModal(); handleButtonClick('A04') }}>A04</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A05'); handleCloseModal(); handleButtonClick('A05') }}>A05</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A10'); handleCloseModal(); handleButtonClick('A10') }}>A10</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A11'); handleCloseModal(); handleButtonClick('A11') }}>A11</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A12'); handleCloseModal(); handleButtonClick('A12') }}>A12</Button>
                        </div>
                        <div>
                            <h3>Durability</h3>
                            <Button className='m-2' onClick={() => { handleSelectOption('B01'); handleCloseModal(); handleButtonClick('B01') }}>B01</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('B02'); handleCloseModal(); handleButtonClick('B02') }}>B02</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('B03'); handleCloseModal(); handleButtonClick('B03') }}>B03</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('B04'); handleCloseModal(); handleButtonClick('B04') }}>B04</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('B05'); handleCloseModal(); handleButtonClick('B05') }}>B05</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('B06'); handleCloseModal(); handleButtonClick('B06') }}>B06</Button>
                        </div>
                        <div>
                            <h3>StartCart</h3>
                            <Button className='m-2' onClick={() => { handleSelectOption('A06'); handleCloseModal(); handleButtonClick('A06') }}>A06</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A07'); handleCloseModal(); handleButtonClick('A07') }}>A07</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A08'); handleCloseModal(); handleButtonClick('A08') }}>A08</Button>
                            <Button className='m-2' onClick={() => { handleSelectOption('A09'); handleCloseModal(); handleButtonClick('A09') }}>A09</Button>
                        </div>

                    </ModalBody>
                </ModalContent>
            </Modal>
        </div>
    );
};

export default App;
