package com.itplanet.contest.mapper;

import com.itplanet.contest.dto.TypeDTOResponse;
import com.itplanet.contest.entity.Type;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TypeDTOResponseMapper implements Function<Type, TypeDTOResponse> {
    @Override
    public TypeDTOResponse apply(Type type) {
        return new TypeDTOResponse(
                type.getId(),
                type.getType()
        );
    }
}
