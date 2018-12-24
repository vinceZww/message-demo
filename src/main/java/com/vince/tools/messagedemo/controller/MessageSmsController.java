package com.vince.tools.messagedemo.controller;

import com.vince.tools.messagedemo.base.model.SmsSenderResult;
import com.vince.tools.messagedemo.model.BatchSenderSmsPOJO;
import com.vince.tools.messagedemo.model.MessageSmsInfoModel;
import com.vince.tools.messagedemo.service.MessageSmsService;
import com.vince.tools.messagedemo.utils.Base64Util;
import com.vince.tools.messagedemo.utils.DateUtils;
import com.vince.tools.messagedemo.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Vince
 * @version 1.4
 */
@Controller
@RequestMapping(value = "/sms")
public class MessageSmsController {

    @Autowired
    private MessageSmsService smsService;

    @RequestMapping(value="/batchSenderSms/{key}", method= RequestMethod.POST)
    @ResponseBody
    public Object batchSenderSms(@PathVariable(name = "key") String key,@RequestBody BatchSenderSmsPOJO batchSenderSmsPOJO){
        String enKey="vince"+DateUtils.getgetCurrentDateYMD();//最基础的接口内拦截一下，自己修改加密算法
        if(enKey.equals(Base64Util.decode(key))){

        }else {
            return jointResult("4001","密钥验证失败");
        }
        Integer creator = 1;//*
        String templateCode = batchSenderSmsPOJO.getTemplateCode();
        String template = batchSenderSmsPOJO.getTemplate();
        String signName = batchSenderSmsPOJO.getSignName();
        String[] mobileArray = batchSenderSmsPOJO.getPhoneNumbers();
        String smsSerialNumber = batchSenderSmsPOJO.getSerialNumber();
        try {
            //异步
            ExecutorService executor = Executors.newCachedThreadPool() ;
            //每批发送数量
            int batchSize = 500;
            if(mobileArray.length>batchSize){
                //总页数
                int total=(int)Math.ceil((double)mobileArray.length/batchSize);
                for (int i = 0; i < total; i++) {
                    String res ="";
                    int inPageSize = (i+1) * batchSize;
                    for(int j = i*batchSize;j<inPageSize;j++){
                        if(j<mobileArray.length){
                            res = res+","+mobileArray[j];
                        }else {
                            break;
                        }
                    }
                    String phoneNumbers = res.substring(1, res.length());
                    smsService.savePrepareSmsEntity(creator,batchSenderSmsPOJO);//*
                    executor.submit(new Runnable() {
                        SmsSenderResult smsSenderResult = null;
                        @Override
                        public void run() {
                            try {
                                //发送短信
                                smsSenderResult = SmsUtils.sendSms(phoneNumbers, templateCode, template, signName);
                                Thread.sleep(8000);//等待8秒后进行回调
                                smsService.smsBatchStatusPullResult(smsSerialNumber, mobileArray, smsSenderResult.getBizId(), DateUtils.getgetCurrentDateYMD());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }else {
                String res2 ="";
                for(String s:mobileArray) {
                    res2 = res2 +","+ s;
                }
                String phoneNumbers = res2.substring(1, res2.length());
                smsService.savePrepareSmsEntity(creator,batchSenderSmsPOJO);//有个坑
                executor.submit(new Runnable() {
                    SmsSenderResult smsSenderResult = null;
                    @Override
                    public void run() {
                        try {
                            String smsNumberNo = null;
                            //发送短信
                            smsSenderResult = SmsUtils.sendSms(phoneNumbers,templateCode,template,signName);
                            //进行回调
                            smsService.smsBatchStatusPullResult(smsSerialNumber,mobileArray,smsSenderResult.getBizId(), DateUtils.getgetCurrentDateYMD());
//                            smsService.smsBatchStatusPullResult(smsSerialNumber,mobileArray,"164824045553326059^0", DateUtils.getgetCurrentDateYMD());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            return  jointResult("1","正在努力发送中...");
        }catch (Exception e){
            e.printStackTrace();
            return  jointResult("4002","发送异常，请检查参数。");
        }
    }

    @RequestMapping(value="/cycleSenderSms/{key}", method= RequestMethod.POST)
    public Object cycleSenderSms(@PathVariable(name = "key") String key,@RequestBody Map listBatchSenderSms){
        String enKey = "vince"+DateUtils.getgetCurrentDateYMD();//*
        if(enKey.equals(Base64Util.decode(key))){

        }else {
            return jointResult("4001","密钥验证失败");
        }
        Object serialNumber = listBatchSenderSms.get("serialNumber");
        Integer creator = 1;//*
        List<Map<String,Object>> batchSenderSmsPOJOList = (List<Map<String,Object>>) listBatchSenderSms.get("smsList");
        try {
            if(batchSenderSmsPOJOList.size()>0) {
                //异步
                ExecutorService executor = Executors.newCachedThreadPool() ;
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //线程池
                            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
                            int pageSize = 2000;
                            //总页数
                            int total = (int) Math.ceil((double) batchSenderSmsPOJOList.size() / pageSize);
                            for (int i = 0; i < total; i++) {
                                int inPageSize=(i+1)*pageSize;
                                for(int j = i*pageSize;j<inPageSize;j++){
                                    if(j<batchSenderSmsPOJOList.size()){
                                        // 得到每个对象
                                        Map batchSenderSms = batchSenderSmsPOJOList.get(j);
                                        //内容拼装
                                        String phoneNumber = batchSenderSms.get("phoneNumber").toString();
                                        String templateCode = batchSenderSms.get("templateCode").toString();
                                        String template = batchSenderSms.get("template").toString();
                                        String signName = batchSenderSms.get("signName").toString();
                                        smsService.savePrepareSmsEntity(creator,serialNumber.toString(),phoneNumber);//*
                                        fixedThreadPool.execute(new Runnable() {
                                            SmsSenderResult smsSenderResult = null;
                                            @Override
                                            public void run() {
                                                try {
                                                    //发送短信
                                                    smsSenderResult = SmsUtils.sendSms(phoneNumber, templateCode, template, signName);
                                                    //进行回调
                                                    Thread.sleep(3000);
                                                    smsService.smsStatusPullResult(serialNumber.toString(), phoneNumber, smsSenderResult.getBizId(),DateUtils.getgetCurrentDateYMD());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    System.out.println("出现异常");
                                                }
                                            }
                                        });
                                    }else {
                                        break;
                                    }
                                }
                                if(total>1){
                                    Thread.sleep(5000);//每发完2000调进行一次等待
                                }
                            }
                            fixedThreadPool.shutdown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else {
                return jointResult("4003","数据为空无法发送短信");
            }
            return  jointResult("1","正在努力发送中...");

        }catch (Exception e){
            e.printStackTrace();
            return  jointResult("4002","发送异常，请检查参数。");
        }
    }

    /**
     * 根据流水号获取短信发送信息
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/getSmsSenderDetailsList", method= RequestMethod.POST)
    @ResponseBody
    public Object getSmsSenderDetailsList(@RequestBody Map queryParam){
        String smsNumberNo = queryParam.get("smsNumberNo").toString();
        String page = queryParam.get("page").toString();
        String size = queryParam.get("size").toString();
        if(StringUtils.isBlank(page)){
            page = "1";
        }
        if(StringUtils.isBlank(size)){
            size = "10";
        }
        Integer ipage = Integer.parseInt(page);
        Integer isize = Integer.parseInt(size);


        List<MessageSmsInfoModel> resultList = smsService.querySmsResult(smsNumberNo,ipage,isize);
        Integer totalSize = smsService.querySmsResultTotal(smsNumberNo.toString());

        Map<String,Object> result = new HashMap<>();
        result.put("code",1);
        result.put("data",resultList);
        result.put("totalSize",totalSize);
        return result;
    }




    /**
     * 返回信息拼装
     * 后续版本优化点
     */
    private Map<String,Object> jointResult(String code, String message){
        Map<String,Object> result = new HashMap<>();
        result.put("code",code);
        result.put("message",message);
        return result;
    }
}
