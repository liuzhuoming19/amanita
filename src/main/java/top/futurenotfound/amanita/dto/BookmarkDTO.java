package top.futurenotfound.amanita.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 书签入参
 *
 * @author liuzhuoming
 */
@Data
@ApiModel(value = "BookmarkDTO", description = "书签入参")
public class BookmarkDTO implements Serializable {
    private static final long serialVersionUID = -1723569070857437946L;
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("书签地址")
    @NotEmpty
    private String url;
    @ApiModelProperty("书签标题")
    private String title;
    @ApiModelProperty("书签笔记")
    private String note;
    @ApiModelProperty("书签标签组")
    private List<String> tagNames;
    @ApiModelProperty("是否收藏 0/null/其他-否 1-是")
    private Integer isStarred;
}
