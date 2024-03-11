import 'bootstrap/dist/css/bootstrap.css';
import './Card.css'
import React, {Component} from 'react';
import {Card, CardBody, CardFooter, CardHeader, CardText, CardTitle, Col, Row} from "reactstrap";
// import {CausalAtual} from "../Causal/CausalComponent";

class MyComponent extends Component {

    render() {
        const {status} = this.props;
        const cardHeaderClass = () =>{
            if(status === "Running"){
                return "card-header-run";
            }else if(status === "Cooling"){
                return "card-header-cooling";
            }else if(status === "Sala inativa"){
                return "card-header-inactive";
            }else{
                return "card-header-stop";
            }
        }
        const effClass = this.props.status==="Sala inativa" ? "display-6 w-auto h-auto fw-bold text-secondary" :
                                    this.props.eff > 60 ? "display-6 w-auto h-auto fw-bold text-success" :
                                    "display-6 w-auto h-auto fw-bold text-danger";

        const handleClick = (myLink) => () => {
            window.open(myLink, '_blank');
        }


        return (
            <div className="container" onClick={handleClick('/detail')}>
                <div>
                    <Card
                        className="my-0"
                        style={{
                            width: '15.5vw',
                            height: '100%',
                            minHeight: '28vh'
                        }}
                    >
                        <CardHeader className={cardHeaderClass()} >
                            {this.props.testCell}
                        </CardHeader>
                        <CardBody className="card-body">
                            <CardTitle tag="p" className="card-status my-0 p-0 border-0 w-auto h-auto ">
                                {this.props.status}
                            </CardTitle>
                            <CardText tag="div">
                                <Row className="my-2 align-content-center card-data w-auto h-auto">
                                    <Col className="card-percent w-auto h-auto">
                                        <p className="p-0 m-0 w-auto h-auto">Efficiency</p>
                                        <span className={effClass}>{this.props.eff}%</span>
                                    </Col>
                                    <Col>
                                        <Row>Motor: {this.props.motor}</Row>
                                        <Row>Prova: {this.props.teste}</Row>
                                        <Row>Projeto: {this.props.projeto}</Row>
                                    </Col>
                                </Row>
                            </CardText>
                        </CardBody>
                        <CardFooter className="card-footer my-0 p-0 align-content-center h-auto">
                            <Row className="w-100">
                                <Col className="d-flex justify-content-center display-8">
                                    <span className="p-1">Atual: {this.props.paradaAtual}</span>
                                </Col>
                                <Col className="d-flex justify-content-center display-8">
                                    <span className="p-1">Total: {this.props.paradaTotal}</span>
                                </Col>
                            </Row>
                        </CardFooter>
                    </Card>
                </div>
            </div>
        );
    }
}


// function Stop(sala){
//
//
//     return sala
// }

export default MyComponent;