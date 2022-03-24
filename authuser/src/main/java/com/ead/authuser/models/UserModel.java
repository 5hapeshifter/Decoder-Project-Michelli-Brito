package com.ead.authuser.models;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // todas as vezes que for preciso fazer a serialização de atributo nulo, o campo será ocultado
@Entity
@Table(name = "TB_USERS")
public class UserModel extends RepresentationModel implements Serializable { // Quando utilizarmos a herança, todas as classes serão serializadas para evitar possiveis erros

    private static final long serialVersionUID = 1L; // numero de controle de versionamento das conversoes feitas pela JVM, e sera utilizado para verificar os atributos são dessa classe

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId; // melhora a manutenibilidade, pode ser gerado em qualquer lugar, facilita a réplica de dados para transferencia de dados
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 255)
    @JsonIgnore // Estamos definindo que esse atributo não será retornado para garantir a segurança do dado
    private String password;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // estamos definindo o tipo do dado que será salvo
    private UserStatus userStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 20)
    private String cpf;
    @Column
    private String imageUrl;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

}
