package com.itplanet.contest.service;

import com.itplanet.contest.dto.TypeDTORequest;
import com.itplanet.contest.dto.TypeDTOResponse;

public interface ITypeService {
    TypeDTOResponse findById(Long id);

    TypeDTOResponse save(TypeDTORequest type);

    TypeDTOResponse update(TypeDTORequest type, Long id);

    void delete(Long id);
}
