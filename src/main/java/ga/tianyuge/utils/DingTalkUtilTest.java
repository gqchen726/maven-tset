package ga.tianyuge.utils;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 钉钉工具类
 * @author: GuoqingChen
 * @Time: 2023/07/18 14:43
 * @Email: guoqing.chen01@hand-china.com
 */
@Slf4j
public class DingTalkUtilTest {

    public final static String DINGTALK_GETTOKEN_SUCCESS_MEGGAGE = "DingTalk gettoken success: {}";
    public final static String DINGTALK_REQUEST_ERROR_MEGGAGE =
            "DingTalk request error, URL: {}, REQUEST: {}, RESPONSE: {}";
    public final static String DINGTALK_REQUEST_URL_LOG = "DingTalk client request url is: {}";
    public String DINGTALK_BASE_URL = "https://oapi.dingtalk.com";
    public String DINGTALK_GETTOKEN_URL = "/gettoken";
    public String DINGTALK_GET_USER_BY_MOBILE_URL = "/topapi/v2/user/getbymobile";
    public final static String DINGTALK_APPKEY_CHECK_MEGGAGE = "appKey is null";
    public final static String DINGTALK_APPSECRET_CHECK_MEGGAGE = "appSecret is null";
    public final static String DINGTALK_RESP_CHECK_MEGGAGE = "dingtalk resp is null";
    public final static String DINGTALK_TOKEN_CHECK_MEGGAGE = "dingtalk token is null";
    public final static String DINGTALK_MOBILE_MEGGAGE = "mobile is null";
    public final static String DINGTALK_MOBILE_LIST_MEGGAGE = "mobileList is null";
    public final static String DINGTALK_AUTHINFO_CHECK_MEGGAGE = "DingTalkAuthInfo resp is null";
    public final static String ZCXJ_DD_BASE_URL = "ZCXJ_DD_BASE_URL";
    public final static String ZCXJ_DD_GETTOKEN_URL = "ZCXJ_DD_GETTOKEN_URL";
    public final static String ZCXJ_DD_GETUSERBYMOBILE_URL = "ZCXJ_DD_GETUSERBYMOBILE_URL";
    public final static String APP_KEY = "app_key";
    public final static String APP_SECRET = "app_secret";

    /**
     * 获取钉钉的access_token
     * @param appKey
     * @param appSecret
     * @return access_token
     * @throws Exception
     */
    public String getAccessToken(String appKey, String appSecret) throws Exception {

        DingTalkClient client = new DefaultDingTalkClient(DINGTALK_BASE_URL+DINGTALK_GETTOKEN_URL);
        log.debug(DINGTALK_REQUEST_URL_LOG, DINGTALK_BASE_URL+DINGTALK_GETTOKEN_URL);
        OapiGettokenRequest req = new OapiGettokenRequest();
        req.setAppkey(appKey);
        req.setAppsecret(appSecret);
        req.setHttpMethod("GET");
        OapiGettokenResponse rsp = client.execute(req);
        if (0L != rsp.getErrcode()) {
            log.error(DINGTALK_REQUEST_ERROR_MEGGAGE, DINGTALK_BASE_URL+DINGTALK_GETTOKEN_URL,
                    JSON.toJSONString(req), JSON.toJSONString(rsp));
            throw new RuntimeException(rsp.getErrmsg());
        }
        String accessToken = rsp.getAccessToken();
        log.debug(DINGTALK_GETTOKEN_SUCCESS_MEGGAGE, accessToken);
        return accessToken;
    }

    /**
     * 获取钉钉的access_token，从数据库查询appKey&appSecret
     * @return
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        String appKey = "dingphkm5ogwjtlc6oxr";
        String appSecret = "3eYDCNQJmzo0fEiAovGAr4v13DKGmQ21obLWU1YFSkZeBB1XJpcz_O-VQliJCQMl";
        return this.getAccessToken(appKey, appSecret);
    }

    /**
     * 根据手机号码获取用户ID
     * @param mobileList
     * @return
     * @throws Exception
     */
    public Map<String, String> getUserIdByMobile(List<String> mobileList) throws Exception {
        // 去重
        mobileList = mobileList.stream().distinct().collect(Collectors.toList());
        String accessToken = this.getAccessToken();
        Map<String, String> mobileAndUserMap = new HashMap<>(1);
        for (String mobile : mobileList) {
            String userId = this.getUserIdByMobile(mobile, accessToken);
            mobileAndUserMap.put(mobile, userId);
        }
        return mobileAndUserMap;
    }

    /**
     * 根据手机号码获取用户ID
     * @param mobile
     * @param accessToken
     * @return
     * @throws Exception
     */
    public String getUserIdByMobile(String mobile, String accessToken) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient(DINGTALK_BASE_URL+DINGTALK_GET_USER_BY_MOBILE_URL);
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(mobile);
        OapiV2UserGetbymobileResponse rsp = client.execute(req, accessToken);
        if (0L != rsp.getErrcode()) {
            log.error(DINGTALK_REQUEST_ERROR_MEGGAGE, DINGTALK_BASE_URL+DINGTALK_GET_USER_BY_MOBILE_URL,
                    JSON.toJSONString(req), JSON.toJSONString(rsp));
            throw new RuntimeException(rsp.getErrmsg());
        }
        OapiV2UserGetbymobileResponse.UserGetByMobileResponse userGetByMobileResponse = rsp.getResult();
        if (Objects.isNull(userGetByMobileResponse)) return null;
        return userGetByMobileResponse.getUserid();
    }

    public static void main(String[] args) {
        try {
            DingTalkUtilTest dingTalkUtil = new DingTalkUtilTest();
            List<String> mobileList = new ArrayList();
            mobileList.add("13333596220");
            Map<String, String> userIdByMobile = dingTalkUtil.getUserIdByMobile(mobileList);
            System.out.println(JSON.toJSONString(userIdByMobile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
