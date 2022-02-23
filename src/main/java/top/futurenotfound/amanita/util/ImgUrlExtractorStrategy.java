package top.futurenotfound.amanita.util;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import lombok.SneakyThrows;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import top.futurenotfound.amanita.env.Constant;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 图片地址获取策略
 * <p>
 * 处理一些特殊情况：
 * 比如zhihu.com
 *
 * @author liuzhuoming
 */
public class ImgUrlExtractorStrategy {

    /**
     * 拉取图片策略
     * <p>
     * 新增策略需添加在此
     */
    private static final Map<String, Function<URL, String>> IMG_STRATEGY = new ConcurrentHashMap<>() {
        private static final long serialVersionUID = -5272813377667100165L;

        {
            //通用策略
            put("", ImgUrlExtractorStrategy::common);
            put("www.zhihu.com", ImgUrlExtractorStrategy::wwwZhihuCom);
            put("zhuanlan.zhihu.com", ImgUrlExtractorStrategy::zhuanlanZhihuCom);
        }
    };

    /**
     * 拉取页面首图（特殊页面需做特殊处理
     *
     * @param uRL 书签url
     * @return 首图url
     */
    public static String apply(URL uRL) {
        Function<URL, String> func = IMG_STRATEGY.get(uRL.getHost());
        return Optional.ofNullable(func)
                .orElse(IMG_STRATEGY.get(""))
                .apply(uRL);
    }

    /**
     * 通用首图拉取
     *
     * @param uRL 书签url
     * @return 首图url
     */
    @SneakyThrows
    private static String common(URL uRL) {
        String imgUrl = null;
        Element element = ContentExtractor.getContentElementByUrl(uRL.toString());
        Elements elements = element.getElementsByTag("img");
        if (!elements.isEmpty()) {
            imgUrl = elements.get(0).attr("src");
            if (!imgUrl.startsWith(Constant.HTTPS) && !imgUrl.startsWith(Constant.HTTP)) {
                imgUrl = null;
            }
        }
        return imgUrl;
    }

    @SneakyThrows
    private static String wwwZhihuCom(URL uRL) {
        String imgUrl = null;
        Element element = ContentExtractor.getContentElementByUrl(uRL.toString());
        Elements elements = element.getElementsByTag("img");
        if (!elements.isEmpty()) {
            imgUrl = elements.get(0).attr("data-actualsrc");
            if (!imgUrl.startsWith(Constant.HTTPS) && !imgUrl.startsWith(Constant.HTTP)) {
                imgUrl = null;
            }
        }
        return imgUrl;
    }

    @SneakyThrows
    private static String zhuanlanZhihuCom(URL uRL) {
        String imgUrl = null;
        Element element = ContentExtractor.getContentElementByUrl(uRL.toString());
        Elements elements = element.getElementsByTag("img");
        if (!elements.isEmpty()) {
            imgUrl = elements.get(0).attr("data-actualsrc");
            if (!imgUrl.startsWith(Constant.HTTPS) && !imgUrl.startsWith(Constant.HTTP)) {
                imgUrl = null;
            }
        }
        return imgUrl;
    }
}
