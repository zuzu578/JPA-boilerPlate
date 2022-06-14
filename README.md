# JPA-boilerPlate
 
# spring legacy 에서 hibernate , jpa 를 사용할 경우 환경설정 

프로젝트 우클릭 → configure → Convert to JPA Project' 을 통해 JPA 프로젝트로 바꿔준다.
![test111](https://user-images.githubusercontent.com/69393030/169774227-3af20ce8-30ad-4378-9b65-f42e71c4a837.png)

그런다음 위를 보면 persistence.xml 파일이 생성될텐데. 거기에 hibernate db connection 정보 등을 작성한다.


# persistence.xml 
```xml

<?xml version="1.0" encoding="UTF-8"?>

<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">

   <persistence-unit name="EMS">

       <class>egovframework.com.bo.cmm.entity.BoMenuManageEntity</class>

 

        <properties>

            <property name="hibernate.dialect" value="org.hibernate.dialect.CUBRIDDialect"/>

            <property name="hibernate.show_sql" value="true"/>
				<property name="hibernate.connection.url" value="jdbc:CUBRID:1.214.219.236:33000:EMS:::"/> 
				<property name="hibernate.connection.driver_class" value="cubrid.jdbc.driver.CUBRIDDriver"/>
			   <property name="hibernate.connection.username" value="emsuser"/>
			   <property name="hibernate.connection.password" value="ems527useR4"/>


        </properties>   
   </persistence-unit>

</persistence>


```

그런다음 jpa config파일을 생성한다 . 
![스크린샷tetestsetests-11](https://user-images.githubusercontent.com/69393030/169774812-feda1cd2-d13d-469c-80c7-1a43ab368bf7.png)

그런다음 위와같이 작성해준다.
```java
package egovframework.com.bo.cmm.repository;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = {"egovframework.com.bo.cmm.repository"})
public class JpaConfig {
	
	 @Bean
	 public LocalEntityManagerFactoryBean entityManagerFactory() {
	        LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
	        factoryBean.setPersistenceUnitName("EMS"); // 위에서 persistence.xml 파일에서 정한 persistence-unit name = "EMS" 그대로 적어준다.
	          
	        return factoryBean;
	 }
	      
	    @Bean
	    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManagerFactory);
	          
	        return transactionManager;
	  } 
	

}


```


# spring boot 와 JPA 를 사용한 보일러 플레이트.

# configuration 

초기 sql script 

```sql
CREATE TABLE sys.board_comment (
comment_no int (11) auto_increment primary key ,
board_no int (11),
name varchar(200),
comment varchar(600),
created_time dateTime,
delete_time dateTime,
updated_time dateTime
 
) 



CREATE TABLE sys.board (
board_no int (11) auto_increment primary key ,
name varchar(200),
title varchar(200),
content varchar(600),
created_time dateTime,
delete_time dateTime,
updated_time dateTime
 
) 


```




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


# jwt 

JWT 란 json web token 은 웹표준 으로서 일반적으로 클라이언트 - 서버 - 서비스 - 서비스 통신시 권한 인가를 위해 사용하는 토큰이다.

# jwt 구조 

1) header : 토큰 타입 , 해시 알고리즘 저장 
2) payload : 정보 값 
3) signature : 위변조 방지값 


# criteria 질의문 
criteria 란 객체지향 쿼리 빌더 로써 질의문을 java method 로 작성함으로써 sql 을 동적으로 생성할수있다.

1) 사용법 
``` java
@PersistenceContext

EntityManager entityManager;

```
를 추가해준다.


``` java
public List<Board> testGetList(final int startRow, final int pageSize)

{

	// CriteriaBuilder 인스턴스를 작성한다.

	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

	// CriteriaQuery 인스턴스를 생성한다. 

	CriteriaQuery<Board> criteriaQuery = criteriaBuilder.createQuery(Board.class);



	// Root는 영속적 엔티티를 표시하는 쿼리 표현식이다. SQL의 FROM 과 유사함

	Root<Board> root = criteriaQuery.from(Board.class);



	// SQL의 WHERE절이다. 조건부는 CriteriaBuilder에 의해 생성

	Predicate restrictions = criteriaBuilder.equal(root.get("boardTitle"), "jpa");

	criteriaQuery.where(restrictions);



	// ORDER BY절. CriteriaQuery로 생성

	criteriaQuery.orderBy(criteriaBuilder.desc(root.get("boardIdx")));



	//  TypedQuery는 실행 결과를 리턴하는 타입이다.

	TypedQuery<Board> boardListQuery = entityManager.createQuery(criteriaQuery).setFirstResult(startRow).setMaxResults(pageSize);

	List<Board> boardList = boardListQuery.getResultList();



	return boardList;

}



```

# criteria 질의문에서 where 조건 and 여러개 추가하기.

해당 코드는 동적으로 parameter 가 넘어올 경우 and 조건을 추가해주는 코드이다.  and 조건을 추가할때는 criteriaBuilder.and() 를 사용하면 되고 or 조건은 criteriaBuilder.or() 
를 사용한다. 
```java

				  Predicate condition1 = null ;
				  Predicate condition2 = null ; 
				  Predicate defaultCondition = criteriaBuilder.equal(root.get("delYn"), 'N');
				  Predicate orderByCondition = criteriaBuilder.eq
				  
				  Predicate goodsConditions = null;
				  if(searchCondition.get("gds_nm") != null) {
					  condition1 = criteriaBuilder.equal(root.get("gds_nm"), searchCondition.get("gds_nm").toString());  
					  
				  }
				  if(searchCondition.get("reg_ymd")!= null) {
					  condition2 = criteriaBuilder.equal(root.get("reg_ymd"), searchCondition.get("reg_ymd").toString());
				  }
				  if(condition1 != null && condition2 != null ) {					  
					  goodsConditions = criteriaBuilder.and(condition1, condition2,defaultCondition);
					  criteriaQuery.where(goodsConditions);
				  }else if(condition1 != null) {
					  goodsConditions = criteriaBuilder.and(condition1, defaultCondition);
				  } else if (condition2 != null) {
					  goodsConditions = criteriaBuilder.and(condition2, defaultCondition);
				  }
				  criteriaQuery.orderBy(criteriaBuilder.desc(root.get("reg_ymd")));
				  result = entityManager.createQuery(criteriaQuery).getResultList();
		    	 


```
# criteria 에서 paging 하는법 
```java
TypedQuery<AdminUserVO> boardListQuery = entityManager.createQuery(criteriaQuery).setFirstResult(Integer.parseInt(pageNum)).setMaxResults(fetchSize);
```
끝 
