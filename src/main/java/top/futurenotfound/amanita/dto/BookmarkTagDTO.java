package top.futurenotfound.amanita.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 书签标签
 *
 * @author liuzhuoming
 */
@ApiModel(value = "BookmarkTagDTO", description = "书签标签入参")
@Data
public class BookmarkTagDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3618862567286379862L;
    /**
     * 书签id
     */
    @ApiModelProperty("书签id")
    private String bookmarkId;
    @ApiModelProperty("标签名称")
    private String tagName;
}