package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
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
@Table(name = "TB_COURSES")
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false, length = 250)
    private String description;
    @Column
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") // Padrao ISO 8601
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // estamos definindo o tipo do dado que será salvo
    private CourseStatus courseStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;
    @Column(nullable = false)
    private UUID userInstructor;

    // Estamos definindo que a entidade curso pode ter varios modulos
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Estamos definindo o tipo de acesso a esse atributo especifico, tanto na serializacao quanto o inverso
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY) // aqui estamos informando que a chave estrangeira em tb_modules será "course", fetch type define a forma de carregamento dos dados (eager ou lazy)
    @Fetch(FetchMode.SUBSELECT) // Define a forma de buscar os dados, "Join" (eager e defoult) faz uma unica consulta e traz tudo que estiver vinculado, Subselect faz uma consulta para o objeto e outra para o resto que estiver vinculado, o select faz uma consulta para cada coisa
    private Set<ModuleModel> modules; // Utilizamos o set ao invés de um List porque o Set não é ordenado e não permite duplicatas, quando temos muitas associações dentro de uma entidade, se usassemos o o List, teriamos como resultado somente a primeira associacao, as demais nao seriam retornadas

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Estamos definindo o tipo de acesso a esse atributo especifico, tanto na serializacao quanto o inverso
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY) // atributo que sera chave estrangeira na outra tabela
    private Set<CourseUserModel> coursesUsers;

    public CourseUserModel convertToCourseUserModel(UUID userId) {
        return new CourseUserModel(null, this, userId);
    }
}
