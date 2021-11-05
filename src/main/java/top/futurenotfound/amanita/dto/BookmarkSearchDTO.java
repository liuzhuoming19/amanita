package top.futurenotfound.amanita.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import top.futurenotfound.amanita.env.BookmarkSearchKeywordType;
import top.futurenotfound.amanita.env.BookmarkSearchType;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 书签搜索入参
 *
 * @author liuzhuoming
 */
@Data
@ApiModel(value = "BookmarkSearchDTO", description = "书签搜索入参")
public class BookmarkSearchDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1318589269631532670L;
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
    @ApiModelProperty(value = "搜索类型 0普通1加星2已删除3已有笔记", example = "0")
    @NotNull
    @Range(min = 0, max = 3)
    private Integer searchType;
}
