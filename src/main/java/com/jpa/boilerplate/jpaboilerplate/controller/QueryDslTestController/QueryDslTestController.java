package com.jpa.boilerplate.jpaboilerplate.controller.QueryDslTestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysema.query.jpa.impl.JPAQuery;

@RestController
public class QueryDslTestController {

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/test")
    public void test() {
        JPAQuery query = new JPAQuery(entityManager);
        // QBoardEntity test = new QBoardEntity("m");
        // JPAQuery query = new JPAQuery(entityManager);
        // QBoardEntity qMember = new QBoardEntity("m"); // 생성되는 JPQL의 별칭이 m
        // List<Member> members = query
        // .from(qMember)
        // .where(qMember.username.eq("철수"))
        // .orderBy(qMember.username.desc())
        // .list(qMember);

        // for (Member member : members) {
        // System.out.println("Member : " + member.getMemberId() + ", " +
        // member.getUsername());
        // }
        // }
    }
}
