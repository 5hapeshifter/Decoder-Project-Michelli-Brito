package com.ead.course.specifications;


import com.ead.course.models.CourseModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

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


}
