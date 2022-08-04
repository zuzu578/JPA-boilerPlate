# Integer property 를 like 로 검색할때 
다음과 같이한다. 

참조 : https://stackoverflow.com/questions/10230680/criteria-query-search-for-integer-using-like
```java
predicates.add(criteriaBuilder.like(orderRoot.get(sKey).as(String.class), "%"+""+sWords+""+"%"));		

```

# id column 끼리 join 하지 않고 , fk 끼리 조인할경우
serialize 를 구현한다.
```java
package egovframework.system.user.service;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import egovframework.system.manage.service.ComtnemplyrscrtyestbsT;
import lombok.Data;

@Entity
@Table(name = "comtngnrlmber")
@DynamicUpdate
@Data
public class SystemAdminUserVO implements Serializable {
	
	@Id
	@Column(name = "mber_id", updatable=false)
	private String mberId;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "password_hint")
	private String passwordHint;
	
	@Column(name = "password_cnsr")
	private String passwordCnsr;
	
	@Column(name = "ihidnum")
	private String ihidnum;
	
	@Column(name = "mber_nm")
	private String mberNm;
	
	@Column(name = "zip")
	private String zip;
	
	@Column(name = "adres")
	private String adres;
	
	@Column(name = "area_no")
	private String areaNo;
	
	@Column(name = "mber_sttus")
	private String mberSttus;
	
	@Column(name = "detail_adres")
	private String detailAdres;
	
	@Column(name = "end_telno")
	private String endTelno;
	
	@Column(name = "mbtlnum")
	private String mbtlnum;
	
	@Column(name = "group_id")
	private String groupId;
	
	@Column(name = "mber_fxnum")
	private String mberFxnum;
	
	@Column(name = "mber_email_adres")
	private String mberEmailAdres;
	
	@Column(name = "middle_telno")
	private String middleTelno;
	
	@Column(name = "sbscrb_de", updatable=false)
	private String sbscrbDe;
	
	@Column(name = "sexdstn_code")
	private String sexdstnCode;
	
	@Column(name = "esntl_id")
	private String esntlId;
	
	@Column(name = "lock_at")
	private String lockAt;
	
	@Column(name = "lock_cnt")
	private String lockCnt;
	
	@Column(name = "lock_last_pnttm")
	private String lockLastPnttm;
	
	@Column(name = "chg_pwd_last_pnttm")
	private String chgPwdLastPnttm;
	
	@Column(name = "usergrade")
	private String usergrade;
	
	@Column(name = "del_yn")
	private String delYn;
	
	@Transient
	private String passwordComfirm;

	@OneToMany
	@JoinColumn(name = "scrty_dtrmn_trget_id", referencedColumnName="esntl_id",insertable = false, updatable = false)
	private Set<ComtnemplyrscrtyestbsT> comtnemplyrscrtyestbsT;
	
	
}


```

# left join on 절에 and 추가하기 

간혹 left join on 에 and 를 추가하는 쿼리가있다 예를들어 
```sql
 select
            comtnrolei0_.role_code as role_cod1_6_0_,
            comtauthor1_.role_code as role_cod1_1_1_,
            comtnrolei0_.role_creat_de as role_cre2_6_0_,
            comtnrolei0_.role_dc as role_dc3_6_0_,
            comtnrolei0_.role_nm as role_nm4_6_0_,
            comtnrolei0_.role_pttrn as role_ptt5_6_0_,
            comtnrolei0_.role_sort as role_sor6_6_0_,
            comtnrolei0_.role_ty as role_ty7_6_0_,
            comtauthor1_.author_code as author_c2_1_1_,
            comtauthor1_.creat_dt as creat_dt3_1_1_ 
        from
            comtnroleInfo comtnrolei0_ 
        left outer join
            comtnauthorrolerelate comtauthor1_ 
                on comtnrolei0_.role_code=comtauthor1_.role_code 
                and (
                    comtauthor1_.author_code=?
                ) 
        where
            1=1 
        order by
            comtnrolei0_.role_creat_de desc limit ?


```
이런식으로...
이럴땐 oneToMany 로 조인 관계를 설정한뒤 (Set<VO> 이렇게하고)
```java
// join 
			Root<ComtnroleInfoVO> comtnroleInfo = criteria.from(ComtnroleInfoVO.class);
			Join<ComtnroleInfoVO, ComtnauthorrolerelateVO> comtauthorroleate = comtnroleInfo.join("comtauthorroleateVO", JoinType.LEFT);
			comtauthorroleate.on(
					criteriaBuilder.and(
							criteriaBuilder.equal(comtauthorroleate.get("authorCode"), authorCode)
							)
					);

```
이렇게 한다.
# ** @DynamicUpdate
@DynamicUpdate 는 수정하려는 컬럼만 수정하도록 entity 에 설정해두면 된다. 이는 어노테이션으로 명시해주게되면 수정하려는 컬럼만 수정하여 persist 할수있다.

@DynamicUpdate 를 명시하지않을경우 예를들어 userId 를 수정하면 전체 컬럼이 수정되는 상황이 발생하나 , @DynamicUpdate 를 명시하게된다면 userId 만 수정하게 할수있다.

# ** updatable=false 
updatable=false 설정을 통해 업데이트 를 하지못하게 해당 엔티티의 컬럼에 설정을 할수있다.


# JPA-boilerPlate

<img width="764" alt="스크린샷 2022-06-16 오후 11 53 48" src="https://user-images.githubusercontent.com/69393030/174098234-11415393-9f1d-4d89-a884-1f5a340163a7.png">
<img width="769" alt="스크린샷 2022-06-17 오전 12 04 18" src="https://user-images.githubusercontent.com/69393030/174100556-2b260955-972d-404e-8436-3a417c41c3ad.png">

<img width="768" alt="스크린샷 2022-06-16 오후 11 55 06" src="https://user-images.githubusercontent.com/69393030/174098547-1d028c97-cc7a-4892-9477-8bbbc74bd917.png">
<img width="741" alt="스크린샷 2022-06-16 오후 11 57 29" src="https://user-images.githubusercontent.com/69393030/174099068-d3b7d492-b304-411b-8117-0fc7149601cf.png">
<img width="741" alt="스크린샷 2022-06-16 오후 11 58 44" src="https://user-images.githubusercontent.com/69393030/174099397-439a612d-4201-4b08-85e5-b9d717c0b472.png">

# 필자는 하이버네이트 , jpa 세팅하다가 머리털이 몇개 빠진것같습니다.
<img width="741" alt="스크린샷 2022-06-16 오후 11 59 20" src="https://user-images.githubusercontent.com/69393030/174099506-1c72c072-b3eb-4052-914d-6a07e1909e0b.png">

# 속보 속보 속보 

메이븐 컴파일이 안될때 

    java.lang.NoClassDefFoundError: javax/xml/bind/JAXBException 
    
    이런 오류 나서 

``` xml
<!-- JAXB API only -->
  <dependency>
      <groupId>jakarta.xml.bind</groupId>
      <artifactId>jakarta.xml.bind-api</artifactId>
      <version>3.0.0</version>
  </dependency>

  <!-- JAXB RI, Jakarta XML Binding -->
  <dependency>
      <groupId>com.sun.xml.bind</groupId>
      <artifactId>jaxb-impl</artifactId>
      <version>3.0.0</version>
      <scope>runtime</scope>
  </dependency>


```
 해주니 됌 
 

 
# spring legacy 에서 hibernate , jpa 를 사용할 경우 환경설정 
<img width="741" alt="스크린샷 2022-06-17 오전 12 00 29" src="https://user-images.githubusercontent.com/69393030/174099775-0fd4a3e2-65e3-45fb-be69-1e39f8305ce1.png">
<img width="762" alt="스크린샷 2022-06-17 오전 12 02 36" src="https://user-images.githubusercontent.com/69393030/174100220-56e3b6d2-7608-4263-929d-cab6ed6807d2.png">

스프링 레거시나 전자정부에서 하이버네이트와 마이바티스를 같이 쓸경우이다.. 물론 전자정부에서 하이버네이트를 쓴다면..(그럴일은 적겠지만..) 필자가 전자정부에다 삽질을 하며 고생한끝에 두개다 설정했다.
이게 그렇게 어려운것은 아닌데 이상하게 설정이 꼬여서 너무 힘들었다. 

<img width="768" alt="스크린샷 2022-06-16 오후 11 55 38" src="https://user-images.githubusercontent.com/69393030/174098671-d1dcd0c3-b8ad-452b-9394-08e6e11137dd.png">

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
				<property name="hibernate.connection.url" value=""/> 
				<property name="hibernate.connection.driver_class" value="cubrid.jdbc.driver.CUBRIDDriver"/>
			   <property name="hibernate.connection.username" value=""/>
			   <property name="hibernate.connection.password" value=""/>


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



# criteria 질의문 

<img width="768" alt="스크린샷 2022-06-16 오후 11 55 06" src="https://user-images.githubusercontent.com/69393030/174098547-1d028c97-cc7a-4892-9477-8bbbc74bd917.png">

criteria 란 객체지향 쿼리 빌더 로써 질의문을 java method 로 작성함으로써 sql 을 동적으로 생성할수있다. 가독성이 매우 안좋다.. 하루빨리 querydsl 로 바꿔서 이를 보완하도록하자..


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


# criteria 에서  entity 간의 관계를 맺고 조인하는법 ( metamodel 생성안하고 조인 ) 
```javapublic List<HashMap<String,Object>> getAuthList(String Auth) {
		
		  List<Tuple> tuples = new ArrayList<Tuple>();
		  CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		  List<HashMap<String, Object>> resultArr = new ArrayList<HashMap<String, Object>>();
		  HashMap<String , Object> tupleResultMap = null;
	        
		  CriteriaQuery<Tuple> criteria = criteriaBuilder.createTupleQuery();  
		  Root<ComtnroleInfoVO> comtnroleInfo = criteria.from(ComtnroleInfoVO.class);
	        //Root<ComtauthorroleateVO> comtauthorroleate = criteria.from(ComtauthorroleateVO.class);

   	 	try {
   	 		// join
   	 		Join<ComtnroleInfoVO, ComtauthorroleateVO> comtauthorroleate = comtnroleInfo.join("comtauthorroleateVO", JoinType.LEFT);
   	 		Predicate conditions = criteriaBuilder.equal(comtauthorroleate.get("authorCode"), Auth);
	        criteria.multiselect(comtnroleInfo, comtauthorroleate)
	        .where(conditions);
	        tuples = entityManager.createQuery(criteria).getResultList();
	        
	   	 	for (Tuple tuple : tuples) {
	   	 		tupleResultMap = new HashMap<String , Object>();
	   	 		tupleResultMap.put("roleCode", tuple.get(comtnroleInfo).getRoleCode());
	   	 		tupleResultMap.put("roleCreateDe", tuple.get(comtnroleInfo).getRoleCreatDe());
	   	 		tupleResultMap.put("roleDc", tuple.get(comtnroleInfo).getRoleDc());
	   	 		tupleResultMap.put("roleNm", tuple.get(comtnroleInfo).getRoleNm());
	   	 		tupleResultMap.put("rolePttrn", tuple.get(comtnroleInfo).getRolePttrn());
	   	 		tupleResultMap.put("roleSort", tuple.get(comtnroleInfo).getRoleSort());
	   	 		tupleResultMap.put("authorCode", tuple.get(comtauthorroleate).getAuthorCode());
	   	 		
	   	 		resultArr.add(tupleResultMap);
	   	 	}
   	 	}catch(Exception e) {
   	 		e.printStackTrace();
   	 	}
   	 	return resultArr ; 
	}

```
# criteria 에서 로그인 로직 

``` java
@Service("loginService")
public class LoginService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	AdminUserVO userEntity;

	public AdminUserVO doLogin(String mberId , String password) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdminUserVO> criteriaQuery = criteriaBuilder.createQuery(AdminUserVO.class);
   	 	Root<AdminUserVO> root = criteriaQuery.from(AdminUserVO.class);
   	 	TypedQuery<AdminUserVO> user = null;
   	 	
   	 	Predicate passwordCondition = null ;
	
   	 	passwordCondition = criteriaBuilder.equal(root.get("mberId"),mberId);  
		String encodingPassword = passwordEncoder.encode(password);
		criteriaQuery.where(passwordCondition);
		try {			
			userEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
			boolean isMatchedPassword = passwordEncoder.matches(password, userEntity.getPassword());
			System.out.println("is matched password ? " + isMatchedPassword);
		}catch(Exception e) {
			return null;
		}
		return userEntity;
	}
}



```

이거 특이하게도 passwordEncoder 를 쓰면 password 가 항상 다른 값을 암호화하게되는데 

그냥 디비에서 userId 로 조회한후 , 평문비밀번호와 조회하여 가져온 암호화된 password 를 


```java
passwordEncoder.matches(password, userEntity.getPassword()
```
이 api 를 사용하여 일치하는지 여부를 판별해주면된다.


참고 : https://groups.google.com/g/ksug/c/1W11JJ6AZxc

# queryDsl 방식을 사용하기 
<img width="769" alt="스크린샷 2022-06-17 오전 12 05 24" src="https://user-images.githubusercontent.com/69393030/174100782-16038f20-d505-45fc-b4c6-b5de3bc4d837.png">

<img width="769" alt="스크린샷 2022-06-17 오전 12 05 57" src="https://user-images.githubusercontent.com/69393030/174100911-0b27fab9-acaa-48ca-85c5-348824066406.png">

<img width="769" alt="스크린샷 2022-06-17 오전 12 06 33" src="https://user-images.githubusercontent.com/69393030/174101036-de45e3c3-5bc7-4f92-9380-97a5feb1f45c.png">

criteria 에 비해 가독성도 좋고 , 깔끔하게 작성할수있어서 좋다. 대부분의 사람들 글을 보면 jpa 에 querydsl 조합으로 가는듯하다. 
쿼리 dsl 을 쓰려면 Q class 를 생성해야하는데 , 플러그인으로 자동생성한뒤 Q class를 이용하여 작성할수있다.
querydsl 사용 방법은 다음과같다.

우선 querydsl dependency 를 받고 , 플러그인을 받는다 플러그인은 Q 클래스를 자동으로 생성하도록 도와주는 플러그인이다.

```xml

<dependency>
<groupId>com.querydsl</groupId>
<artifactId>querydsl-apt</artifactId>
<version>${querydsl.version}</version>
<scope>provided</scope>
</dependency>

<dependency>
<groupId>com.querydsl</groupId>
<artifactId>querydsl-jpa</artifactId>
<version>${querydsl.version}</version>
</dependency>




```
첫번째 querydsl-apt 는 q class 자동생성을 위한 의존성이며

두번째 querydsl-jpa 는 querydsl 을 jpa 에서 사용할수있도록 해주는 의존성이다. 

이거 의존성도 잘찾아서 해야한다 필자는 블로그 에서 복붙해서 작동안되서 멘탈이 아스팔트에 갈렸는데 공식문서에있는 의존성을넣어주니 해결되었다. 이럴땐 공식문서를 참조하자 
``` xml

<!-- querydsl plugin -->
<plugin>
        <groupId>com.mysema.maven</groupId>
        <artifactId>apt-maven-plugin</artifactId>
        <version>1.1.3</version>
        <executions>
          <execution>
            <goals>
              <goal>process</goal>
            </goals>
            <configuration>
              <outputDirectory>target/generated-sources/java</outputDirectory>
              <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
            </configuration>
          </execution>
        </executions>
        <dependencies>
          <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-apt</artifactId>
            <version>${querydsl.version}</version>
          </dependency>
        </dependencies>
      </plugin>

```

그런다음 mvn compile 을 하면 target 밑에 generated-source 에 q class 가 생성된다. q class 는 엔티티를 복제하여 querydsl 에서 q class 를 통해 사용할수 있도록한다.


# vs code 에서 qclass 생성안되는경우
확장프로그램에서 
test runner for java , debug for java  버전을 낮추거나 , 사용안함으로 하셈 이거때매 멘탈 갈렸다 

간혹 vscode 에서 

```
.vscode folder in .java is not on the classpath of project

```

이런 오류가 뜨는경우 pom.xml 우클릭하고, maven 업데이트 해준다.

그러면 저 경고가 사라지고 Qclass 를 임포트 해줄수있다.

<img width="936" alt="스크린샷 2022-06-21 오후 11 13 12" src="https://user-images.githubusercontent.com/69393030/174821245-d878c9d5-5202-4f37-b242-a0b7b444f372.png">


위와같이 q class 들을 참조할수있다. 

그래도 안되면 , pom에 명시되어있는 java version 과 , 현재 프로젝트 버젼을 확인해본다.

# queryDsl 사용 (JpaQuery임 쿼리팩토리아님) 

<img width="814" alt="스크린샷 2022-06-16 오전 11 35 09" src="https://user-images.githubusercontent.com/69393030/173978947-190afc2c-11ac-41fa-b72f-f30f9b0b65d2.png">

참고문헌:https://www.inflearn.com/questions/35226


# queryFactory 를 사용하여 querydsl 사용하기 

필자는 criteria query , jpaquery, jpaqueryFactory 3개 간단하게 써봤지만 jpaqueryFactory 를 쓰는 애들도 많은거같고 대부분 쿼리팩토리로 하는거같다 걍 이거써라

# 이럴거면 왜 criteria , jpa query를 왜썻냐 지금까지 

![2gsjgna1uruvUuS7ndh9YqVwYGPLVszbFLwwpAYXZ1rkyz7vKAbhJvHdPRzCvhGfPWQdhkcqKLhnajnHFpGdgkDq3R1XmTFaFxUfKbVyyA3iDi1Fzv](https://user-images.githubusercontent.com/69393030/174218704-523a9fd2-a53d-41c2-9427-20f9e4affa8c.png)

사실 jpa 에서 제공해주는 jparepository만 쓰다가 where 조건을 추가해줘야겠다해서 해주니까 뭔 분기별로 메서드를 구현해야되서 이건좀...

하다가 criteria 를 찾게되었고 마침 1년전에 쌩신입때 하이버네이트 criteria 를 잠깐 유지보수한적이 있어서 해봤다가 와 이건 ... 가독성이 쓰레기다.. 싶어서 찾아보다가 queryDsl 보고 .. 
querydsl 에서 jpaquery 쓰다가 다들 jpaqueryfactory 쓰고있었고.. 응응.. 나만 진심이었지..



# queryDsl queryfactory 에서 연관관계 매핑 안된 엔티티를 join 하는법 

원래 queryDsl 예전 버전에서는 연관관계를 매핑해줘야 join 이 됐었다.
하지만 버전업되면서 연관관계 매핑 없이 포린키로 (컬럼) 으로 조인하는게 가능해졌다.
사실 
이렇게 하기까지 많은 시행착오가 있었다. 애초에 의존성만 잘받았으면 이러지도 않았다. 

조인할때 컬럼 2개이상 셀렉트하게 될텐데

이떄 tuple 로 받아야한다. 안받으면 오류남 근데 다른사람은 잘되는거 같던데 또 나만이러지.. 나만진심이지 진짜 감정쓰레기통이다 나만 당해버렸다 나라서 당했다..

# 조인 
```java
QTestBoardEntity board = new QTestBoardEntity("q1");
        QTestBoardFileEntity file = new QTestBoardFileEntity("q2");
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<Tuple> result = queryFactory
                .select(board, file)
                .from(board)
                .join(file)
                .on(board.fileNo.eq(file.fileNo))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("test!!!====>" + tuple.get(board).getBoardNo());
            System.out.println("test =====>" + tuple.get(file).getFileName());
        }
        return new ResponseEntity<>(
                "",
                HttpStatus.OK);
    }



```

# orderby , offset , limit , where condition 
``` java

@GetMapping("/test2")
    public ResponseEntity<?> test2() {

        QTestBoardEntity board = new QTestBoardEntity("q1");
        QTestBoardFileEntity file = new QTestBoardFileEntity("q2");
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return new ResponseEntity<>(
                queryFactory
                        .select(board)
                        .from(board)
                        .where(board.userName.eq("주환"))
                        .orderBy(board.createdTime.asc())
                        .offset(0)
                        .limit(10)
                        .fetch(),
                HttpStatus.OK);
    }



```

# subquery 사용 

queryDsl 에서 서브쿼리 사용할때 JPAExpressions 를 사용하여 서브쿼리를 작성할수있따.

```java

 @GetMapping("/test3")
    public ResponseEntity<?> test3() {

        /**
         * select *
         * from sys.taiko_board t1
         * left join sys.taiko_board_file t2
         * on t1.file_no = t2.file_no
         * 
         * where t1.board_no = (select board_no from sys.taiko_board where board_no =
         * '290')
         * 
         */
        QTestBoardEntity board = new QTestBoardEntity("q1");
        QTestBoardFileEntity file = new QTestBoardFileEntity("q2");
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        JSONObject obj = new JSONObject();
        List<Tuple> result = queryFactory
                .select(board.contents, board.userName, file.fileName, file.filePath)
                .from(board)
                .leftJoin(file)
                .on(board.fileNo.eq(file.fileNo))
                .where(board.boardNo
                        .in(JPAExpressions
                                .select(board.boardNo)
                                .from(board)
                                .where(board.boardNo.eq(290))))
                .fetch();

        result.forEach(item -> obj.put("data", item.toArray()));

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}





```

# 정말입이 딱벌어집니다 
솔직히 너무힘들었습니다. 새벽 4시까지 삽질하고 삽질하고.. 검색도 해보고 .. 뭔 인도인이 하이버네이트 설명하는 유튜브 영상도 보고 .. 하나도 안들리는 콩글리시 인도인 영어 주먹물고 오열하면서 보면서.. 따라해보고 그랬지만 결국은 의존성주입을 잘못해줬기때문에 일어난 대참사라고 생각합니다.  이걸 현업에서 쓸생각에 뒷목잡고 쓰러질것같습니다. 공부량을 늘려서라도 하이버네이트 쓰도록해야겠습니다.

   ![img](https://user-images.githubusercontent.com/69393030/174218501-351f840f-d070-4d9a-ab72-c219017acf9b.jpg)



# 참고 문헌 

https://ultrakain.gitbooks.io/jpa/content/chapter10/chapter10.4.html : queryDsl 사용법 핸드북인듯하다


https://www.youtube.com/watch?v=3HzIvCF7miQ : youtube 에서  querydsl 에대한 사용법을 영상으로 찍은것인듯하다.


http://querydsl.com/index.html : queryDsl 공식사이트다 신뢰도가 제일높다 



