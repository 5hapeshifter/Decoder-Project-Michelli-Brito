package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonModel, UUID> {

    @Query(value="select * from tb_lessons where module_module_id = :moduleId", nativeQuery = true) // @Query só podemos utilizar quando for select, se for para deletar ou atualizar temos que usar @Modifying em conjunto com @Query
    List<LessonModel> findAllLessonsIntoModule(@Param("moduleId") UUID moduleId);

    @Query(value = "select * from tb_lessons where module_module_id = :moduleId and lesson_id = :lessonId", nativeQuery = true)
    Optional<LessonModel> findLessonIntoModule(@Param("moduleId") UUID courseId, @Param("lessonId") UUID moduleId);

}
