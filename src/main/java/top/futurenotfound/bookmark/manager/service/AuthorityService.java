package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.Authority;

import java.io.Serializable;
import java.util.List;

/**
 * 权限
 */
public interface AuthorityService extends IService<Authority> {

    List<Authority> listByUserId(Serializable userId);
}
