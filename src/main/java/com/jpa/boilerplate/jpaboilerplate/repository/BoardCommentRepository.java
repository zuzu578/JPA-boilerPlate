package com.jpa.boilerplate.jpaboilerplate.repository;

import com.jpa.boilerplate.jpaboilerplate.entity.BoardCommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Integer> {

}
