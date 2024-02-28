// import 'bootstrap/dist/css/bootstrap.css';
import './Card.css'
import React, {Component} from 'react';
import {Card, CardBody, CardFooter, CardHeader, CardText, CardTitle, Col, Row} from "reactstrap";
// import {CausalAtual} from "../Causal/CausalComponent";


class MyComponent extends Component {
    render() {
        const {status} = this.props;
        const cardHeaderClass = status === "Running" ? "card-header-green" : "card-header-red";

        return (
            <div className="container">
                <div>
                    <Card
                        className="my-0"
                        style={{
                            width: '15.5vw',
                            height: '100%',
                            minHeight: '28vh'
                        }}
                    >
                        <CardHeader className={cardHeaderClass} >
                            {this.props.testCell}
                        </CardHeader>
                        <CardBody className="card-body">
                            <CardTitle tag="p" className="card-status my-0 p-0 border-0 w-auto h-auto">
                                {/*{this.props.status_bool ? "Running" : Stop(this.props.testCell)}*/}
                                {this.props.status}
                            </CardTitle>
                            <CardText tag="div">
                                <Row className="my-2 align-content-center card-data w-auto h-auto">
                                    <Col className="card-percent w-auto h-auto">
                                        <p className="p-0 m-0 w-auto h-auto">Efficiency</p>
                                        <span className="display-6 w-auto h-auto">100%</span>
                                    </Col>
                                    <Col>
                                        <Row>Motor: {this.props.motor}</Row>
                                        <Row>Prova: {this.props.teste}</Row>
                                        <Row>Projeto: {this.props.projeto}</Row>
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