import React, {useState, useEffect, useMemo} from 'react';
import {list_Causais} from "../../services/StatusService";
import indexUmd from "bootstrap/js/index.umd";
import {Button} from "reactstrap";


const App = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        list_Causais().then((response) => {
            setData(response.data);
        }).catch(error => {
            console.log(error);
        })
    }, [data])


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



    return (
        <div id="data-container">
            <div className="w-100 m-3 text-center w-50">
                <input className="w-25 text-center" type="text" id="busca" placeholder="Digite sua pesquisa..." onChange={handleSearchChange}/>
                {filteredData.length > 0 && (
                    <div className="w-100 text-center">
                        {filteredData.map((item) => (
                            <div>
                                <input type="checkbox" className="m-1"/>
                                <span className="text-center" key={item.id}>{item.causal}</span>
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
                                            type="checkbox"
                                            value={item.causal}
                                            onChange={() => {
                                                // Sua lógica de manipulação do estado aqui
                                            }}
                                        />
                                        <span>{` ${item.code} ${item.causal} `}</span>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    </div>
                </div>
            ))}
            <form action="" method="post" className="form">
                <div className="form-input">
                    <div className="row">
                        <div className="col-md-3">
                            <div className="row" id="texts">
                                <label htmlFor="TestCell">TestCell:</label>
                            </div>
                            <div className="row" id="inputs">
                                <input type="text" name="TestCell" id="TestCell" value="<?php echo $GLOBALS['valor']; ?>"/>
                            </div>
                        </div>
                        <div className="col-md-1">
                            <div className="row" id="texts">
                                <label htmlFor="Code">Código:</label>
                            </div>
                            <div className="row" id="inputs">
                                <input type="text" name="Code" id="Code"/>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="row" id="texts">
                                <label htmlFor="causal">Causal:</label>
                            </div>
                            <div className="row" id="inputs">
                                <input type="text" name="causal" id="causal" />
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="row" id="texts">
                                <label htmlFor="obs">Obs:</label>
                            </div>
                            <div className="row" id="inputs">
                                <textarea name="obs" id="obs" cols="30" rows="4" className="obs"></textarea>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12">
                            <div className="row" id="texts">
                                <input type="submit" value="Enviar dados" onClick="alterLocalStorage(document.getElementById('TestCell').value, document.getElementById('causal').value)"/>


                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default App;
