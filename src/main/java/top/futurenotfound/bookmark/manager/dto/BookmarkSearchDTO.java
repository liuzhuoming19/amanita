package top.futurenotfound.bookmark.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import top.futurenotfound.bookmark.manager.env.BookmarkSearchKeywordType;
import top.futurenotfound.bookmark.manager.env.BookmarkSearchType;

import javax.validation.constraints.NotNull;

/**
 * 书签搜索入参
 *
 * @author liuzhuoming
 */
@Data
@ApiModel(value = "BookmarkSearchDTO", description = "书签搜索入参")
public class BookmarkSearchDTO {
    /**
     * @see BookmarkSearchKeywordType
     */
    @ApiModelProperty("关键字类型 0书签名称1标签名称")
    @Range(min = 0, max = 1)
    private Integer keywordType;
    @ApiModelProperty("关键字")
    private String keyword;
    /**
     * @see BookmarkSearchType
     */
    @ApiModelProperty("搜索类型 0普通1加星2已删除")
    @NotNull
    @Range(min = 0, max = 2)
    private Integer searchType;
}
