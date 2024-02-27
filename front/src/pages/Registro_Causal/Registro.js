import React, {useState, useEffect, useMemo} from 'react';
import {list_Causais} from "../../services/StatusService";
import GetIP from "./getIP";


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


    function checkboxCausal(causal, code) {
        // Obtenha os elementos do DOM
        const causalElement = document.getElementById('causal');
        const codeElement = document.getElementById('Code');
        const causal_selected = document.getElementById("checkbox causal");

        // Atualize os valores com base no estado da caixa de seleção
        if (causal_selected.checked) {
            causalElement.value = causal;
            codeElement.value = code;
        } else {
            causalElement.value = '';
            codeElement.value = '';
        }
    }


    const [checkedState, setCheckedState] = useState(
        new Array(30).fill(false)
    );

    // function checkboxOFF(position){
    //     checkedState.map((item, index) =>
    //         index !== position ? setCheckedState(false) : null
    //     );
    //     console.log(checkedState);
    // }

    const handleOnChange = (code,causal,position) => {

        console.log(position + " - "+ checkedState[position] + " e " + causal + "------" + checkedState)
        // const updatedCheckedState = checkedState.map((item, index) =>
        //     index === position ? !item : item
        // );

        for (let index in checkedState)
            if(index == position){
                checkedState[index] = !checkedState[index]
            }

        // setCheckedState(updatedCheckedState);

        // setCheckedState(!checkedState[item]);
        if(checkedState[position]){
            document.getElementById('causal').value = causal
            document.getElementById('Code').value = code
        }else{
            document.getElementById('causal').value = ""
            document.getElementById('Code').value = ""
        }

    }


    return (
        <div id="data-container">
            <div className="w-100 m-3 text-center w-50">
                <input className="w-25 text-center" type="text" id="busca" placeholder="Pesquise o Causal..." onChange={handleSearchChange}/>
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
            <form action="" method="post" className="form">
                <div className="form-input">
                    <div className="row">
                        <div className="col-md-3">
                            <div className="row" id="texts">
                                <label htmlFor="TestCell">TestCell:</label>
                            </div>
                            <div className="row" id="inputs">
                                <input type="text" name="TestCell" id="TestCell" value={GetIP()}/>
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
                                <textarea name="obs" id="obs" cols="30" rows="2" className="obs"></textarea>
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
