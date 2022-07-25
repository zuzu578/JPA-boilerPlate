package egovframework.api.admin.user.querybuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.api.admin.repository.AdminApiUserRepository;
import egovframework.api.admin.user.service.AdminApiUserVO;
import egovframework.api.admin.utils.Message;
import egovframework.api.admin.utils.MessageCode;
import egovframework.api.admin.utils.PageCalculator;
import egovframework.api.admin.utils.StringUtils;
import egovframework.system.user.service.SystemAdminUserService;
import egovframework.system.user.service.SystemAdminUserVO;
import egovframework.system.util.ResultBody;
import egovframework.system.util.ResultMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiUserQueryBuilder {
	@PersistenceContext
	EntityManager entityManager;
	
	AdminApiUserRepository adminApiUserRepository;
	
	@Autowired
	ApiUserQueryBuilder(AdminApiUserRepository adminApiUserRepository){
		this.adminApiUserRepository = adminApiUserRepository;
	}
	
	public ResultBody getApiUser(HashMap<String , Object> paramMap){
		
		ResultBody resultBody = new ResultBody();
		
		List<AdminApiUserVO> resultList = new ArrayList<AdminApiUserVO>();
		AdminApiUserVO adminApiUserVO = new AdminApiUserVO();
		HashMap<String , Object >resultMap = new HashMap<String , Object>();
		HashMap<String , Object> pageNationMap = new HashMap<String , Object>();
		HashMap<String , Object> results = new HashMap<String , Object>();

		// Create List Query
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdminApiUserVO> criteriaQuery = criteriaBuilder.createQuery(AdminApiUserVO.class);
		Root<AdminApiUserVO> root = criteriaQuery.from(AdminApiUserVO.class);
		
		// Create Count Query
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<AdminApiUserVO> userCnt = countQuery.from(AdminApiUserVO.class);
				
		List<Predicate> predicates = new ArrayList<Predicate>();
				
		try {
			// 페이징 변수 정리
			int pageSize = Integer.parseInt(StringUtils.nvl(StringUtils.nvl(paramMap.get("perPage")), "10"));
			int pageNumber = Integer.parseInt(StringUtils.nvl(StringUtils.nvl(paramMap.get("page")), "1"));
			// 검색 파라미터 정리
			String delYn = StringUtils.nvl(paramMap.get("delYn"), "N");
			String stDate = StringUtils.nvl(paramMap.get("stDate"));
			String edDate = StringUtils.nvl(paramMap.get("edDate"));
			String sKey = StringUtils.nvl(paramMap.get("sKey"));
			String sWords = StringUtils.nvl(paramMap.get("sWords"));
			String sSort = StringUtils.nvl(paramMap.get("sSort"),"createdAt");
			
			predicates.add(criteriaBuilder.equal(root.get("delYn"), delYn));
			
			if( !stDate.equals("") ) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), stDate));
			}
			
			if( !edDate.equals("") ) {
				edDate = edDate + " 23:59:59";
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), edDate));
			}
			
			if( !sKey.equals("") && !sWords.equals("") ) {
				predicates.add(criteriaBuilder.like(root.get(sKey), "%"+sWords+"%"));
			}
			
			if ( !sSort.equals("") ) {
			}
			if(paramMap.get("mberIdx") == null && paramMap.get("page") != null && paramMap.get("perPage")!= null) {
				// 정렬조건 정의
				Order orderBy = criteriaBuilder.desc(root.get(sSort));
				
				
				// create query
				criteriaQuery
					.where(predicates.toArray(new Predicate[predicates.size()]))
					.orderBy(orderBy)
					;
							
				// select result
				resultList = entityManager
								.createQuery(criteriaQuery)
								.setFirstResult((pageNumber-1) * pageSize)
								.setMaxResults(pageSize)
								.getResultList()
								;		
				// create query
				countQuery
					.select(criteriaBuilder.count(userCnt))
					.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])))
					;
				// select count
				Long totalCount = entityManager
									.createQuery(countQuery)
									.getSingleResult()
									;
				pageNationMap.put("page", pageNumber);
				pageNationMap.put("totalCount", totalCount);
				
				resultMap.put("pagination", pageNationMap);
				resultMap.put("contents", resultList);
				
				resultBody.setResult(true);
				resultBody.setCode(ResultMessage.SUCCESS.getCode());
				resultBody.setMessage(ResultMessage.SUCCESS.getMessage());
				resultBody.setData(resultMap);	
				
			}else {
				if(paramMap.get("mberIdx") == null) {
					results.put("code", MessageCode.noMberIdx);
					results.put("message", Message.noMberIdx);
				}else {
					Predicate condition1 = criteriaBuilder.equal(root.get("mberIdx"),Integer.parseInt(paramMap.get("mberIdx").toString()));	
					Predicate deleteCondition = criteriaBuilder.isNull(root.get("deletedAt"));	
					Predicate apiUserCondition = criteriaBuilder.and(condition1,deleteCondition);
					
					criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdAt"))).where(apiUserCondition);
					adminApiUserVO = entityManager.createQuery(criteriaQuery).getSingleResult();
					
					resultBody.setResult(true);
					resultBody.setCode(ResultMessage.SUCCESS.getCode());
					resultBody.setMessage(ResultMessage.SUCCESS.getMessage());
					resultBody.setData(adminApiUserVO);
				}
			}
		}catch(NoResultException e) {
			resultBody.setResult(true);
			resultBody.setCode(ResultMessage.NODATA.getCode());
			resultBody.setMessage(ResultMessage.NODATA.getMessage());
		}catch(Exception e) {
			log.error(e.getMessage());
			resultBody.setResult(false);
			resultBody.setCode(ResultMessage.FAILURE.getCode());
			resultBody.setMessage(ResultMessage.FAILURE.getMessage());
		}
		return resultBody;
	}
}
