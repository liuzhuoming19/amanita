package top.futurenotfound.bookmark.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.futurenotfound.bookmark.manager.domain.Vip;

import java.util.List;

/**
 * 会员
 *
 * @author liuzhuoming
 */
public interface VipService extends IService<Vip> {

    List<Vip> listByUserId(String userId);
}
