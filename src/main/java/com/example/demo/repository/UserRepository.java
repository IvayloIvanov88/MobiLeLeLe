package com.example.demo.repository;

import com.example.demo.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u.username FROM UserEntity u ORDER BY u.username")
    List<String> findAllUsernames();

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
