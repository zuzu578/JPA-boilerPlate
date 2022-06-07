package com.jpa.boilerplate.jpaboilerplate.utils.MessageUtils;

import java.util.HashMap;

import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardEntity;

import org.apache.commons.lang3.StringUtils;

public class BoardNullCheckMessage {

    public HashMap<String, Object> nullCheck(BoardEntity boardParam) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        if (StringUtils.isEmpty(boardParam.getName())) {
            resultMap.put("message", "name is empty");
        }

        if (StringUtils.isEmpty(boardParam.getContent())) {
            resultMap.put("message", "content is empty");
        }

        if (StringUtils.isEmpty(boardParam.getTitle())) {
            resultMap.put("message", "title is empty");
        }

        return resultMap;
    }
}
