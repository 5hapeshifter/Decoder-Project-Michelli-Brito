package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2 // Esta no pacote do lombok ja
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {


    // Objeto para capturar os logs da classe.
    // COM O LOMBOK NAO PRECISAMOS FICAR INSTANCIANDO OS OBJETOS ABAIXO, POIS ELE JA TEM DISPONIVEL, PRECISAMOS APENAS ANOTAR A CLASSE COM @Log4j2
    // Logger logger = LoggerFactory.getLogger(AuthenticationController.class); // Logger do LOGBack
    // Logger logger = LogManager.getLogger(AuthenticationController.class); // Logger do log4j

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(
            @RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) { // Essa anotação define qual visão ele deve validar, nesse caso RegistrationPost
        // Exemplo de log, utilizando as chaves nao ficamos restritos aos tipos primitivos, as chaves serao substituidas pelo segundo objeto que passarmos, userBto nesse caso
        log.debug("POST - registerUser - userDto received {}", userDto.toString());

        if (userService.existsByUserName(userDto.getUsername())) {
            log.warn("Username {} is already taken.", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if (userService.existsByEmail(userDto.getEmail())) {
            log.warn("Email {} is already taken.", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel); // método responsável por copiar os dados do userDto para userModel
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(userModel);
        // Exemplo de log, utilizando as chaves nao ficamos restritos aos tipos primitivos, as chaves serao substituidas pelo segundo objeto que passarmos, userBto nesse caso
        log.debug("POST - registerUser - userId saved {}", userModel.getUserId());
        log.warn("User saved successfully userId {}", userModel.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @GetMapping("/")
    public String index(){
        // Os logs são definidos por níveis, portanto, como não configuramos nada e o padrao default do Spring e logger.info, ele vai trazer os niveis abaixo, warn e error por exemplo
        // Se quisermos informações pelo logger.trace ou .debug, temos que definir isso em arquivos de configuracao
        log.trace("TRACE");  // logger.trace traz os minimos detalhes
        log.debug("DEBUG");  // logger.debug informações detalhadas, mais utilizado em ambiente de DEV
        log.info("INFO");    // logger.info traz informações menos detalhadas, tipo default do Spring
        log.warn("WARN");    // logger.warn se tiver perda de dados, conflitos, processos repetidos, vai trazer um alerta
        log.error("ERROR");  // logger.error quando ocorrer um erro, uma exception for lançada, sera informado o que está acontecendo
//        try{
//            throw new Exception("Exception message");
//        } catch (Exception e){
//            log.error("--------------- ERROR -------------", e);
//        }
        return "Logging Spring Boot...";
    }

}
