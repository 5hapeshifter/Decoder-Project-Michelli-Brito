package com.ead.course.specifications;


import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    // O parametro path define o atributo da entidade que usaremos para fazer o filtro, messe caso um Enum
    // Já o espec define o tipo da especificação, com estamos usando o Equal, os valores devem ser exatos para serem retornados
    // Se utilizassemos o Like ao inves do Equal, seriam retornados todos os valores com conteúdos semelhantes, se buscarmos "Carlos" tudo que contem Carlos será retornado
    @And({ // Com o @And podemos fazer uma combinação de especificações, ou @Or que trará o resultada caso pelo menos uma especifcação seja cumprida
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {}


    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpec extends Specification<LessonModel> {}

    // Metodo criado para poder fazer a contulta de uma lista de modulos dentro dos cursos, utilizando o CriteriaBuilder
    public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true); // Definicao para nao trazer valores duplicados
            Root<ModuleModel> module = root; // Entidade que fara parte da consula
            Root<CourseModel> course = query.from(CourseModel.class); // Entidade que fara parte da consula
            Expression<Collection<ModuleModel>> coursesModules = course.get("modules"); // Estamos extraindo a colecao de uma entidade, extraindo os modulos do curso
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, coursesModules)); // Construcao da query
        };
    }

    // Metodo criado para poder fazer a contulta de uma lista de modulos dentro dos cursos, utilizando o CriteriaBuilder
    public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
        return (root, query, cb) -> {
            query.distinct(true); // Definicao para nao trazer valores duplicados
            Root<LessonModel> lesson = root; // Entidade que fara parte da consula
            Root<ModuleModel> module = query.from(ModuleModel.class); // Entidade que fara parte da consula
            Expression<Collection<LessonModel>> moduleLessons = module.get("lessons"); // Estamos extraindo a colecao de uma entidade, extraindo os modulos do curso
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, moduleLessons)); // Construcao da query
        };
    }

    // Metodo utilizado para buscar todos os cursos de um usuario
    public static Specification<CourseModel> courseUserId(final UUID userId){
        return (root, query, cb) -> {
            query.distinct(true);
            Join<CourseModel, CourseUserModel> courseProd = root.join("coursesUsers");
            return cb.equal(courseProd.get("userId"), userId);
        };
    }
}
