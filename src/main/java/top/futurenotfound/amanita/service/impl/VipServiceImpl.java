package top.futurenotfound.amanita.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.amanita.mapper.VipMapper;
import top.futurenotfound.amanita.service.VipService;
import top.futurenotfound.amanita.domain.Vip;

import java.util.List;

/**
 * 会员
 *
 * @author liuzhuoming
 */
@Service
@AllArgsConstructor
public class VipServiceImpl implements VipService {
    private final VipMapper vipMapper;

    @Override
    public List<Vip> listByUserId(String userId) {
        LambdaQueryWrapper<Vip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Vip::getUserId, userId);
        return vipMapper.selectList(queryWrapper);
    }
}




