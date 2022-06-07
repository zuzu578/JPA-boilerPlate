package com.jpa.boilerplate.jpaboilerplate.repository.Board;

import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardCommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Integer> {

}
