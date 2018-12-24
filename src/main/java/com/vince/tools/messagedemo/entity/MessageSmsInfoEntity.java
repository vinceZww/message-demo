package com.vince.tools.messagedemo.entity;

import javax.persistence.*;
import java.sql.Date;
/**
 * @author Vince
 * @version 1.4
 */
@Entity
@Table(name = "message_sms_info")
public class MessageSmsInfoEntity {

    @Id//主键
    @GeneratedValue(strategy= GenerationType.IDENTITY)//自增长
    private Long id;//id
    @Column(nullable = false)//不可空
    private Integer creator;
    @Column(nullable = false)
    private Integer updater;
    @Column(nullable = false)//不可空
    private String serialNumber;
    @Column(nullable = false)//不可空
    private String phoneNum;

    private Integer sendStatus;

    private String errCode;

    private String templateCode;

    private String content;

    private Date sendDate;

    private Date receiveDate;

    private String bizId;

    private Integer isDel;

    private String remark;

    public MessageSmsInfoEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "MessageSmsInfoEntity{" +
                "id=" + id +
                ", creator=" + creator +
                ", updater=" + updater +
                ", serialNumber='" + serialNumber + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", sendStatus=" + sendStatus +
                ", errCode='" + errCode + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                ", receiveDate=" + receiveDate +
                ", bizId='" + bizId + '\'' +
                ", isDel='" + isDel + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
