package com.jpa.boilerplate.jpaboilerplate.service.TestService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.QTestBoardEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.TestEntity.TestBoardEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class TestService {

    @PersistenceContext
    EntityManager entityManager;

    public List<TestBoardEntity> getTestData() {
        QTestBoardEntity board = new QTestBoardEntity("q1");
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        List<TestBoardEntity> result = queryFactory.select(board).from(board).fetch();

        return result;

    }
}
