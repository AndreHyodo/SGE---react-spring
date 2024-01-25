import React, {useState, useEffect, useMemo} from 'react';
import {list_Causais} from "../../services/StatusService";



const hideNextSiblings = () => {
    const toggleButtons = document.querySelectorAll('.toggle-data-btn');

    toggleButtons.forEach((toggleButton) => {
        const cardBody = toggleButton.parentNode.nextElementSibling;
        cardBody.style.display = 'none';
    });
};

const renderData = (data) => {
    // Função renderData permanece sem alterações
};

// const App = () => {
//     const [data, setData] = useState([]);
//
//     // useEffect(() => {
//     //     const fetchDataAndRender = async () => {
//     //         const fetchedData = await list_Causais();
//     //         setData(fetchedData);
//     //         hideNextSiblings();
//     //     };
//     //
//     //     fetchDataAndRender();
//     // }, []); // O array vazio [] como segundo argumento garante que o useEffect é chamado apenas uma vez após a montagem do componente.
//
//     useEffect(() => {
//         list_Causais().then((response) => {
//             setData(response.data);
//         }).catch(error => {
//             console.log(error);
//         })
//     }, [data])
//
//     const handleToggleButtonClick = (event) => {
//         if (event.target.matches('.toggle-data-btn')) {
//             const cardBody = event.target.parentNode.nextElementSibling;
//             const toggleBtnText = event.target.textContent;
//
//             if (toggleBtnText === '+') {
//                 event.target.textContent = '-';
//                 cardBody.style.display = 'block';
//             } else {
//                 event.target.textContent = '+';
//                 cardBody.style.display = 'none';
//             }
//         }
//     };
//
//     return (
//         <div id="data-container">
//             {data.map((typeData,index) => (
//                 <div key={index} className="col-md-3">
//                     <div className="card mb-3">
//                         <div className="card-body">
//                             <h5 className="card-title">
//                                 {typeData.type}
//                                 <button
//                                     className="btn btn-primary btn-sm toggle-data-btn"
//                                     onClick={handleToggleButtonClick}
//                                 >
//                                     +
//                                 </button>
//                             </h5>
//                             <ul>
//                                 {/*{typeData.items.map((item, itemIndex) => (*/}
//                                 {/*    <li key={itemIndex}>*/}
//                                 {/*        <input*/}
//                                 {/*            type="checkbox"*/}
//                                 {/*            value={item.causal}*/}
//                                 {/*            onChange={() => {*/}
//                                 {/*                // Sua lógica de manipulação do estado aqui*/}
//                                 {/*            }}*/}
//                                 {/*        />*/}
//                                 {/*        <span>{` ${item.Code} ${item.causal} `}</span>*/}
//                                 {/*        /!* Restante do seu código de renderização *!/*/}
//                                 {/*    </li>*/}
//                                 {/*))}*/}
//                                 <li key={index}>
//                                     <input
//                                         type="checkbox"
//                                         value={typeData.causal}
//                                         onChange={() => {
//                                             // Sua lógica de manipulação do estado aqui
//                                         }}
//                                     />
//                                     <span>{` ${typeData.Code} ${typeData.causal} `}</span>
//                                     {/* Restante do seu código de renderização */}
//                                 </li>
//                             </ul>
//                         </div>
//                     </div>
//                 </div>
//             ))}
//         </div>
//     );
// };

// ... (código anterior)

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

    return (
        <div id="data-container">
            {groupedData.map((groupData, index) => (
                <div key={index} className="col-md-3">
                    <div className="card mb-3">
                        <div className="card-body">
                            <h5 className="card-title">
                                {groupData.Type}
                                <button
                                    className="btn btn-primary btn-sm toggle-data-btn"
                                    onClick={handleToggleButtonClick}
                                >
                                    +
                                </button>
                            </h5>
                            <ul style={{ display: 'none' }}>

                                {groupData.items.map((item, itemIndex) => (
                                    <li key={index}>
                                        <input
                                            type="checkbox"
                                            value={groupData.causal}
                                            onChange={() => {
                                                // Sua lógica de manipulação do estado aqui
                                            }}
                                        />
                                        <span>{` ${groupData.Code} ${groupData.causal} `}</span>
                                        {/* Restante do seu código de renderização */}
                                    </li>
                                ))}
                            </ul>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default App;
