import React, {useState} from 'react';
import {
    Flex,
    Heading,
    Input,
    Button,
    FormControl,
    FormLabel,
    Switch,
    useColorMode,
    useColorModeValue,
} from '@chakra-ui/react';
import logo from '../../img/logoStellantis.png';
import axios from "axios";
import {useNavigate} from "react-router-dom";

const App = () => {

    const [user, setUser] = useState('');
    const [senha, setSenha] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await
                axios.get(`users/verificaUser/${user}/${senha}`);
            if (response.data) {
                // Login successful
                console.log('Login successful');
                console.log(response.data.token);
                setErrorMessage("");
                sessionStorage.setItem('TOKEN_OK', response.data.token);
                window.location.reload();
            } else {
                setErrorMessage('Usuário ou senha inválidos');
            }
        } catch (error) {
            console.error('Error:', error);
            setErrorMessage('Ocorreu um erro durante a verificação');
        }
    };

    const { toggleColorMode } = useColorMode();
    const formBackground = '#0d2445';

    return (
        <Flex h="80vh" alignItems="center" justifyContent="center" flexDirection="column">
            <Flex
                flexDirection="column"
                bg={formBackground}
                p={12}
                borderRadius={8}
                boxShadow="lg"
            >
                <Heading textAlign="center" mb={6} style={{ color: "white" }}>
                    Log In
                </Heading>
                <Input
                    placeholder="Usuário"
                    variant=""
                    mb={3}
                    style={{ color: "black" }}
                    value={user}
                    onChange={(e) => setUser(e.target.value)}
                />
                <Input
                    placeholder="**********"
                    type="password"
                    variant="filled"
                    mb={6}
                    style={{ color: "white" }}
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                />
                <Button colorScheme="teal" mb={8} onClick={handleSubmit}>
                    Log In
                </Button>
                {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
            </Flex>
            <img src={logo} alt="Logo Stellantis" />
        </Flex>
    );
};

export default App;