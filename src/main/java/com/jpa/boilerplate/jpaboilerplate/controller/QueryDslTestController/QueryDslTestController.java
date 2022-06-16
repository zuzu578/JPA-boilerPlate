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
import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.QTestBoardEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.QTestBoardFileEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.TestBoardEntity;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAQueryFactory;

@RestController
@RequestMapping("/test")
public class QueryDslTestController {

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        JPAQuery query = new JPAQuery(entityManager);

        QTestBoardEntity board = QTestBoardEntity.testBoardEntity;

        QTestBoardFileEntity file = QTestBoardFileEntity.testBoardFileEntity;

        String testParameter = "주환";

        // select * from board b1 left join file f1 on b1.fileNo = f1.fileNo
        List<?> result = query
                .from(board)
                // .where(board.userName.like(testParameter))
                .leftJoin(file).on(file.fileNo.eq(board.fileNo))
                // .orderBy(board.createdTime.asc())
                // .offset(0)
                // .limit(10)
                .list(board);

        // QBoardEntity qBoard = new QBoardEntity("m"); // 생성되는 JPQL의 별칭이 m
        // List<BoardEntity> result = query
        // .from(qBoard)
        // .where(qBoard.title.like("수정함"))
        // .orderBy(qBoard.createdTime.desc())
        // .offset(0)
        // .limit(10)
        // .list(qBoard);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
