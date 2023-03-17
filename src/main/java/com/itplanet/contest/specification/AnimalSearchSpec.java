package com.itplanet.contest.specification;

import com.itplanet.contest.entity.Animal;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;


@And({
        @Spec(
                path = "startDateTime",
                spec = GreaterThan.class
        ),
        @Spec(
                path = "endDateTime",
                spec = LessThan.class
        ),
        @Spec(path = "chipperId.id",params = "chipperId",spec = Equal.class, config = "String -> Integer"),
        @Spec(path = "chippingLocationId.id",params = "chippingLocationId",spec = Equal.class),
        @Spec(path = "lifeStatus",spec = In.class),
        @Spec(path = "gender", spec = In.class)
})
public interface AnimalSearchSpec extends Specification<Animal> {
}
