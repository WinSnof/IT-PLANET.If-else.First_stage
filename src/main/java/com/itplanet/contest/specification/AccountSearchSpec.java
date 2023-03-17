package com.itplanet.contest.specification;

import com.itplanet.contest.entity.Account;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "firstName", spec = LikeIgnoreCase.class),
        @Spec(path = "lastName", spec = LikeIgnoreCase.class),
        @Spec(path = "email", spec = LikeIgnoreCase.class)
})
public interface AccountSearchSpec extends Specification<Account> {
}
