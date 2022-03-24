package com.ead.authuser.specifications;

import com.ead.authuser.models.UserModel;
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
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {}

}
