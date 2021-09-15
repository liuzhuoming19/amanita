package top.futurenotfound.bookmark.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 书签入参
 *
 * @author liuzhuoming
 */
@Data
@ApiModel(value = "BookmarkDTO", description = "书签入参")
public class BookmarkDTO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("书签地址")
    private String url;
    @ApiModelProperty("书签标题")
    private String title;
    @ApiModelProperty("书签笔记")
    private String note;
    @ApiModelProperty("书签标签组")
    private List<String> tagNames;
}