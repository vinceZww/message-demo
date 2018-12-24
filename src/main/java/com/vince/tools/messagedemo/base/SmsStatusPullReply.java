package com.vince.tools.messagedemo.base;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.vince.tools.messagedemo.base.model.SmsStatusPullReplyResult;

import java.util.List;

/**
 * @author Vince
 * @version 1.4
 * 回调底层类
 */
public class SmsStatusPullReply {
    private static final String accessKeyId = "youraccessKeyId";//2.0版本写入配置文件
    private static final String accessKeySecret = "youraccessKeySecret";
    private static final String product = "Dysmsapi";
    private static final String domain = "dysmsapi.aliyuncs.com";

    public SmsStatusPullReplyResult replyAliSms(String phoneNumber, String bizId, String sendDate) throws Exception {
        SmsStatusPullReplyResult result;
        result = this.smsReplyAlisms(phoneNumber,bizId,sendDate);
        return result;
    }

    public static SmsStatusPullReplyResult smsReplyAlisms(String phoneumber, String bizId, String sendDate)throws  Exception{
        //设置超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber(phoneumber);
        request.setBizId(bizId);//可选-调用发送短信接口时返回的BizId
        request.setSendDate(sendDate);//必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd
        request.setPageSize(10L);//必填-页大小
        request.setCurrentPage(1L);//必填-当前页码从1开始计数
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        SmsStatusPullReplyResult result;
        //获取返回结果
        if(querySendDetailsResponse.getCode() != null && querySendDetailsResponse.getCode().equals("OK")){
            result = new SmsStatusPullReplyResult();
            List<QuerySendDetailsResponse.SmsSendDetailDTO> smsSendDetailDTOList = querySendDetailsResponse.getSmsSendDetailDTOs();
            if(smsSendDetailDTOList.size()>0){
                //只记录第一条。漏洞：会出现同一个流水号，同一个账号，发了重复短信的情况！
                result.setResult("1");
                result.setMessage(smsSendDetailDTOList.get(0).getContent());
                result.setSendStatus(3);
                result.setTemplateCode(smsSendDetailDTOList.get(0).getTemplateCode());
                result.setSendDate(smsSendDetailDTOList.get(0).getSendDate());
                result.setReceiveDate(smsSendDetailDTOList.get(0).getReceiveDate());
            }else {
                result.setResult("5051");
                result.setSendStatus(2);
                result.setMessage("无任何记录！");
            }
        }else {
            result = new SmsStatusPullReplyResult();
            result.setResult("5002");
            result.setSendStatus(2);
            result.setMessage(querySendDetailsResponse.getMessage());
        }
        return  result;
    }
}
