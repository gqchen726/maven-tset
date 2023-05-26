package ga.tianyuge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;

import java.util.Date;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/10 10:28
 * @Email: guoqing.chen01@hand-china.com
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDTO {
    private String fileUrl;
    private String fileType;
    private String fileName;
    private Long fileSize;
    private String bucketName;
    private Date creationDate;
    private Long createdBy;
    private String loginName;
    private String realName;
}
