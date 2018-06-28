/*
 * 描述: <描述>
 * 修改人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.security.dao.JwtSigningKeyDao;
import com.tx.component.security.model.JwtSigningKey;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * JwtSigningKey的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("jwtSigningKeyService")
public class JwtSigningKeyService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(JwtSigningKeyService.class);
    
    @Resource(name = "jwtSigningKeyDao")
    private JwtSigningKeyDao jwtSigningKeyDao;
    
    /**
    * 将jwtSigningKey实例插入数据库中保存<br />
    * 1、如果jwtSigningKey为空时抛出参数为空异常<br />
    * 2、如果jwtSigningKey中部分必要参数为非法值时抛出参数不合法异常<br />
    *
    * <功能详细描述>
    * 
    * @param district [参数说明]
    * 
    * @return void [返回类型说明]
    * @exception throws
    * @see [类、类#方法、类#成员]
    */
    @Transactional
    public void insert(JwtSigningKey jwtSigningKey) {
        //验证参数是否合法
        AssertUtils.notNull(jwtSigningKey, "jwtSigningKey is null.");
        
        this.jwtSigningKeyDao.insert(jwtSigningKey);
    }
    
    /**
     * 根据id删除jwtSigningKey实例<br />
     * 1、如果入参数为空，则抛出异常<br />
     * 2、执行删除后，将返回数据库中被影响的条数<br />
     * 有些业务场景，如果已经被别人删除同样也可以认为是成功的<br />
     * 这里讲通用生成的业务层代码定义为返回影响的条数<br />
     *
     * @param id
     *
     * @return 返回删除的数据条数<br/>
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public int deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        JwtSigningKey condition = new JwtSigningKey();
        condition.setId(id);
        return this.jwtSigningKeyDao.delete(condition);
    }
    
    /**
      * 根据Id查询JwtSigningKey实体
      * 1、当id为empty时抛出异常
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return JwtSigningKey [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public JwtSigningKey findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        JwtSigningKey condition = new JwtSigningKey();
        condition.setId(id);
        
        JwtSigningKey res = this.jwtSigningKeyDao.find(condition);
        return res;
    }
    
    /**
      * 根据JwtSigningKey实体列表
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<JwtSigningKey> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<JwtSigningKey> queryList(Map<String, Object> params) {
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<JwtSigningKey> resList = this.jwtSigningKeyDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 分页查询JwtSigningKey实体列表
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<JwtSigningKey> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<JwtSigningKey> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<JwtSigningKey> resPagedList = this.jwtSigningKeyDao
                .queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
      * 查询jwtSigningKey列表总条数
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int count(Map<String, Object> params) {
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.jwtSigningKeyDao.count(params);
        
        return res;
    }
    
    /**
      * 根据id更新对象
      * <功能详细描述>
      * @param jwtSigningKey
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(JwtSigningKey jwtSigningKey) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(jwtSigningKey, "jwtSigningKey is null.");
        AssertUtils.notEmpty(jwtSigningKey.getId(),
                "jwtSigningKey.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", jwtSigningKey.getId());
        
        //TODO:需要更新的字段
        updateRowMap.put("type", jwtSigningKey.getType());
        updateRowMap.put("signingKeyCode", jwtSigningKey.getSigningKeyCode());
        updateRowMap.put("signingKey", jwtSigningKey.getSigningKey());
        //updateRowMap.put("createDate", jwtSigningKey.getCreateDate());
        updateRowMap.put("duration", jwtSigningKey.getDuration());
        
        int updateRowCount = this.jwtSigningKeyDao.update(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
