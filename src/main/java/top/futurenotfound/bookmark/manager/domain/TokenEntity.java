package top.futurenotfound.bookmark.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * token返回值
 *
 * @author liuzhuoming
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {
    private String token;
    private Date createTime;
    private Date expireTime;
}
