package com.rocky.controller;//package com.rocky;

import com.alibaba.fastjson.JSONObject;
import com.lianpay.api.util.TraderRSAUtil;
import com.rocky.bean.NotifyResponseBean;
import com.rocky.constant.ConfigConstant;
import com.rocky.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: TODO
 * @Author Baizhen
 * @Date 2020/6/28 14:53
 */
@Controller
public class ResponseController {

	private static final Logger logger = LoggerFactory.getLogger(ResponseController.class);

	/**
	 * @param jsonObject
	 * @return
	 */
	@RequestMapping(value = "/api/signRequest", method = RequestMethod.POST)
    @ResponseBody
	public NotifyResponseBean signRequest(@RequestBody JSONObject jsonObject) {
		logger.info("sign request:" + jsonObject);
		boolean signCheck = TraderRSAUtil.checksign(ConfigConstant.PUBLIC_KEY_ROCKY,
				SignUtil.genSignData(jsonObject),
                jsonObject.getString("sign"));

		NotifyResponseBean responseBean = new NotifyResponseBean();
		if (!signCheck) {
			// 传送数据被篡改，可抛出异常
			logger.error("返回结果验签异常,可能数据被篡改");
			// 回调内容先验签，再处理相应逻辑
			responseBean.setRet_code("9999");
			responseBean.setRet_msg("未知异常");
			return responseBean;
		}

		//先验签
		responseBean.setRet_code("0000");
		responseBean.setRet_msg("验签成功");
		return responseBean;
	}

}
