package com.vince.tools.messagedemo.utils;

import com.vince.tools.messagedemo.base.SmsSender;
import com.vince.tools.messagedemo.base.SmsStatusPullReply;
import com.vince.tools.messagedemo.base.model.SmsSenderResult;
import com.vince.tools.messagedemo.base.model.SmsStatusPullReplyResult;
/**
 * @author Vince
 * @version 1.4
 */
public class SmsUtils {
    private static final String APP_ID ="";//坑
    private static final String APP_KEY = "";

    /**
     * 发送短信
     * @param phoneNumbers 手机号码
     * @param templateCode 短信模板
     * @param template JSON参数
     * @param sign 短信签名
     * @return
     * @throws Exception
     */
    public static SmsSenderResult sendSms(String phoneNumbers, String templateCode, String template, String sign) throws Exception {
        SmsSender singleSender = new SmsSender(APP_ID, APP_KEY);
        SmsSenderResult batchSenderResult;
        try {
            batchSenderResult = singleSender.sendWithParam(phoneNumbers, templateCode, template, sign);
            return batchSenderResult;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 短信结果回调
     * @param phoneNumber 手机号码
     * @param bizId 发送阿里回调流水号
     * @param sendDate 短信发送日期格式yyyyMMdd
     * @return
     * @throws Exception
     */
    public static SmsStatusPullReplyResult replyAlism(String phoneNumber, String bizId, String sendDate) throws Exception {
        SmsStatusPullReply smsStatusPullReply = new SmsStatusPullReply();
        SmsStatusPullReplyResult smsStatusPullReplyResultPo;
        try {
            smsStatusPullReplyResultPo = smsStatusPullReply.replyAliSms(phoneNumber,bizId,sendDate);
            return smsStatusPullReplyResultPo;
        } catch (Exception e) {
            throw e;
        }
    }



}