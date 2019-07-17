package org.parts.item.PartList.repository;

import org.parts.item.PartList.model.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PartRepository extends JpaRepository<Part, Integer> {

    Page<Part> findAll(Pageable pageable);
    Page<Part> findAllByTitleIsContainingIgnoreCase(String string, Pageable pageable);

    Page<Part> findByRequired(Boolean required, Pageable pageable);
    List<Part> findByRequiredIsTrue();
}
