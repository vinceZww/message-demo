package com.vince.tools.messagedemo.base.model;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

/**
 * @author Vince
 * @version 1.4
 *
 */
public class SmsSenderResult extends SendSmsResponse {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
