package ga.tianyuge.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/23 10:51
 * @Email: guoqing.chen01@hand-china.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Word2PdfDTO {
    /**
     * 请求内容
     */
    private boolean async;
    private String filetype;
    private String key;
    @JsonProperty("outputtype")
    @JSONField(name = "outputtype")
    private String outPutType;
    private String title;
    private String url;
    /**
     * 响应内容
     */
    private String fileUrl;
    private String fileType;
    private Long percent;
    private boolean endConvert;
    private boolean error;

    {
        this.async = false;
        this.filetype = "docx";
        this.outPutType = "pdf";
    }
}
