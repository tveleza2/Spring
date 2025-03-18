package com.tva.demo.repositories;

import java.util.UUID;
import com.tva.demo.entities.Factory;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface FactoryRepository extends JpaRepository<Factory,UUID> {
}
