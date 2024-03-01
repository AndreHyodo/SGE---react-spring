import React, {useState, useEffect, useMemo} from 'react';
import {list_Causais} from "../../services/StatusService";
import GetIP from "./getIP";
import axios from "axios";


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
        const formData = new FormData(e.target);
        const date = Date.now();
        const hora_inicio = `${hora}:${minuto}:${segundos}`;
        const testCell = formData.get('TestCell');
        const code = formData.get('Code');
        const causal = formData.get('causal');
        const obs = formData.get('obs');

        // const article = { testCell: testCell, Code: code, causal: causal, hora_inicio: hora_inicio, data: date, obs: obs };
        // const headers = {
        //     'Authorization': 'Bearer my-token',
        //     'My-Custom-Header': 'foobar',
        //     mode:"no-cors"
        // };
        // axios.post('http://172.28.251.102:8080/causais/insertCausal', article, { headers })
        //     .then(response => this.setState({ articleId: response.data.id }));
        //

        // try {
        //     const response = await fetch('http://172.28.251.102:8080/causais/insertCausal', {
        //         method: 'POST',
        //         // body: JSON.stringify({ testCell, code, causal, hora_inicio, data, obs}),
        //         body: JSON.stringify({ testCell, code, causal, obs}),
        //         headers: { 'Content-Type': 'application/json' },
        //         mode:"no-cors"
        //     });
        //
        //     if (!response.ok) {
        //         throw new Error(`Error submitting form: ${response.status}`);
        //     }
        //
        //     console.log('Form submitted successfully.');
        // } catch (error) {
        //     console.error('Error submitting form:', error);
        // }
    };


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
            <form action="" method="post" className="form" onSubmit={handleSubmit}>
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
                                {/*<input type="submit" value="Enviar dados" onClick="alterLocalStorage(document.getElementById('TestCell').value, document.getElementById('causal').value)"/>*/}
                                <input type="submit" value="Enviar dados"/>

                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default App;
