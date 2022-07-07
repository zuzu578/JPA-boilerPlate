package com.jpa.boilerplate.jpaboilerplate.service.MainService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.Board.QBoardCommentEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.Board.QBoardEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class MainService {

    @PersistenceContext
    EntityManager entityManager;

    public List<BoardEntity> getBoardList(String pageNum) {
        // findBydeleteTimeNull
        QBoardEntity board = new QBoardEntity("board");
        QBoardCommentEntity comment = new QBoardCommentEntity("comment");

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<BoardEntity> result = queryFactory
                .select(board)
                .from(board)
                .leftJoin(comment)
                .on(board.boardNo.eq(comment.boardNo))
                .where(board.deleteTime.isNull())
                .orderBy(board.createdTime.desc())
                .fetch();

        return result;
    }

}
