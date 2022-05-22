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
