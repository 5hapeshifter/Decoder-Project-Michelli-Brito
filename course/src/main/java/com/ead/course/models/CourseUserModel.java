package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
// todas as vezes que for preciso fazer a serialização de atributo nulo, o campo será ocultado
@Entity
@Table(name = "TB_COURSES_USERS")
public class CourseUserModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    //@JoinColumn(name = "courseId") renomeando a coluna
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CourseModel course; // chave primaria da outra tabela, que sera chave estrangeira
    @Column(nullable = false)
    private UUID userId;

}
