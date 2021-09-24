package top.futurenotfound.amanita.service;

import top.futurenotfound.amanita.domain.Tag;

import java.util.List;

/**
 * 标签
 *
 * @author liuzhuoming
 */
public interface TagService {

    List<Tag> listByBookmarkId(String bookmarkId);

    /**
     * 通过标签名称列表构建标签列表
     *
     * @param tagNames 标签名称列表
     */
    List<Tag> mkTags(List<String> tagNames);

    /**
     * 根据标签名获取标签详情
     * <p>
     * 如果不存在则生成并插入数据库后返回
     *
     * @param name 标签名
     * @return 标签
     */
    Tag getByName(String name);
}
