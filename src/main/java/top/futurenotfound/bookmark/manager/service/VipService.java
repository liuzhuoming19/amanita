package top.futurenotfound.bookmark.manager.service;

import top.futurenotfound.bookmark.manager.domain.Vip;

import java.util.List;

/**
 * 会员
 *
 * @author liuzhuoming
 */
public interface VipService {

    List<Vip> listByUserId(String userId);
}
