package com.jpa.boilerplate.jpaboilerplate.controller.CriteriaSampleController;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardCommentEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardEntity;

@RestController
public class CriteriaSample {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * sample code
     */
    @GetMapping("/criteriaTest")
    public void test() {
        List<Tuple> tuples = new ArrayList<Tuple>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> criteria = criteriaBuilder.createTupleQuery();
        Root<BoardEntity> board = criteria.from(BoardEntity.class);
        Join<BoardEntity, BoardCommentEntity> boardComment = board.join("boardComment", JoinType.LEFT);

        try {
            // where clause
            Predicate conditions = criteriaBuilder.equal(board.get("name"), "test");
            criteria.where(conditions);
            criteria.multiselect(board, boardComment);
            tuples = entityManager.createQuery(criteria.where()).getResultList();
            // todo : tuple 에서 목록 꺼내서 ListHashMap으로 변환 + 조건에 맞게 filter
            for (Tuple tuple : tuples) {
                System.out.println("test ===>" + tuple.get(board).getTitle());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
