package com.inventorsoft.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneralService<T, ID extends Integer> {

    JpaRepository<T, ID> jpaRepository;

    GeneralService(JpaRepository<T, ID> repository) {
        this.jpaRepository = repository;
    }

    public Optional<T> findOne(ID id) {
        return ofNullable(jpaRepository.findOne(id));
    }

    public T getOne(ID id) {
        return ofNullable(jpaRepository.getOne(id))
                .orElseThrow(EntityNotFoundException::new);
    }

}
