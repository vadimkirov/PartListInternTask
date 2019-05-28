package org.parts.item.PartList.repository;

import org.parts.item.PartList.model.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartRepository extends JpaRepository<Part, Integer> {

    Page<Part> findAllByOrderByTitleAsc(Pageable pageable);


}
