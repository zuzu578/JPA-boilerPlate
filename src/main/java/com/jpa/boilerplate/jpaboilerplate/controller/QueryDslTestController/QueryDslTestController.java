package com.jpa.boilerplate.jpaboilerplate.controller.QueryDslTestController;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import com.querydsl.jpa.JPAExpressions;
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
                .leftJoin(file)
                .on(board.fileNo.eq(file.fileNo))
                .fetch();

        JSONArray arr = new JSONArray();
        JSONObject obj = null;
        JSONObject res = new JSONObject();
        JSONObject file2 = null;
        JSONObject board2 = null;

        for (Tuple tuple : result) {

            obj = new JSONObject();
            file2 = new JSONObject();
            board2 = new JSONObject();
            board2.put("contents", tuple.get(board).getContents());
            board2.put("userName", tuple.get(board).getUserName());
            file2.put("fileName", tuple.get(file).getFileName());
            file2.put("filePath", tuple.get(file).getFilePath());
            obj.put("file", file2);
            obj.put("board", board2);

            arr.add(obj);

        }
        res.put("data", arr);
        return new ResponseEntity<>(
                res,
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

    @GetMapping("/test3")
    public ResponseEntity<?> test3() {

        /**
         * select *
         * from sys.taiko_board t1
         * left join sys.taiko_board_file t2
         * on t1.file_no = t2.file_no
         * 
         * where t1.board_no = (select board_no from sys.taiko_board where board_no =
         * '290')
         * 
         */
        QTestBoardEntity board = new QTestBoardEntity("q1");
        QTestBoardFileEntity file = new QTestBoardFileEntity("q2");
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        JSONObject obj = new JSONObject();
        List<Tuple> result = queryFactory
                .select(board.contents, board.userName, file.fileName, file.filePath)
                .from(board)
                .leftJoin(file)
                .on(board.fileNo.eq(file.fileNo))
                .where(board.boardNo
                        .in(JPAExpressions
                                .select(board.boardNo)
                                .from(board)
                                .where(board.boardNo.eq(290))))
                .fetch();

        result.forEach(item -> obj.put("data", item.toArray()));

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
