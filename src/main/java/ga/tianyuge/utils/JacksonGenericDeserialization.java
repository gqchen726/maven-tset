package org.srm.purchasecooperation.cux.infra.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonGenericDeserialization {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public final static String DESERIALIZE_ERROR = "反序列化失败: error: %s, data: %s";

    /**
     * 使用 TypeReference 处理泛型集合等
     * @param json 需要反序列化的json字符串
     * @param typeReference 泛型
     * @return typeReference 泛型
     * @param <T> typeReference 泛型
     * @throws IOException 反序列化过程中的异常
     */
    public static  <T> T deserializeList(String json, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(json, typeReference);
    }
}