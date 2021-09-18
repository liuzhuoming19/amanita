package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.futurenotfound.bookmark.manager.domain.Access;

/**
 * 认证
 *
 * @author liuzhuoming
 */
public interface AccessService {

    /**
     * 生成access
     *
     * @param userId 用户id
     */
    Access generateAccess(String userId);

    /**
     * 重生成access
     * <p>
     * access key不变，重新生成access secret
     *
     * @param id access id
     */
    Access regenerateAccess(String id);

    Access getByKeyAndSecret(String key, String secret);

    /**
     * 获取脱敏access
     *
     * @param id access id
     */
    Access getDesensitizedById(String id);

    /**
     * 获取脱敏access列表
     *
     * @param userId 用户id
     */
    Page<Access> pageDesensitizedByUserId(String userId, Page<Access> page);

    void deleteById(String id);
}
