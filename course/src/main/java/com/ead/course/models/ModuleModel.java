package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // todas as vezes que for preciso fazer a serialização de atributo nulo, o campo será ocultado
@Entity
@Table(name = "TB_MODULES")
public class ModuleModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID moduleId;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(nullable = false, length = 250)
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private LocalDateTime creationDate;

    // Aqui estamos definindo que cada módulo só pertence a um curso
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Estamos definindo o tipo de acesso a esse atributo especifico, tanto na serializacao quanto o inverso
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // aqui estamos definindo que qualquer módulo deve estar associado a um curso obrigatoriamente
    private CourseModel course;

    // Aqui estamos definindo que cada módulo tem varias lições
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Estamos definindo o tipo de acesso a esse atributo especifico, tanto na serializacao quanto o inverso
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY) // aqui estamos informando que a chave estrangeira em tb_modules será "course", fetch type define a forma de carregamento dos dados (eager ou lazy)
    @Fetch(FetchMode.SUBSELECT)
    private Set<LessonModel> lessons;


    //@OnDelete(action = OnDeleteAction.CASCADE) // estamos delegando a responsabilidade da deleção para o banco de dados, lado ruim é que não sabemos o que está sendo deletado
}
