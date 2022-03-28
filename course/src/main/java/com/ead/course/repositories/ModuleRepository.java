package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    // Aqui estamos definindo uma consulta específica, localizar um módulo pelo título, mas também retornando o atributo de curso, dessa forma é como se esse atributo estivesse com o fetch type "eager"
    //@EntityGraph(attributePaths = {"course"})
    //ModuleModel findByTitle(String title);

    // Estamos definindo uma query de forma nativa
    //@Modifying // Utilizar quando for fazer alguma alteração, update, delete e etc
    @Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true) // @Query só podemos utilizar quando for select, se for para deletar ou atualizar temos que usar @Modifying em conjunto com @Query
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "select * from tb_modules where course_course_id = :courseId and module_id = :moduleId", nativeQuery = true)
    Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
}
