// import 'bootstrap/dist/css/bootstrap.css';
import './Card.css'
import React, {Component} from 'react';
import {Card, CardBody, CardFooter, CardHeader, CardText, CardTitle, Col, Row} from "reactstrap";
// import {CausalAtual} from "../Causal/CausalComponent";


class MyComponent extends Component {
    render() {
        return (
            <div className="container">
                <div>
                    <Card
                        className="my-0"
                        style={{
                            width: '15.5vw',
                            height: '27vh'
                        }}
                    >
                        <CardHeader className="card-title">
                            {this.props.testCell}
                        </CardHeader>
                        <CardBody className="card-body">
                            <CardTitle tag="p" className="card-status my-0 p-0 border-0">
                                {/*{this.props.status_bool ? "Running" : Stop(this.props.testCell)}*/}
                                {this.props.status}
                            </CardTitle>
                            <CardText>
                                <Row className="my-2 align-content-center card-data">
                                    <Col className="card-percent">
                                        <h2>100%</h2>
                                    </Col>
                                    <Col>
                                        <Row>Motor: </Row>
                                        <Row>Prova: </Row>
                                        <Row>Projeto: </Row>
                                    </Col>
                                </Row>
                            </CardText>
                        </CardBody>
                        <CardFooter className="card-footer my-0 p-0 align-content-center">
                            <Row>
                                <Col>
                                    <p>atual</p>
                                </Col>
                                <Col>
                                    <p>total</p>
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