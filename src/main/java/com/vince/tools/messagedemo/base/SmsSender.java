package com.vince.tools.messagedemo.base;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.vince.tools.messagedemo.base.model.SmsSenderResult;

/**
 * @author Vince
 * @version 1.4
 * 发送底层
 */
public class SmsSender {
    private static final String accessKeyId = "youraccessKeyId";//2.0版本写入配置文件
    private static final String accessKeySecret = "youraccessKeySecret";
    private static final String product = "Dysmsapi";
    private static final String domain = "dysmsapi.aliyuncs.com";

    public SmsSender(String accessKeyId,String accessKeySecret) {}

    public SmsSenderResult sendWithParam(String phoneNumbers, String templateCode, String template, String sign) throws Exception {
        //日志
        SmsSenderResult result = null;
        result = this.smsSenderAlism(phoneNumbers, templateCode,template,sign);
        return result;
    }

    public SmsSenderResult smsSenderAlism(String phoneNumber, String templateCode, String template, String signName) throws Exception {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou",product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phoneNumber);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(template);

        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        //加日志
        SmsSenderResult result;
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            result = new SmsSenderResult();
            result.setResult("1");
            result.setMessage(sendSmsResponse.getMessage());
            result.setBizId(sendSmsResponse.getBizId());
        } else {
            result = new SmsSenderResult();
            result.setResult("5001");
            result.setMessage(sendSmsResponse.getMessage());
        }
        return result;
    }

}