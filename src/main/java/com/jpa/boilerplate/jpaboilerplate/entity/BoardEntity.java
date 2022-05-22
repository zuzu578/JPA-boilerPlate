package com.jpa.boilerplate.jpaboilerplate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * board = 게시물 테이블
 */
@Entity(name = "board") // table name
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardNo;
    private String name;
    private String title;
    private String content;
    private String createdTime;
    private String deleteTime;
    private String updatedTime;

    @OneToOne
    @JoinColumn(name = "boardNo", insertable = false, updatable = false)
    private BoardCommentEntity boardComment;

    public int getBoardNo() {
        return boardNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BoardCommentEntity getBoardComment() {
        return boardComment;
    }

    public void setBoardComment(BoardCommentEntity boardComment) {
        this.boardComment = boardComment;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

}
