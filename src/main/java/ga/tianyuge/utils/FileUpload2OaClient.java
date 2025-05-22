package org.srm.purchasecooperation.cux.infra.util;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

//@Component
//@Slf4j
public class FileUpload2OaClient {

    private final static String ATTACHMENT_FILE = "attachmentFile: {}";
    private final static String BUCKET_DEFAULT = "private-bucket";
    private final static String FORM_DATA_KEY = "file";
    private final static String UPLOAD_FILE_URL = "uploadFile-url: {}";
    private final static String UPLOAD_FILE_HEADERS = "uploadFile-headers: {}";
    private final static String HTTPCLIENT_CLOSE_ERROR = "httpClient close error: %s";
    private final static String UPLOAD_FILE_ERROR = "upload file error: %s";
    private final static String ATTACHMENT_FILES_IS_NULL = "attachmentFiles";
    private final static String OA_UPLOAD_ATTACHMENT_URL_PROFILE_NAME = "OA_UPLOAD_ATTACHMENT_URL";
    private final static String OA_UPLOAD_ATTACHMENT_URL_DEFAULT = "http://36.7.170.35:8088/seeyon/rest/attachment";
    private final static String OA_UPLOAD_FILE_ERROR = "上传文件到OA失败，请联系管理员";

    public String uploadFileWithInputStreamMap(Map<String, List<InputStream>> inputStreamMap, Map<String, String> requestHeaderMap, String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for (Map.Entry<String, List<InputStream>> entry : inputStreamMap.entrySet()) {
                builder.setCharset(StandardCharsets.UTF_8);
                // 如果你需要完全符合 MIME 标准，请使用 STRICT 模式。
                // 如果需要兼容旧浏览器，请使用 BROWSER_COMPATIBLE 模式。
                // 如果需要支持 UTF-8 编码的头部，请使用 RFC6532 模式。
                builder.setMode(HttpMultipartMode.RFC6532);
                for (InputStream inputStream : entry.getValue()) {
                    builder.addBinaryBody(FORM_DATA_KEY, inputStream, ContentType.MULTIPART_FORM_DATA, entry.getKey());
                }
            }

            HttpEntity multipartEntity = builder.build();
            httpPost.setEntity(multipartEntity);
            if (!CollectionUtils.isEmpty(requestHeaderMap)) {
                requestHeaderMap.forEach(httpPost::setHeader);
            }

            // 移除对 httpPost 对象的 JSON 序列化
            log.debug(UPLOAD_FILE_URL, url);
            if (!CollectionUtils.isEmpty(requestHeaderMap)) {
                log.debug(UPLOAD_FILE_HEADERS, JSON.toJSONString(requestHeaderMap));
            }

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity);
            }

            // 关闭entity
            EntityUtils.consume(multipartEntity);
        } catch (IOException e) {
            log.debug(String.format(UPLOAD_FILE_ERROR, e.getMessage()));
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.debug(String.format(HTTPCLIENT_CLOSE_ERROR, e.getMessage()));
                e.printStackTrace();
            }
        }
        return null;
    }
}