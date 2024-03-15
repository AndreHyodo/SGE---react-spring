// import React, {useEffect,useState} from "react";
// import { listStatus} from "../../services/StatusService";
// import {PowerBIEmbed, PowerEmbed} from 'powerbi-client-react';
// import { models } from 'powerbi-client';
// import './AppPowerBI.css';
//
// const StatusSalas = () => {
//
//     const[Salas, setSalas] = useState([])
//     // const[Dados, setDados] = useState([])
//
//     useEffect(() => {
//         listStatus().then((response) => {
//             setSalas(response.data);
//         }).catch(error => {
//             console.log(error);
//         })
//     }, [Salas])
//
//     return(
//         <div className="">
//             <section className="App">
//                 <h1>A01 Details</h1>
//                 <section id="bi-report">
//                     <PowerBIEmbed
//                         embedConfig = {{
//                             type: 'report',   // Since we are reporting a BI report, set the type to report
//                             // id: 'c01504e1-76a9-4575-a971-a81cc1004df4', // Add the report Id here
//                             id : 'dc013ece-c46d-4fb0-908f-c3669b6b7091',
//                             // embedUrl: 'https://app.powerbi.com/reportEmbed?reportId=c01504e1-76a9-4575-a971-a81cc1004df4&config=eyJjbHVzdGVyVXJsIjoiaHR0cHM6Ly9XQUJJLU5PUlRILUVVUk9QRS1HLVBSSU1BUlktcmVkaXJlY3QuYW5hbHlzaXMud2luZG93cy5uZXQiLCJlbWJlZEZlYXR1cmVzIjp7InVzYWdlTWV0cmljc1ZOZXh0Ijp0cnVlLCJkaXNhYmxlQW5ndWxhckpTQm9vdHN0cmFwUmVwb3J0RW1iZWQiOnRydWV9fQ%3d%3d', // Add the embed url here
//                             embedUrl: 'https://app.powerbi.com/reportEmbed?reportId=dc013ece-c46d-4fb0-908f-c3669b6b7091&config=eyJjbHVzdGVyVXJsIjoiaHR0cHM6Ly9XQUJJLU5PUlRILUVVUk9QRS1HLVBSSU1BUlktcmVkaXJlY3QuYW5hbHlzaXMud2luZG93cy5uZXQiLCJlbWJlZEZlYXR1cmVzIjp7InVzYWdlTWV0cmljc1ZOZXh0Ijp0cnVlLCJkaXNhYmxlQW5ndWxhckpTQm9vdHN0cmFwUmVwb3J0RW1iZWQiOnRydWV9fQ%3d%3d',
//                             // accessToken: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IlhSdmtvOFA3QTNVYVdTblU3Yk05blQwTWpoQSIsImtpZCI6IlhSdmtvOFA3QTNVYVdTblU3Yk05blQwTWpoQSJ9.eyJhdWQiOiJodHRwczovL2FuYWx5c2lzLndpbmRvd3MubmV0L3Bvd2VyYmkvYXBpIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvMTRjYmQ1YTctZWM5NC00NmJhLWIzMTQtY2MwZmM5NzJhMTYxLyIsImlhdCI6MTcxMDE5NTk4MCwibmJmIjoxNzEwMTk1OTgwLCJleHAiOjE3MTAyMDA4NTgsImFjY3QiOjAsImFjciI6IjEiLCJhaW8iOiJBVFFBeS84V0FBQUErTnppaGc5RmZKMGhidGpoZVkyT1FKaVdZUjRBT3JoSkZCOHFZelA0WkN0T2QyMm95bWpBc2VWcmZXTVp6bVYyIiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6Ijg3MWMwMTBmLTVlNjEtNGZiMS04M2FjLTk4NjEwYTdlOTExMCIsImFwcGlkYWNyIjoiMCIsImZhbWlseV9uYW1lIjoiSHlvZG8iLCJnaXZlbl9uYW1lIjoiQW5kcsOpIiwiaXBhZGRyIjoiMTg2LjI0OC43OS41MCIsIm5hbWUiOiJBbmRyw6kgVGVpaWNoaSBTYW50b3MgSHlvZG8iLCJvaWQiOiJmYWRhOTQ4MS1iZmZlLTQ3YTUtOWVlMS0zOWQ4N2I2NWU0NWYiLCJvbnByZW1fc2lkIjoiUy0xLTUtMjEtMjAxMzg5NjE3My05MzExNTM1MDItNDExODEwMDE3Ni0zMjc1NDIiLCJwdWlkIjoiMTAwMzIwMDI4NEUyQzE2RiIsInJoIjoiMC5BUkVBcDlYTEZKVHN1a2F6Rk13UHlYS2hZUWtBQUFBQUFBQUF3QUFBQUFBQUFBQVJBRTguIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic2lnbmluX3N0YXRlIjpbImttc2kiXSwic3ViIjoiRE5QVVlXdmpwWTFCblJqZWpYTGFTelBRSm05Q1liS21FUjEtZ1VVWkJLWSIsInRpZCI6IjE0Y2JkNWE3LWVjOTQtNDZiYS1iMzE0LWNjMGZjOTcyYTE2MSIsInVuaXF1ZV9uYW1lIjoiMTA2NjIwMkBzZ2EucHVjbWluYXMuYnIiLCJ1cG4iOiIxMDY2MjAyQHNnYS5wdWNtaW5hcy5iciIsInV0aSI6InU2TEJQUzR4WTBDZGNJNE1DazVHQUEiLCJ2ZXIiOiIxLjAiLCJ3aWRzIjpbImI3OWZiZjRkLTNlZjktNDY4OS04MTQzLTc2YjE5NGU4NTUwOSJdLCJ4bXNfY2MiOlsiQ1AxIl19.cgx3Llgp7oKJLEFOBS5bIusVUHkzM_2ASNFkwWXkz4wwcwh_wkZo1Tsad2i1S-2OIAXeXgwNcI2PZxdZT2bqJlUHBt6-T74rHep9ozJGpzQTV8GBxvPE4ntRNN3_eclNRLcbCtNHa6mowXh4MxnmKEiwoSbAVOHqSrqbYQdoiU3fvYvWBGfIdkj2R2e02L-4n_2V3cknNxXFmjl5CvRfZAjSkWsVj-4VryQJlY-akpjwFzpXb4U6ja0lroKecJOhdYjUKkFkRuzieixrAtwpddZa0DL39SbHExghSIiQMepUG1dxo75IOqqt3_Qj84FABSH4wNrDpaaDO2LOSDAS3A', // Add the access token here
//                             accessToken: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IlhSdmtvOFA3QTNVYVdTblU3Yk05blQwTWpoQSIsImtpZCI6IlhSdmtvOFA3QTNVYVdTblU3Yk05blQwTWpoQSJ9.eyJhdWQiOiJodHRwczovL2FuYWx5c2lzLndpbmRvd3MubmV0L3Bvd2VyYmkvYXBpIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvMTRjYmQ1YTctZWM5NC00NmJhLWIzMTQtY2MwZmM5NzJhMTYxLyIsImlhdCI6MTcxMDE5NTk4MCwibmJmIjoxNzEwMTk1OTgwLCJleHAiOjE3MTAyMDA4NTgsImFjY3QiOjAsImFjciI6IjEiLCJhaW8iOiJBVFFBeS84V0FBQUErTnppaGc5RmZKMGhidGpoZVkyT1FKaVdZUjRBT3JoSkZCOHFZelA0WkN0T2QyMm95bWpBc2VWcmZXTVp6bVYyIiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6Ijg3MWMwMTBmLTVlNjEtNGZiMS04M2FjLTk4NjEwYTdlOTExMCIsImFwcGlkYWNyIjoiMCIsImZhbWlseV9uYW1lIjoiSHlvZG8iLCJnaXZlbl9uYW1lIjoiQW5kcsOpIiwiaXBhZGRyIjoiMTg2LjI0OC43OS41MCIsIm5hbWUiOiJBbmRyw6kgVGVpaWNoaSBTYW50b3MgSHlvZG8iLCJvaWQiOiJmYWRhOTQ4MS1iZmZlLTQ3YTUtOWVlMS0zOWQ4N2I2NWU0NWYiLCJvbnByZW1fc2lkIjoiUy0xLTUtMjEtMjAxMzg5NjE3My05MzExNTM1MDItNDExODEwMDE3Ni0zMjc1NDIiLCJwdWlkIjoiMTAwMzIwMDI4NEUyQzE2RiIsInJoIjoiMC5BUkVBcDlYTEZKVHN1a2F6Rk13UHlYS2hZUWtBQUFBQUFBQUF3QUFBQUFBQUFBQVJBRTguIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic2lnbmluX3N0YXRlIjpbImttc2kiXSwic3ViIjoiRE5QVVlXdmpwWTFCblJqZWpYTGFTelBRSm05Q1liS21FUjEtZ1VVWkJLWSIsInRpZCI6IjE0Y2JkNWE3LWVjOTQtNDZiYS1iMzE0LWNjMGZjOTcyYTE2MSIsInVuaXF1ZV9uYW1lIjoiMTA2NjIwMkBzZ2EucHVjbWluYXMuYnIiLCJ1cG4iOiIxMDY2MjAyQHNnYS5wdWNtaW5hcy5iciIsInV0aSI6InU2TEJQUzR4WTBDZGNJNE1DazVHQUEiLCJ2ZXIiOiIxLjAiLCJ3aWRzIjpbImI3OWZiZjRkLTNlZjktNDY4OS04MTQzLTc2YjE5NGU4NTUwOSJdLCJ4bXNfY2MiOlsiQ1AxIl19.cgx3Llgp7oKJLEFOBS5bIusVUHkzM_2ASNFkwWXkz4wwcwh_wkZo1Tsad2i1S-2OIAXeXgwNcI2PZxdZT2bqJlUHBt6-T74rHep9ozJGpzQTV8GBxvPE4ntRNN3_eclNRLcbCtNHa6mowXh4MxnmKEiwoSbAVOHqSrqbYQdoiU3fvYvWBGfIdkj2R2e02L-4n_2V3cknNxXFmjl5CvRfZAjSkWsVj-4VryQJlY-akpjwFzpXb4U6ja0lroKecJOhdYjUKkFkRuzieixrAtwpddZa0DL39SbHExghSIiQMepUG1dxo75IOqqt3_Qj84FABSH4wNrDpaaDO2LOSDAS3A',
//                             tokenType: models.TokenType.Aad, // Since we are using an Azure Active Directory access token, set the token type to Aad
//                             settings: {
//                                 panes: {
//                                     filters: {
//                                         expanded: false,
//                                         visible: true
//                                     }
//                                 },
//                                 background: models.BackgroundType.Transparent,
//                             }
//                         }}
//
//                         eventHandlers = {
//                             new Map([
//                                 ['loaded', function () {console.log('Report loaded');}],
//                                 ['rendered', function () {console.log('Report rendered');}],
//                                 ['error', function (event) {console.log(event.detail);}],
//                                 ['visualClicked', () => console.log('visual clicked')],
//                                 ['pageChanged', (event) => console.log(event)],
//                             ])
//                         }
//
//                         cssClassName = { "bi-embedded" }
//
//                         getEmbeddedComponent = { (embeddedReport) => {
//                             window.report = embeddedReport; // save report in window object
//                         }}
//                     />
//                 </section>
//             </section>
//         </div>
//     )
// }
//
// export default StatusSalas;
//
