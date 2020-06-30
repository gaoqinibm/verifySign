package com.rocky.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rocky.bean.StudentRequestBean;
import com.rocky.constant.ConfigConstant;
import com.rocky.util.HttpUtil;
import com.rocky.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: TODO
 * @Author Baizhen
 * @Date 2020/6/28 14:51
 */
@Controller
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @RequestMapping(value = "/reqApi/reqData", method = RequestMethod.POST)
    public void reqData() {
        StudentRequestBean studentRequestBean = new StudentRequestBean();
        studentRequestBean.setId("5929");
        studentRequestBean.setName("张三");
        studentRequestBean.setPassword("20170317165929");
        studentRequestBean.setCard_no("6216261000000000000");
        studentRequestBean.setOid_partner(ConfigConstant.OID_PARTNER);
        studentRequestBean.setSign_type(ConfigConstant.SIGN_TYPE);
        studentRequestBean.setSign(SignUtil.genRSASign(JSON.parseObject(JSON.toJSONString(studentRequestBean))));

        String jsonStr = JSON.toJSONString(studentRequestBean);
        logger.info("请求报文数据：" + jsonStr);

//        String encryptStr = LianLianPaySecurity.encrypt(jsonStr, PaymentConstant.PRIVATE_KEY_ROCKY);
//        if (StringUtils.isEmpty(encryptStr)) {
//            // 加密异常
//            logger.error("加密异常:");
//            return;
//        }

        //System.out.println("pay_load: " + encryptStr);

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        logger.info("请求jsonObject报文数据：" + jsonObject);

        String response = HttpUtil.doPost("http://127.0.0.1:8080/api/signRequest", jsonObject, "UTF-8");
        System.out.println("付款接口返回响应报文：" + response.toString());

        logger.info("付款接口返回响应报文：" + response);
    }
}
