package com.itplanet.contest.specification;

import com.itplanet.contest.entity.Visited;
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
        )
})
public interface VisitedSearchSpec extends Specification<Visited> {
}
