package com.vince.tools.messagedemo.base.model;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;

/**
 * @author Vince
 * @version 1.4
 */
public class SmsStatusPullReplyResult extends QuerySendDetailsResponse {
    private String result;

    private Integer sendStatus;

    private String templateCode;

    private String sendDate;

    private String receiveDate;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }
}
