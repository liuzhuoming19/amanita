package top.futurenotfound.amanita.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import top.futurenotfound.amanita.env.Constant;

import java.io.IOException;

/**
 * http工具
 *
 * @author liuzhuoming
 */
@Slf4j
public class HttpUtil {
    private HttpUtil() {
    }

    /**
     * 是否正确的url（正确是指以 https:// 或者 http:// 开头）
     *
     * @param url url
     * @return true正确 false错误
     */
    public static boolean isCorrectUrl(String url) {
        return url.startsWith(Constant.HTTPS) || url.startsWith(Constant.HTTP);
    }

    /**
     * url是否可访问
     *
     * @param url url
     * @return true可访问 false不可访问
     */
    public static boolean isReachable(String url) {
        return StringUtil.isNotEmpty(get(url));
    }

    public static String get(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.string();
            }
        } catch (IOException e) {
            log.error("{}", e);
        }
        return null;
    }
}
