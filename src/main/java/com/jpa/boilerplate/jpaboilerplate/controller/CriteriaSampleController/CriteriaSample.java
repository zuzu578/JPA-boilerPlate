package com.jpa.boilerplate.jpaboilerplate.controller.CriteriaSampleController;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<HashMap<String, Object>> test() {
        List<Tuple> tuples = new ArrayList<Tuple>();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        HashMap<String, Object> resultMap = null;
        List<HashMap<String, Object>> resultArr = new ArrayList<HashMap<String, Object>>();

        CriteriaQuery<Tuple> criteria = criteriaBuilder.createTupleQuery();
        Root<BoardEntity> board = criteria.from(BoardEntity.class);
        Join<BoardEntity, BoardCommentEntity> boardComment = board.join("boardComment", JoinType.INNER);

        try {
            // where clause
            Predicate conditions = criteriaBuilder.equal(boardComment.get("commentNo"), 4);
            criteria.multiselect(board, boardComment).where(conditions);
            tuples = entityManager.createQuery(criteria).getResultList();
            // todo : tuple 에서 목록 꺼내서 ListHashMap으로 변환 + 조건에 맞게 filter
            for (Tuple tuple : tuples) {
                resultMap = new HashMap<String, Object>();
                resultMap.put("title", tuple.get(board).getTitle());
                resultMap.put("boardNo", tuple.get(board).getBoardNo());
                resultMap.put("contents", tuple.get(board).getContent());
                resultMap.put("name", tuple.get(board).getName());
                resultMap.put("createdTime", tuple.get(board).getCreatedTime());

                resultMap.put("comment", tuple.get(boardComment).getComment());

                resultArr.add(resultMap);

            }

            return resultArr;

        } catch (Exception e) {
            e.printStackTrace();
            // test
            return null;
        }

    }

}
