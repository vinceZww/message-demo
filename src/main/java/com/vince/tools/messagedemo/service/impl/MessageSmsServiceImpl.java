package com.vince.tools.messagedemo.service.impl;

import com.vince.tools.messagedemo.base.model.SmsStatusPullReplyResult;
import com.vince.tools.messagedemo.dao.MessageSmsInfoDao;
import com.vince.tools.messagedemo.entity.MessageSmsInfoEntity;
import com.vince.tools.messagedemo.model.BatchSenderSmsPOJO;
import com.vince.tools.messagedemo.model.MessageSmsInfoModel;
import com.vince.tools.messagedemo.service.MessageSmsService;
import com.vince.tools.messagedemo.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Vince
 * @version 1.4
 */
@Service(value = "messageSmsService")
public class MessageSmsServiceImpl implements MessageSmsService {

    @Autowired
    MessageSmsInfoDao messageSmsInfoDao;

    @Override
    public Integer savePrepareSmsEntity(Integer creator, BatchSenderSmsPOJO batchSenderSmsPOJO) {
        //这里不太好，后续改为sql
        String[] phoneNumbers = batchSenderSmsPOJO.getPhoneNumbers();
        for (int i = 0; i < phoneNumbers.length; i++) {
            MessageSmsInfoEntity entity = new MessageSmsInfoEntity();
            entity.setCreator(creator);
            entity.setUpdater(creator);
            entity.setSerialNumber(batchSenderSmsPOJO.getSerialNumber());
            entity.setPhoneNum(phoneNumbers[i]);
            entity.setSendStatus(1);
            entity.setIsDel(0);
            messageSmsInfoDao.save(entity);
        }
        return 1;
    }

    @Override
    public Integer savePrepareSmsEntity(Integer creator,String serialNumber,String phoneNumber) {
        MessageSmsInfoEntity entity = new MessageSmsInfoEntity();
        entity.setCreator(creator);
        entity.setUpdater(creator);
        entity.setSerialNumber(serialNumber);
        entity.setPhoneNum(phoneNumber);
        entity.setSendStatus(1);
        entity.setIsDel(0);
        messageSmsInfoDao.save(entity);
        return null;
    }

    @Override
    public Map<String, Object> smsStatusPullResult(String smsSerialNumber, String phoneNumber, String bizId, String sendDate) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        SmsStatusPullReplyResult smsStatusPullReplyResult;
        if(StringUtils.isBlank(bizId)){
            resultMap.put("code","5004");
            resultMap.put("message","回调异常！回调ID不能为空");
            return resultMap;
        }
        try {
            smsStatusPullReplyResult = SmsUtils.replyAlism(phoneNumber, bizId, sendDate);
            if(smsStatusPullReplyResult.getResult().equals("1")){
                Long id = messageSmsInfoDao.findMessageSmsInfoIdBySerialNumberAnd(smsSerialNumber,phoneNumber);
                Integer updater = 1;
                Integer sendStatus = smsStatusPullReplyResult.getSendStatus();
                String errCode = smsStatusPullReplyResult.getCode();
                String templateCode = smsStatusPullReplyResult.getTemplateCode();
                String content = smsStatusPullReplyResult.getMessage();
                String sendDate1 =smsStatusPullReplyResult.getSendDate();
                String receiveDate =smsStatusPullReplyResult.getReceiveDate();
                Integer updateId = messageSmsInfoDao.updateMessageSmsInfoEntity(id,updater,sendStatus,errCode,templateCode,content,sendDate1,receiveDate,bizId);
                //日志记录结果
            }
        } catch (Exception e) {
            e.printStackTrace();
            return resultMap;
        }
        resultMap.put("smsReplyResultInput",1);
        resultMap.put("smsReplyResultInputConten","入库成功");
        return resultMap;
    }

    @Override
    public Map<String, Object> smsBatchStatusPullResult(String smsSerialNumber, String[] phoneNumbers, String bizId, String sendDate) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        SmsStatusPullReplyResult smsStatusPullReplyResult;
        if(StringUtils.isBlank(bizId)){
            resultMap.put("code","5004");
            resultMap.put("message","回调异常！回调ID不能为空");
            return resultMap;
        }
        try {
            for (String phoneNumber:phoneNumbers) {
                //获取回调结果
                Thread.sleep(500);
                smsStatusPullReplyResult = SmsUtils.replyAlism(phoneNumber, bizId, sendDate);
                if(smsStatusPullReplyResult.getResult().equals("1")){
                    Long id = messageSmsInfoDao.findMessageSmsInfoIdBySerialNumberAnd(smsSerialNumber,phoneNumber);
                    Integer updater = 1;
                    Integer sendStatus = smsStatusPullReplyResult.getSendStatus();
                    String errCode = smsStatusPullReplyResult.getCode();
                    String templateCode = smsStatusPullReplyResult.getTemplateCode();
                    String content = smsStatusPullReplyResult.getMessage();
                    String sendDate1 =smsStatusPullReplyResult.getSendDate();
                    String receiveDate =smsStatusPullReplyResult.getReceiveDate();
                    Integer updateId = messageSmsInfoDao.updateMessageSmsInfoEntity(id,updater,sendStatus,errCode,templateCode,content,sendDate1,receiveDate,bizId);
                    //日志记录结果
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return resultMap;
        }
        //暂时没用
        resultMap.put("smsReplyResultInput",1);
        resultMap.put("smsReplyResultInputConten","入库成功");
        return resultMap;
    }

    @Override
    public List<MessageSmsInfoModel> querySmsResult(String smsNumberNo, Integer page, Integer size) {
        Integer from = (page-1) * size;

        List<MessageSmsInfoEntity> MessageSmsInfoList =  messageSmsInfoDao.findSmsListBySerialNumber(smsNumberNo,from,size);
        List<MessageSmsInfoModel> result = new ArrayList<>();

        for (MessageSmsInfoEntity entity :MessageSmsInfoList) {
            MessageSmsInfoModel model = new MessageSmsInfoModel();
            model.setSerialNumber(entity.getSerialNumber());
            model.setContent(entity.getContent());
            model.setErrCode(entity.getErrCode());
            model.setPhoneNum(entity.getPhoneNum());
            model.setReceiveDate(entity.getReceiveDate());
            model.setSendDate(entity.getSendDate());
            String SendStatusName = "等待回执";//优化枚举
                    if(entity.getSendStatus()==2){
                            SendStatusName = "发送失败";
                    }else if(entity.getSendStatus()==3){
                        SendStatusName = "发送成功";
                    }
            model.setSendStatusName(SendStatusName);
            result.add(model);
        }

        return result;
    }

    @Override
    public Integer querySmsResultTotal(String smsNumberNo) {
        return messageSmsInfoDao.findSmsListBySerialNumberTotal(smsNumberNo);
    }

}
