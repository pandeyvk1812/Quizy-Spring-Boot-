package com.Spring.Quiz_Application.repository;

import com.Spring.Quiz_Application.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT u FROM User u WHERE u.email = :email")
  public User findByEmail(@Param("email") String email);
  User findByResetToken(String resetToken);

  User findByVerificationToken(String verificationToken);

    boolean existsByEmail(String email);
}
