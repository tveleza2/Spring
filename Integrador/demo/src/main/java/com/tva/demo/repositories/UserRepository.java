package com.tva.demo.repositories;

import java.util.UUID;
import com.tva.demo.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,UUID> {
}
