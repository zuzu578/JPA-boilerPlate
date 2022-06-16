package com.jpa.boilerplate.jpaboilerplate.controller.QueryDslTestController;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.Board.QBoardEntity;
import com.mysema.query.jpa.impl.JPAQuery;

@RestController
@RequestMapping("/test")
public class QueryDslTestController {

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        JPAQuery query = new JPAQuery(entityManager);
        QBoardEntity qBoard = new QBoardEntity("m"); // 생성되는 JPQL의 별칭이 m
        List<BoardEntity> result = query
                .from(qBoard)
                .where(qBoard.title.like("수정함"))
                .orderBy(qBoard.createdTime.desc())
                .list(qBoard);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
