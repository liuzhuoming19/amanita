package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.Tag;

import java.util.List;

/**
 * 标签
 *
 * @author liuzhuoming
 */
public interface TagService extends IService<Tag> {

    List<Tag> listByBookmarkId(String bookmarkId);
}
