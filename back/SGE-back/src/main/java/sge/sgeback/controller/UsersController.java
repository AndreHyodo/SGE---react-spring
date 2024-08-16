package sge.sgeback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sge.sgeback.model.Users;
import sge.sgeback.repository.UsersRepository;

import java.util.Objects;


@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path="/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;


    @GetMapping(path="/verificaUser/{user}/{senha}")
    public @ResponseBody ResponseEntity<Boolean> validarUser(@PathVariable String user, @PathVariable int senha){
        Iterable<Users> users = usersRepository.findAll();
        Boolean encontrado = false;

        for(Users procurado : users){
            if(Objects.equals(procurado.getUsuario(), user) && procurado.getSenha()==senha){
                encontrado = true;
            }
        }

        if(encontrado){
            return  new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

}
