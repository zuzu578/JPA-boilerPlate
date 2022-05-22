# JPA-boilerPlate

# spring boot 와 JPA 를 사용한 보일러 플레이트.

# configuration 

1) dependency 
```xml 
<dependencies>
    <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
</dependencies>

```

# 생성파일 
<img width="460" alt="스크린샷 2022-05-22 오후 5 02 31" src="https://user-images.githubusercontent.com/69393030/169685012-0282cdc6-4714-4aff-ad24-8faa83524d0b.png">

# entity 

엔티티는 데이터베이스의 테이블과 매핑시켜준다. 일단 mysql 을 기준으로 작성했다.
```java

@Entity(name = "boardComment") //테이블 이름 
public class BoardCommentEntity {
// 컬럼 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql 에서 autoincrement 한 pk 고유 id를 갖는 id 컬럼임을 명시해준다. db방언에따라서 설정 방법도 다르다.
    private int commentNo;
    private int boardNo;
    private String name;
    private String comment;
    private String createdTime;
    private String deleteTime;
    private String updatedTime;

    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    public int getBoardNo() {
        return boardNo;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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



```

# repository

레파지토리에서는 레파지토리 interface 를 생성하고 jparepository인터페이스를 상속받는다.이때 상속받게되면 crud api를 사용할수있게된다.

```java

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity, Integer> {

}

```

레파지토리에서 생성한 메서드는 메서드 이름으로 추론하여 jpa에서 쿼리를 생성해주기도한다. 예시는 다음과같다.

```java

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    List<?> findBydeleteTimeNull(Pageable result);
}

```
이렇게 하면. deleteTime 이 null 인 데이터만 가지고 온다.


# jpa 에서 페이징은 어떻게하나요?

페이징 하는 방법은 다음과 같다.

```java
/**
     * 게시물 list 를 조회하는 api
     * 
     * @param req
     * @return
     */
    @GetMapping("/select")
    public ResponseEntity<?> selectBoardList(HttpServletRequest req) {
        String pageNum = req.getParameter("pageNum");
        if (pageNum == "" || pageNum == null) {
            pageNum = "0";
        }

        Pageable result = PageRequest.of(Integer.parseInt(pageNum), 10, Sort.by("createdTime").descending());
        return new ResponseEntity<>(board.findBydeleteTimeNull(result), HttpStatus.OK);
    }


```

다음을 보면 Pageable 이라는 class 를 사용하여 PageNum 을 받아서 페이징 처리를 한다. Sort.by를 통해 정렬을 할수있다.


# jpa 에서 join은 어떻게 하나요?

jpa 에서 조인하는방법은 기준이 되는 테이블 entity 에 조인 하려는 테이블 entity 를 명시해주면된다. 에를들자면 다음과같다.
```java
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


```

코드를 보면 짐작이 가겠지만 

  @OneToOne
    @JoinColumn(name = "boardNo", insertable = false, updatable = false)
    private BoardCommentEntity boardComment;
이 부분에서 oneToOne 은 1:1 관계 라는 의미이다. 즉 entity 관계를 1대1 관계로 설정하고 joinColumn 을 boardNo 로 명시하게 되면 된다.

엔티티 간의 관계 설정은 1대1 1대 다 다대다 로 다양하다 . 테이블 관계와 같다.



이를 sql 로 표현하면 다음과 같다.
```sql
select * from board b1 left join boardComment b2 on b1.boardNo = b2.boardNo;

```
