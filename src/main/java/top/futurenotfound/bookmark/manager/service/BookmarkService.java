package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.Bookmark;

import java.util.List;

/**
 * 书签
 *
 * @author liuzhuoming
 */
public interface BookmarkService extends IService<Bookmark> {

    List<Bookmark> listByUserId(String userId);
}
