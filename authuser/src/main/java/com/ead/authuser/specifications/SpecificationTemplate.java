package com.ead.authuser.specifications;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class SpecificationTemplate {

    // O parametro path define o atributo da entidade que usaremos para fazer o filtro, messe caso um Enum
    // Já o espec define o tipo da especificação, com estamos usando o Equal, os valores devem ser exatos para serem retornados
    // Se utilizassemos o Like ao inves do Equal, seriam retornados todos os valores com conteúdos semelhantes, se buscarmos "Carlos" tudo que contem Carlos será retornado
    @And({ // Com o @And podemos fazer uma combinação de especificações, ou @Or que trará o resultada caso pelo menos uma especifcação seja cumprida
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {}

    // Metodo utilizado para buscar todos os usuarios de um curso
    public static Specification<UserModel> userCourseId(final UUID courseId){
        return (root, query, cb) -> {
            query.distinct(true);
            Join<UserModel, UserCourseModel> userProd = root.join("usersCourses");
            return cb.equal(userProd.get("courseId"), courseId);
        };
    }

}
