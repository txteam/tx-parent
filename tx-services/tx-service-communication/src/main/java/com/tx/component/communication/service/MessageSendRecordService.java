/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.communication.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tx.component.communication.dao.MessageSendRecordDao;
import com.tx.component.communication.model.MessageSendRecord;
import com.tx.component.communication.model.MessageSendStatusEnum;
import com.tx.component.communication.model.MessageTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MessageSendRecord的业务层
 * <功能详细描述>
 *
 * @author
 * @version [版本号, ]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("messageSendRecordService")
public class MessageSendRecordService {

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(MessageSendRecordService.class);

    @Resource
    private MessageSendRecordDao messageSendRecordDao;

    /**
     * 将messageSendRecord实例插入数据库中保存
     * 1、如果messageSendRecord为空时抛出参数为空异常
     * 2、如果messageSendRecord中部分必要参数为非法值时抛出参数不合法异常
     * <功能详细描述>
     *
     * @param messageSendRecord [参数说明]
     * @return void [返回类型说明]
     * @throws throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insertMessageSendRecord(MessageSendRecord messageSendRecord) {
        //验证参数是否合法
        AssertUtils.notNull(messageSendRecord, "messageSendRecord is null.");

        Date now = new Date();
        //为添加的数据需要填入默认值的字段填入默认值
        messageSendRecord.setLastSendDate(now);
        messageSendRecord.setCreateDate(now);

        //调用数据持久层对实体进行持久化操作
        this.messageSendRecordDao.insertMessageSendRecord(messageSendRecord);
    }

    /**
     * 根据id删除messageSendRecord实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数
     *
     * @param id
     * @return int [返回类型说明]
     * @throws throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");

        MessageSendRecord condition = new MessageSendRecord();
        condition.setId(id);
        int resInt = this.messageSendRecordDao.deleteMessageSendRecord(condition);
        return resInt > 0;
    }

    /**
     * 根据Id查询MessageSendRecord实体
     * 1、当id为empty时抛出异常
     * <功能详细描述>
     *
     * @param id
     * @return MessageSendRecord [返回类型说明]
     * @throws throws 可能存在数据库访问异常DataAccessException
     * @see [类、类#方法、类#成员]
     */
    public MessageSendRecord findMessageSendRecordById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");

        MessageSendRecord condition = new MessageSendRecord();
        condition.setId(id);

        MessageSendRecord res = this.messageSendRecordDao.findMessageSendRecord(condition);
        return res;
    }

    /**
     * 查询MessageSendRecord实体列表
     * <功能详细描述>
     *
     * @param type
     * @param maxCreateDate
     * @param status
     * @param minCreateDate
     * @return List<MessageSendRecord> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<MessageSendRecord> queryMessageSendRecordList(
            MessageTypeEnum type, String receiver, Date maxCreateDate,
            MessageSendStatusEnum status, Date minCreateDate) {
        //判断条件合法性

        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("receiver", receiver);
        params.put("maxCreateDate", maxCreateDate);
        params.put("status", status);
        params.put("minCreateDate", minCreateDate);

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<MessageSendRecord> resList = this.messageSendRecordDao.queryMessageSendRecord(params);

        return resList;
    }

    /**
     * 分页查询MessageSendRecord实体列表
     *
     * @param type
     * @param maxCreateDate
     * @param status
     * @param minCreateDate
     * @param pageIndex     当前页index从1开始计算
     * @param pageSize      每页显示行数
     *                      <p>
     *                      <功能详细描述>
     * @return List<MessageSendRecord> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PageInfo<MessageSendRecord> queryMessageSendRecordPagedList(
            MessageTypeEnum type, String receiver, Date maxCreateDate,
            MessageSendStatusEnum status, Date minCreateDate, int pageIndex,
            int pageSize) {
        //T判断条件合法性

        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("receiver", receiver);
        params.put("maxCreateDate", maxCreateDate);
        params.put("status", status);
        params.put("minCreateDate", minCreateDate);

        PageHelper.startPage(pageIndex, pageSize);
        List<MessageSendRecord> resPagedList = this.messageSendRecordDao.queryMessageSendRecord(params);
        PageInfo<MessageSendRecord> page = new PageInfo<MessageSendRecord>(resPagedList);


        return page;
    }


    /**
     * 判断是否已经存在<br/>
     * <功能详细描述>
     *
     * @return int [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isExist(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");

        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeId", excludeId);

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.messageSendRecordDao.queryMessageSendRecordCount(params);

        return res > 0;
    }

    /**
     * 根据id更新对象
     * <功能详细描述>
     *
     * @param messageSendRecord
     * @return boolean [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(MessageSendRecord messageSendRecord) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(messageSendRecord, "messageSendRecord is null.");
        AssertUtils.notEmpty(messageSendRecord.getId(),
                "messageSendRecord.id is empty.");

        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", messageSendRecord.getId());

        //需要更新的字段
        updateRowMap.put("errorCode", messageSendRecord.getErrorCode());
        updateRowMap.put("errorMessage", messageSendRecord.getErrorMessage());
        updateRowMap.put("type", messageSendRecord.getType());
        updateRowMap.put("success", messageSendRecord.isSuccess());
        updateRowMap.put("status", messageSendRecord.getStatus());
        updateRowMap.put("lastSendDate", messageSendRecord.getLastSendDate());
        updateRowMap.put("failCount", messageSendRecord.getFailCount());
        updateRowMap.put("remark", messageSendRecord.getRemark());

        int updateRowCount = this.messageSendRecordDao.updateMessageSendRecord(updateRowMap);

        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }

}
