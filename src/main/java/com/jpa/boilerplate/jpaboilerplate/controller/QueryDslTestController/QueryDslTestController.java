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
import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.QTestBoardEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.QTestBoardFileEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.TestBoardEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.TestBoardFileEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@RestController
@RequestMapping("/test")
public class QueryDslTestController {

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        QTestBoardEntity board = new QTestBoardEntity("q1");
        QTestBoardFileEntity file = new QTestBoardFileEntity("q2");
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Tuple> result = queryFactory
                .select(board, file)
                .from(board)
                .join(file)
                .on(board.fileNo.eq(file.fileNo))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("test!!!====>" + tuple.get(board).getBoardNo());
            System.out.println("test =====>" + tuple.get(file).getFileName());
        }
        return new ResponseEntity<>(
                "",
                HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<?> test2() {

        QTestBoardEntity board = new QTestBoardEntity("q1");
        QTestBoardFileEntity file = new QTestBoardFileEntity("q2");
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return new ResponseEntity<>(
                queryFactory
                        .select(board)
                        .from(board)
                        .where(board.userName.eq("주환"))
                        .orderBy(board.createdTime.asc())
                        .offset(0)
                        .limit(10)
                        .fetch(),
                HttpStatus.OK);
    }
}
