package com.jpa.boilerplate.jpaboilerplate.repository.User;

import java.util.Optional;

import com.jpa.boilerplate.jpaboilerplate.entity.User.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByIdAndPassword(String id, String password);

}
