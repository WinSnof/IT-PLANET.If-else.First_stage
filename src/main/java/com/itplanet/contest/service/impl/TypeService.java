package com.itplanet.contest.service.impl;

import com.itplanet.contest.dto.TypeDTORequest;
import com.itplanet.contest.dto.TypeDTOResponse;
import com.itplanet.contest.entity.Type;
import com.itplanet.contest.exception.BadRequestEx;
import com.itplanet.contest.exception.ConflictEx;
import com.itplanet.contest.exception.NotFoundEx;
import com.itplanet.contest.mapper.TypeDTOResponseMapper;
import com.itplanet.contest.repository.AnimalRepo;
import com.itplanet.contest.repository.TypeRepo;
import com.itplanet.contest.service.ITypeService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class TypeService implements ITypeService {

    private final TypeRepo typeRepo;
    private final AnimalRepo animalRepo;
    private final TypeDTOResponseMapper typeDTOResponseMapper;

    @Override
    public TypeDTOResponse findById(Long id) {
        return typeDTOResponseMapper
                .apply(typeRepo.findById(id)
                .orElseThrow(() -> new NotFoundEx(id)));
    }

    @Override
    public TypeDTOResponse save(TypeDTORequest type) {
        if(typeRepo.findByType(type.type()).isPresent())
            throw new ConflictEx(type.type());
        Type toSave = new Type(type.type());
        return typeDTOResponseMapper
                .apply(typeRepo.save(toSave));
    }

    @Override
    public TypeDTOResponse update(TypeDTORequest type, Long id) {
        typeRepo.findById(id)
                .orElseThrow(() -> new NotFoundEx(id));
        if(typeRepo.findByType(type.type()).isPresent())
            throw new ConflictEx(type.type());
        Type toUpdate = new Type(type.type());
        toUpdate.setId(id);
        return typeDTOResponseMapper
                .apply(typeRepo.save(toUpdate));
    }

    @Override
    public void delete(Long id) {
        Type type = typeRepo.findById(id)
                .orElseThrow(() -> new NotFoundEx(id));
        if(!animalRepo.findByAnimalTypes(type).isEmpty())
            throw new BadRequestEx();
        typeRepo.deleteById(id);
    }
}
