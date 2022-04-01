package com.ead.authuser.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // todas as vezes que for preciso fazer a serialização de atributo nulo, o campo será ocultado
@Entity
@Table(name = "TB_USERS_COURSES")
public class UserCourseModel  extends RepresentationModel implements Serializable { // Quando utilizarmos a herança, todas as classes serão serializadas para evitar possiveis erros

    private static final long serialVersionUID = 1L; // numero de controle de versionamento das conversoes feitas pela JVM, e sera utilizado para verificar os atributos são dessa classe

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // melhora a manutenibilidade, pode ser gerado em qualquer lugar, facilita a réplica de dados para transferencia de dados
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserModel user; // user sera a chave estrangeira, a chave primaria na UserModel
    @Column(nullable = false)
    private UUID courseId;

}
