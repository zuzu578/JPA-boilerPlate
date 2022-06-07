package com.jpa.boilerplate.jpaboilerplate.utils;

import java.util.HashMap;

import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardCommentEntity;

import org.apache.commons.lang3.StringUtils;

public class BoardCommentNullCheckMessage {
    public HashMap<String, Object> nullCheck(BoardCommentEntity commentParam) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        if (StringUtils.isEmpty(commentParam.getName())) {
            resultMap.put("message", "name is empty");
        }

        if (StringUtils.isEmpty(commentParam.getComment())) {
            resultMap.put("message", "comment is empty");
        }

        if (commentParam.getBoardNo() == 0 || StringUtils.isEmpty(Integer.toString(commentParam.getBoardNo()))) {
            resultMap.put("message", "boardNo is empty");
        }

        return resultMap;
    }
}
