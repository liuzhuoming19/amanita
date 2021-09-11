package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.domain.Vip;
import top.futurenotfound.bookmark.manager.mapper.VipMapper;
import top.futurenotfound.bookmark.manager.service.VipService;

import java.util.List;

/**
 * 会员
 *
 * @author liuzhuoming
 */
@Service
public class VipServiceImpl extends ServiceImpl<VipMapper, Vip>
        implements VipService {

    @Override
    public List<Vip> listByUserId(String userId) {
        LambdaQueryWrapper<Vip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Vip::getUserId, userId);
        return this.baseMapper.selectList(queryWrapper);
    }
}




