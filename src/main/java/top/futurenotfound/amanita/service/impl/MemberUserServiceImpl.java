package top.futurenotfound.amanita.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.futurenotfound.amanita.domain.MemberUser;
import top.futurenotfound.amanita.dto.MemberUserDTO;
import top.futurenotfound.amanita.mapper.MemberUserMapper;
import top.futurenotfound.amanita.service.MemberUserService;

import java.util.Date;

/**
 * 用户
 *
 * @author DK
 */
@Service
@AllArgsConstructor
public class MemberUserServiceImpl implements MemberUserService {
    private final MemberUserMapper memberUserMapper;


    /**
     * 获取会员信息，是否会员，会员有效时间等
     *
     * @param userId
     * @return
     */
    @Override
    public MemberUserDTO getMemberInfo(String userId) {

        MemberUser memberUser = memberUserMapper.selectOne(new LambdaQueryWrapper<MemberUser>().eq(StrUtil.isNotBlank(userId),MemberUser::getUserId,userId));

        if(ObjectUtil.isNull(memberUser)){
            return null;
        }
        MemberUserDTO dto = new MemberUserDTO();
        dto.setExpireTime(memberUser.getExpireTime());
        dto.setStatus(memberUser.getStatus());
        dto.setUserId(userId);
        dto.setUserName(memberUser.getUserName());
        return dto;
    }

    @Override
    public MemberUser getById(String id) {
        return memberUserMapper.selectById(id);
    }

    @Override
    public Page<MemberUser> findPage(MemberUserDTO memberUserDTO, Page<MemberUser> page) {
        LambdaQueryWrapper<MemberUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderByDesc(MemberUser::getUpdateTime)
                .eq(StrUtil.isNotBlank(memberUserDTO.getUserId()),MemberUser::getUserId,memberUserDTO.getUserId())
                .eq(StrUtil.isNotBlank(memberUserDTO.getUserName()),MemberUser::getUserName,memberUserDTO.getUserName());
        return memberUserMapper.selectPage(page,queryWrapper);
    }

    @Override
    public void deleteById(String id) {
        memberUserMapper.deleteById(id);
    }

    @Override
    public void save(MemberUserDTO memberUserDTO) {
        MemberUser memberUser = new MemberUser();
        memberUser.setCreateTime(new Date());
        memberUser.setUpdateTime(new Date());
        memberUser.setStatus(1);
        memberUser.setUserName(memberUserDTO.getUserName());
        memberUser.setExpireTime(memberUserDTO.getExpireTime());
        memberUser.setUserId(memberUserDTO.getUserId());
        memberUserMapper.insert(memberUser);
    }

    @Override
    public void modify(MemberUserDTO memberUserDTO) {
        MemberUser memberUser = new MemberUser();
        memberUser.setId(memberUserDTO.getId());
        memberUser.setUpdateTime(new Date());
        memberUser.setStatus(memberUserDTO.getStatus());
        memberUser.setUserName(memberUserDTO.getUserName());
        memberUser.setExpireTime(memberUserDTO.getExpireTime());
        memberUser.setUserId(memberUserDTO.getUserId());
        memberUserMapper.updateById(memberUser);
    }
}




