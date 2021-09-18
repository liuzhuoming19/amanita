package top.futurenotfound.bookmark.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.futurenotfound.bookmark.manager.config.CustomProperties;
import top.futurenotfound.bookmark.manager.domain.Access;
import top.futurenotfound.bookmark.manager.exception.AuthException;
import top.futurenotfound.bookmark.manager.exception.ExceptionCode;
import top.futurenotfound.bookmark.manager.helper.RandomStringUtil;
import top.futurenotfound.bookmark.manager.mapper.AccessMapper;
import top.futurenotfound.bookmark.manager.service.AccessService;
import top.futurenotfound.bookmark.manager.util.DateUtil;

import java.time.temporal.ChronoUnit;

/**
 * 认证
 *
 * @author liuzhuoming
 */
@AllArgsConstructor
@Service
@Slf4j
public class AccessServiceImpl implements AccessService {
    private final AccessMapper accessMapper;
    private final CustomProperties customProperties;

    @Override
    public Access generateAccess(String userId) {
        Access access = new Access();
        String accessKey = RandomStringUtil.generateRandomString(customProperties.getAccessKeyLength());
        String accessSecret = RandomStringUtil.generateRandomString(customProperties.getAccessSecretLength());
        access.setKey(accessKey);
        access.setSecret(accessSecret);
        access.setExpireTime(DateUtil.add(DateUtil.now(), ChronoUnit.DAYS, customProperties.getAccessExpireDays()));
        access.setUserId(userId);
        accessMapper.insert(access);
        return access;
    }

    @Override
    public Access regenerateAccess(String id) {
        Access access = accessMapper.selectById(id);
        if (access == null) throw new AuthException(ExceptionCode.ACCESS_EXPIRED);
        String accessSecret = RandomStringUtil.generateRandomString(customProperties.getAccessSecretLength());
        access.setSecret(accessSecret);
        access.setUpdateTime(DateUtil.now());
        accessMapper.updateById(access);
        return access;
    }

    @Override
    public Access getByKeyAndSecret(String key, String secret) {
        LambdaQueryWrapper<Access> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Access::getKey, key)
                .eq(Access::getSecret, secret)
                //筛选未过期
                .apply("to_date({0},'yyyy-mm-dd hh24:mi:ss') <= expire_time", DateUtil.formatNow(DateUtil.DATETIME_FORMATTER));
        return accessMapper.selectOne(queryWrapper);
    }

    @Override
    public Access getDesensitizedById(String id) {
        Access access = accessMapper.selectById(id);
        access.setSecret("******");
        return access;
    }

    @Override
    public Page<Access> pageDesensitizedByUserId(String userId, Page<Access> page) {
        LambdaQueryWrapper<Access> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Access::getUserId, userId);
        Page<Access> accesses = accessMapper.selectPage(page, queryWrapper);
        for (Access access : accesses.getRecords()) access.setSecret("******");
        return accesses;
    }

    @Override
    public void deleteById(String id) {
        accessMapper.deleteById(id);
    }
}
