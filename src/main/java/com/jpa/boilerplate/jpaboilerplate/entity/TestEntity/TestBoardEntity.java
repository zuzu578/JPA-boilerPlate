package com.jpa.boilerplate.jpaboilerplate.entity.TestEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * taiko_board = test board entity
 */
@Entity(name = "taiko_board")
public class TestBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private int boardNo;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_profile")
    private String userProfile;

    @Column(name = "contents")
    private String contents;

    @Column(name = "file_no")
    private int fileNo;

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "deleted_time")
    private String deletedTime;

    @Column(name = "password")
    private String password;

    @Transient
    private TestBoardFileEntity testBoardFileEntity;

    public TestBoardFileEntity getTestBoardFileEntity() {
        return testBoardFileEntity;
    }

    public void setTestBoardFileEntity(TestBoardFileEntity testBoardFileEntity) {
        this.testBoardFileEntity = testBoardFileEntity;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
        this.fileNo = fileNo;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(String deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
