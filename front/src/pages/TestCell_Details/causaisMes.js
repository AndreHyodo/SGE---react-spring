import React, { useEffect, useState } from "react";
import {
    causais_mes,
    eff_hist_turno,
    causais_date,
    causaisHour_date,
    getStatusTestCell,
    causaisHourFormatted_date
} from "../../services/StatusService";
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    Rectangle,
    PieChart,
    LineChart,
    Pie,
    Line,
    ResponsiveContainer,
} from "recharts";
import "./details.css";
import data from "bootstrap/js/src/dom/data";
import {Text, Box, ThemeProvider} from "@chakra-ui/react";
import {Button} from "reactstrap";

const CausaisMes = () => {
    const [causaisFormatted, setCausaisFormatted] = useState([]);
    const [causais, setCausais] = useState([]);
    const [selectedDate, setSelectedDate] = useState(null);

    const getCausais = async (testCell, date) => {
        try {
            let response = null;
            let responseString = null;
            const currentDate = new Date();
            const formattedDate = currentDate.toISOString().split('T')[0];
            if (date === null) {
                response = await causaisHour_date(testCell, formattedDate); // Getting response with time in Long (sec)
                responseString = await causaisHourFormatted_date(testCell,formattedDate); //Getting response with time in String
            } else {
                response = await causaisHour_date(testCell, date); // Getting response with time in Long (sec)
                responseString = await causaisHourFormatted_date(testCell,date); //Getting response with time in String
            }

            const causaisData = Object.entries(response.data).map(([causal, seconds]) => ({
                name: causal,
                seconds: seconds,
                // secondsFormatted: convertHMS(seconds),
            }));

            const causaisDataFormatted = Object.entries(responseString.data).map(([causal, time_st]) => ({
                name: causal,
                time_st: time_st,
            }));

            // Sort data by seconds in descending order
            causaisData.sort((a, b) => b.seconds - a.seconds);


            setCausais(causaisData);
            setCausaisFormatted(causaisDataFormatted);
        } catch (error) {
            console.error("Error fetching causais data:", error);
        }
    };

    function convertHMS(value) {
        let hours   = Math.floor(value/3600); // get hours
        let minutes = Math.floor((value-(hours*3600))/60); // get minutes
        let seconds = (value-(hours*3600)-(minutes*60)); //  get seconds
        // add 0 if value < 10
        if (hours   < 10) {hours   = "0"+hours;}
        if (minutes < 10) {minutes = "0"+minutes;}
        if (seconds < 10) {seconds = "0"+seconds;}
        return hours+':'+minutes+':'+seconds; // Return is HH : MM : SS
    }

    const url = window.location.href;
    const urlParts = url.split("/");
    const testCell = urlParts[urlParts.length - 1];

    useEffect(() => {
        if (testCell) {
            getCausais(testCell, null);
        }
    }, []);

    const [effData0, setEffData0] = useState([]);
    const [effData1, setEffData1] = useState([]);
    const [effData2, setEffData2] = useState([]);
    const [effData3, setEffData3] = useState([]);

    const getEffData = async (SPM, turno) => {
        try {
            const response = await eff_hist_turno(SPM, turno);
            console.log(response.data);
            return response.data;
        } catch (error) {
            console.error("Error fetching eff_hist_turno data:", error);
            return [];
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            const data0 = await getEffData(testCell, "0");
            setEffData0(
                data0.map((item) => ({
                    ...item,
                    date: new Date(item.date).toLocaleDateString(),
                }))
            );
            const data1 = await getEffData(testCell, "1");
            setEffData1(
                data1.map((item) => ({
                    ...item,
                    date: new Date(item.date).toLocaleDateString(),
                }))
            );
            const data2 = await getEffData(testCell, "2");
            setEffData2(
                data2.map((item) => ({
                    ...item,
                    date: new Date(item.date).toLocaleDateString(),
                }))
            );
            const data3 = await getEffData(testCell, "3");
            setEffData3(
                data3.map((item) => ({
                    ...item,
                    date: new Date(item.date).toLocaleDateString(),
                }))
            );
        };
        fetchData();
    }, [testCell]);

    const handleLineClick = (date) => {
        const dateArray = date.split("/");
        const newDate = `${dateArray[2]}-${dateArray[1]}-${dateArray[0]}`;
        setSelectedDate(newDate);
        if (testCell) {
            getCausais(testCell, newDate);
        }
    };

    if (causais.length === 0 && effData1.length === 0) {
        return <h1>Loading...</h1>;
    }

    return (
        <div className="App">
            <div className="title-container">
                <div className="title">
                    <Box
                        w='100%'
                        h='80vh'
                        bgGradient='linear(to-r, gray.300, #24405d)'
                        display='flex'
                        alignItems='center'
                        borderRadius='10px'
                        marginBottom='15%'
                    >
                        <Text
                            w='100%'
                            bgGradient='linear(to-r, #24405d, #2E2E2E)'
                            bgClip='text'
                            fontSize='6xl'
                            fontWeight='extrabold'
                        >
                            {testCell}
                        </Text>
                    </Box>
                    {/*<div className="title-text">{testCell} Details</div>*/}
                </div>
                <div className="charts">
                    <div className="barchart-container">
                        <ResponsiveContainer width="50%" height="100%">
                            <LineChart
                                data={effData0}
                                margin={{
                                    top: 50,
                                    right: 30,
                                    left: 20,
                                    bottom: 5,
                                }}
                            >
                                <text
                                    x={"50%"}
                                    y={"8%"}
                                    textAnchor="middle"
                                    fontSize={18}
                                    fontWeight="bold"
                                    fill="white"
                                >
                                    Eficiência {testCell}
                                </text>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis
                                    dataKey="date"
                                    tick={{
                                        fill: "white",
                                        fontSize: 12,
                                        fontWeight: "normal",
                                    }}
                                />
                                <YAxis
                                    tick={{ fill: "white", fontSize: 12, fontWeight: "normal" }}
                                    domain={[0, 100]}
                                />
                                <Tooltip
                                    cursor={{
                                        stroke: "#ccc",
                                        strokeWidth: 4,
                                        strokeDasharray: 0,
                                    }}
                                    wrapperStyle={{
                                        border: "none",
                                        boxShadow:
                                            "0px 0px 10px rgba(0, 0, 0, 0.5)",
                                        borderRadius: "5px",
                                        padding: "10px",
                                    }}
                                    labelStyle={{
                                        color: "#FFFA00",
                                        fontSize: 14,
                                        fontWeight: "bold",
                                    }}
                                    contentStyle={{
                                        color: "#FFFA00",
                                        fontSize: 12,
                                        fontWeight: "normal",
                                        backgroundColor:"black",
                                    }}
                                />
                                <Line
                                    type="monotone"
                                    dataKey="eff"
                                    stroke="#FFFA00"
                                    // activeDot={{ r: 8 }}
                                    activeDot={{r: 8 , onClick: (event, payload) => handleLineClick(payload["payload"].date)}}
                                />
                            </LineChart>
                        </ResponsiveContainer>
                        <ResponsiveContainer width="50%" height="100%">
                            <BarChart
                                data={causais}
                                margin={{ top: 50, right: 30, left: 20, bottom: 5 }}
                            >
                                <text
                                    x={"50%"}
                                    y={"8%"}
                                    textAnchor="middle"
                                    fontSize={18}
                                    fontWeight="bold"
                                    fill="white"
                                >
                                    Causais
                                </text>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis
                                    dataKey="name"
                                    angle={-45}
                                    textAnchor="end"
                                    interval={0}
                                    height={125}
                                    style={{
                                        fontSize: 12,
                                        overflow: "visible",
                                        whiteSpace: "pre-wrap",
                                    }}
                                    tick={{
                                        fill: "white",
                                        fontSize: 12,
                                        fontWeight: "normal",
                                    }}
                                />
                                <YAxis
                                    dataKey="seconds"
                                    tickFormatter={(seconds) => convertHMS(seconds)} // Format seconds to HMS
                                    tick={{ fill: "white", fontSize: 12, fontWeight: "normal" }}
                                />
                                <Tooltip
                                    cursor={{
                                        stroke: "#ccc",
                                        strokeWidth: 4,
                                        strokeDasharray: 0,
                                    }}
                                    wrapperStyle={{
                                        border: "none",
                                        boxShadow:
                                            "0px 0px 10px rgba(0, 0, 0, 0.5)",
                                        borderRadius: "5px",
                                        padding: "10px",
                                    }}
                                    labelStyle={{
                                        color: "#333",
                                        fontSize: 14,
                                        fontWeight: "bold",
                                    }}
                                    contentStyle={{
                                        color: "#333",
                                        fontSize: 12,
                                        fontWeight: "normal",
                                    }}
                                    formatter={(value) => convertHMS(value)} // Format seconds to HMS
                                />
                                {/*<Legend />*/}
                                <Bar
                                    dataKey="seconds"
                                    name="Total time"
                                    fill="#63A9FF"
                                    activeBar={<Rectangle fill="pink" stroke="blue" />}
                                />
                            </BarChart>
                        </ResponsiveContainer>
                    </div>
                    <div className="linechart-container">
                        <ResponsiveContainer width="100%" height={350}>
                            <LineChart
                                data={effData1}
                                margin={{ top: 30, right: 30, left: 20, bottom: 5 }}
                            >
                                <text
                                    x={"50%"}
                                    y={15}
                                    textAnchor="middle"
                                    fontSize={18}
                                    fontWeight="bold"
                                    fill="white"
                                >
                                    Eficiência 1º Turno
                                </text>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis
                                    dataKey="date"
                                    tick={{
                                        fill: "white",
                                        fontSize: 12,
                                        fontWeight: "normal",
                                    }}
                                />
                                <YAxis
                                    tick={{ fill: "white", fontSize: 12, fontWeight: "normal" }}
                                    domain={[0, 100]}
                                />
                                <Tooltip
                                    wrapperStyle={{
                                        border: "none",
                                        boxShadow:
                                            "0px 0px 10px rgba(0, 0, 0, 0.5)",
                                        borderRadius: "5px",
                                        padding: "10px",
                                    }}
                                    labelStyle={{
                                        color: "#FFFA00",
                                        fontSize: 14,
                                        fontWeight: "bold",
                                    }}
                                    contentStyle={{
                                        color: "#FFFA00",
                                        fontSize: 12,
                                        fontWeight: "normal",
                                        backgroundColor:"black",
                                    }}
                                />
                                <Line
                                    type="monotone"
                                    dataKey="eff"
                                    stroke="#FFFA00"
                                    activeDot={{ r: 8 }}
                                />
                            </LineChart>
                        </ResponsiveContainer>
                        <ResponsiveContainer width="100%" height={350}>
                            <LineChart
                                data={effData2}
                                margin={{ top: 30, right: 30, left: 20, bottom: 5 }}
                            >
                                <text
                                    x={"50%"}
                                    y={15}
                                    textAnchor="middle"
                                    fontSize={18}
                                    fontWeight="bold"
                                    fill="white"
                                >
                                    Eficiência 2º Turno
                                </text>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis
                                    dataKey="date"
                                    tick={{
                                        fill: "white",
                                        fontSize: 12,
                                        fontWeight: "normal",
                                    }}
                                />
                                <YAxis
                                    tick={{ fill: "white", fontSize: 12, fontWeight: "normal" }}
                                    domain={[0, 100]}
                                />
                                <Tooltip
                                    wrapperStyle={{
                                        border: "none",
                                        boxShadow:
                                            "0px 0px 10px rgba(0, 0, 0, 0.5)",
                                        borderRadius: "5px",
                                        padding: "10px",
                                    }}
                                    labelStyle={{
                                        color: "#FFFA00",
                                        fontSize: 14,
                                        fontWeight: "bold",
                                    }}
                                    contentStyle={{
                                        color: "#FFFA00",
                                        fontSize: 12,
                                        fontWeight: "normal",
                                        backgroundColor:"black",
                                    }}
                                />
                                <Line
                                    type="monotone"
                                    dataKey="eff"
                                    stroke="#FFFA00"
                                    activeDot={{ r: 8 }}
                                />
                            </LineChart>
                        </ResponsiveContainer>
                        {/*{type === "dur" && (*/}
                            <ResponsiveContainer width="100%" height={350}>
                                <LineChart
                                    data={effData3}
                                    margin={{ top: 30, right: 30, left: 20, bottom: 5 }}
                                >
                                    <text
                                        x={"50%"}
                                        y={15}
                                        textAnchor="middle"
                                        fontSize={18}
                                        fontWeight="bold"
                                        fill="white"
                                    >
                                        Eficiência 3º Turno
                                    </text>
                                    <CartesianGrid strokeDasharray="3 3" />
                                    <XAxis
                                        dataKey="date"
                                        tick={{
                                            fill: "white",
                                            fontSize: 12,
                                            fontWeight: "normal",
                                        }}
                                    />
                                    <YAxis
                                        tick={{ fill: "white", fontSize: 12, fontWeight: "normal" }}
                                        domain={[0, 100]}
                                    />
                                    <Tooltip
                                        wrapperStyle={{
                                            border: "none",
                                            boxShadow:
                                                "0px 0px 10px rgba(0, 0, 0, 0.5)",
                                            borderRadius: "5px",
                                            padding: "10px",
                                        }}
                                        labelStyle={{
                                            color: "#FFFA00",
                                            fontSize: 14,
                                            fontWeight: "bold",
                                        }}
                                        contentStyle={{
                                            color: "#FFFA00",
                                            fontSize: 12,
                                            fontWeight: "normal",
                                            backgroundColor:"black",
                                        }}
                                    />
                                    <Line
                                        type="monotone"
                                        dataKey="eff"
                                        stroke="#FFFA00"
                                        activeDot={{ r: 8 }}
                                    />
                                </LineChart>
                            </ResponsiveContainer>
                        {/*)}*/}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CausaisMes;