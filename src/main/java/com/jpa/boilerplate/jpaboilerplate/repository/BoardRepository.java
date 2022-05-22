package com.jpa.boilerplate.jpaboilerplate.repository;

import java.util.List;

import com.jpa.boilerplate.jpaboilerplate.entity.BoardEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    List<?> findBydeleteTimeNull(Pageable result);
}
