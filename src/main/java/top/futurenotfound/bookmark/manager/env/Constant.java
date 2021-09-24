package top.futurenotfound.bookmark.manager.env;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 常量
 *
 * @author liuzhuoming
 */
public interface Constant {

    String JWT_ROLE = "role";
    String JWT_USERNAME = "username";

    String HTTPS = "https://";
    String HTTP = "http://";

    String DESENSITIZED_PASSWORD = "******";

    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_SOURCE = "Source";
    String HEADER_ACCESS_KEY = "AccessKey";
    String HEADER_ACCESS_SECRET = "AccessSecret";

    Integer DATABASE_TRUE = 1;
    Integer DATABASE_FALSE = 0;

    List<String> COLOR_CODES = new ArrayList<>(
            Set.of(
                    //TODO 待补充颜色代码
                    "#0aa344",
                    "#70f3ff",
                    "#ffa631",
                    "#9d2933",
                    "#eacd76",
                    "#549688",
                    "#725e82",
                    "#395260"
            ));

}
