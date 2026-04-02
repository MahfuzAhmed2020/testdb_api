package com.example.testdb_api.repository;

import com.example.testdb_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    // No additional methods needed for basic CRUD


  Page<User> findAll(Pageable pageable);

      
} 