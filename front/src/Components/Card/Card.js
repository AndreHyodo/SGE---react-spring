import 'bootstrap/dist/css/bootstrap.css';
import './Card.css'
import React, {Component} from 'react';
import {Card, CardBody, CardFooter, CardHeader, CardText, CardTitle, Col, Row} from "reactstrap";

class MyComponent extends Component {

    render() {
        const {status_actual} = this.props;
        const {causalParada} = this.props;
        const {status} = this.props;
        const cardHeaderClass = () =>{
            if(status_actual === 1){
                return "card-header-run";
            }else if(status_actual === 2){
                return "card-header-cooling";
            }else if(status_actual === 3){
                return "card-header-inactive";
            }else if(status === "Aguardando causal"){
                return "card-header-wait";
            }else if(status_actual === 0){
                return "card-header-stop";
            }else{
                return "card-header-inactive";
            }
        }
        const effClass = this.props.status==="Sala inativa" ? "display-6 w-auto h-auto fw-bold text-secondary" :
                                    this.props.eff > 60 ? "display-6 w-auto h-auto fw-bold text-success" :
                                    "display-6 w-auto h-auto fw-bold text-danger";

        const handleClick = (myLink) => () => {
            // window.open(myLink, '_blank'); //Abre em outra aba do navegador
            window.location.href = myLink;
        }


        return (
            <div className="container" onClick={handleClick(`/detail/${this.props.testCell}`, this.props.testCell)}>
                <div>
                    <Card
                        className="my-0 h-auto w-auto"
                        style={{
                            // width: '15.5vw',
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
                                <Row className="my-0 align-content-center card-data w-auto h-auto ">
                                    <Col className="card-percent w-auto h-auto">
                                        <p className="p-0 m-0 w-auto h-auto">%Efficiency</p>
                                        <span className={effClass}>{this.props.eff}</span>
                                    </Col>
                                    <Col className="card-percent w-auto h-auto m-0">
                                        <Row >Motor: {this.props.motor}</Row>
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


export default MyComponent;