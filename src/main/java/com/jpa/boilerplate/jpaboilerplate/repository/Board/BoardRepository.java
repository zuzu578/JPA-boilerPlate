package com.jpa.boilerplate.jpaboilerplate.repository.Board;

import java.util.List;

import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    List<?> findBydeleteTimeNull(Pageable result);
}
