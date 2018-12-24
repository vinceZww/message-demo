package com.vince.tools.messagedemo.service;

import com.vince.tools.messagedemo.entity.MessageSmsInfoEntity;
import com.vince.tools.messagedemo.model.BatchSenderSmsPOJO;
import com.vince.tools.messagedemo.model.MessageSmsInfoModel;

import java.util.List;
import java.util.Map;

/**
 * @author Vince
 * @version 1.4
 */
public interface MessageSmsService {

    /**
     * 批量保存准备发送的短信
     *
     */
    Integer savePrepareSmsEntity(Integer creator, BatchSenderSmsPOJO batchSenderSmsPOJO);


    /**
     * 单条保存准备发送的短信
     *
     */
    Integer savePrepareSmsEntity(Integer creator,String serialNumber,String phoneNumber);

    /**
     * 单条短信回调
     *
     * @param phoneNumber 手机号码
     * @param bizId       发送流水号
     * @param sendDate    短信发送日期格式yyyyMMdd
     * @return
     * @throws Exception
     */
    Map<String, Object> smsStatusPullResult(String smsSerialNumber,String phoneNumber, String bizId, String sendDate);

    /**
     * 批量短信回调
     *
     * @param phoneNumbers 手机号码列表
     * @param bizId        发送流水号
     * @param sendDate     短信发送日期格式yyyyMMdd
     * @return
     * @throws Exception
     */
    Map<String, Object> smsBatchStatusPullResult(String smsSerialNumber, String[] phoneNumbers, String bizId, String sendDate);


    /**
     * 单条短信回调
     *
     * @param smsNumberNo 发送流水号
     * @param page        页码
     * @param size        每页大小
     * @return
     */
    List<MessageSmsInfoModel> querySmsResult(String smsNumberNo, Integer page, Integer size);

    /**
     * 回调结果总数
     *
     * @param smsNumberNo 发送流水号
     * @return
     */
    Integer querySmsResultTotal(String smsNumberNo);



}
