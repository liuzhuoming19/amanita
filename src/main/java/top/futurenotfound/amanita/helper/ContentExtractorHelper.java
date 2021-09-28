package top.futurenotfound.amanita.helper;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import top.futurenotfound.amanita.domain.WebExcerptInfo;
import top.futurenotfound.amanita.domain.WebHtmlInfo;
import top.futurenotfound.amanita.util.ImgUrlExtractorStrategy;
import top.futurenotfound.amanita.util.StringUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网页抽取工具
 *
 * @author liuzhuoming
 */
@Component
@Slf4j
public class ContentExtractorHelper {

    /**
     * 网页正文摘要抽取
     *
     * @param url url
     */
    public static WebExcerptInfo excerpt(String url) {
        URL uRL;
        try {
            uRL = new URL(url);
        } catch (MalformedURLException e) {
            //构建URl失败
            return new WebExcerptInfo(url, null, url, null, null);
        }

        try {
            String title = null;
            String excerpt = null;
            String firstImageUrl = null;

            try {
                News news = ContentExtractor.getNewsByUrl(url);
                title = news.getTitle();
                String content = news.getContent();
                if (!StringUtil.isEmpty(content)) {
                    if (content.length() >= 200) {
                        excerpt = content.substring(0, 197) + "...";
                    } else {
                        excerpt = content;
                    }
                }
            } catch (Exception ignored) {
            }
            try {
                //拉取首图
                firstImageUrl = ImgUrlExtractorStrategy.apply(uRL);
            } catch (Exception ignored) {
            }
            return new WebExcerptInfo(url, uRL.getHost(), title, excerpt, firstImageUrl);
        } catch (Exception e) {
            log.error("{}", e);
            //抽取正文失败
            return new WebExcerptInfo(url, uRL.getHost(), uRL.getHost(), null, null);
        }
    }

    public static void main(String[] args) {
        excerpt("https://www.zhihu.com/question/19912421/answer/565144490?utm_medium=social&utm_oi=607907406420774912&utm_source=com.instapaper.android");
    }

    /**
     * 网页全文html代码抽取
     *
     * @param url url
     */
    public WebHtmlInfo html(String url) {
        URL uRL;
        try {
            uRL = new URL(url);
        } catch (MalformedURLException e) {
            //构建URl失败
            return new WebHtmlInfo(url, null, url, null);
        }

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(uRL).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() == null) {
                return new WebHtmlInfo(url, uRL.getHost(), url, null);
            }
            String html = response.body().string();
            return new WebHtmlInfo(url, uRL.getHost(), url, html);
        } catch (IOException e) {
            log.error("{}", e);
            //抽取html失败
            return new WebHtmlInfo(url, uRL.getHost(), url, null);
        }
    }
}
