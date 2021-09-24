package top.futurenotfound.amanita.service;

import top.futurenotfound.amanita.domain.Vip;

import java.util.List;

/**
 * 会员
 *
 * @author liuzhuoming
 */
public interface VipService {

    List<Vip> listByUserId(String userId);
}
