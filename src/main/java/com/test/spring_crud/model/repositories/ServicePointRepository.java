package com.test.spring_crud.model.repositories;

import com.test.spring_crud.model.entities.ServicePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePointRepository extends JpaRepository<ServicePoint, Integer> {
}
