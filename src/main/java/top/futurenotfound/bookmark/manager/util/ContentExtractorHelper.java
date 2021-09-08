package top.futurenotfound.bookmark.manager.util;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import top.futurenotfound.bookmark.manager.entity.WebExcerptInfo;
import top.futurenotfound.bookmark.manager.entity.WebHtmlInfo;
import top.futurenotfound.bookmark.manager.env.Constant;

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
     * @param origUrl 原始url
     */
    public static WebExcerptInfo excerpt(String origUrl) {
        String url;
        URL uRL;

        if (!origUrl.startsWith(Constant.HTTPS) && !origUrl.startsWith(Constant.HTTP)) {
            url = Constant.HTTPS + origUrl;
        } else {
            url = origUrl;
        }

        try {
            uRL = new URL(url);
        } catch (MalformedURLException e) {
            //构建URl失败
            return new WebExcerptInfo(origUrl, null, origUrl, null, null);
        }

        try {
            String title = null;
            String excerpt = null;
            String imageUrl = null;

            try {
                News news = ContentExtractor.getNewsByUrl(url);
                title = news.getTitle();
                String content = news.getContent();
                if (!StringUtils.isEmpty(content)) {
                    excerpt = content.substring(0, 197) + "...";
                }
            } catch (Exception ignored) {
            }
            try {
                Element element = ContentExtractor.getContentElementByUrl(url);
                Elements elements = element.getElementsByTag("img");
                if (!elements.isEmpty()) {
                    imageUrl = elements.get(0).attr("src");
                    if (!imageUrl.startsWith(Constant.HTTPS) && !imageUrl.startsWith(Constant.HTTP)) {
                        imageUrl = null;
                    }
                }
            } catch (Exception ignored) {
            }
            return new WebExcerptInfo(origUrl, uRL.getHost(), title, excerpt, imageUrl);
        } catch (Exception e) {
            log.error("{}", e);
            //抽取正文失败
            return new WebExcerptInfo(origUrl, uRL.getHost(), uRL.getHost(), null, null);
        }
    }

    /**
     * 网页html代码抽取
     *
     * @param origUrl 原始url
     */
    public static WebHtmlInfo html(String origUrl) {
        String url;
        URL uRL;

        if (!origUrl.startsWith(Constant.HTTPS) && !origUrl.startsWith(Constant.HTTP)) {
            url = Constant.HTTPS + origUrl;
        } else {
            url = origUrl;
        }

        try {
            uRL = new URL(url);
        } catch (MalformedURLException e) {
            //构建URl失败
            return new WebHtmlInfo(origUrl, null, origUrl, null);
        }

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(uRL).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() == null) {
                return new WebHtmlInfo(origUrl, uRL.getHost(), origUrl, null);
            }
            String html = response.body().string();
            return new WebHtmlInfo(origUrl, uRL.getHost(), origUrl, html);
        } catch (IOException e) {
            log.error("{}", e);
            //抽取html失败
            return new WebHtmlInfo(origUrl, uRL.getHost(), origUrl, null);
        }
    }

    public static void main(String[] args) {
        log.info(excerpt("https://zhuanlan.zhihu.com/p/341867459").toString());
    }
}
