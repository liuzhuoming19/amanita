package top.futurenotfound.amanita.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.futurenotfound.amanita.domain.MemberUser;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.dto.MemberUserDTO;
import top.futurenotfound.amanita.dto.UserPasswordDTO;

import java.util.Date;

/**
 * 会员用户
 *
 * @author DK
 */
public interface MemberUserService {

    /**
     * 获取会员信息，是否会员，会员有效时间等
     * @param userId
     * @return
     */
     MemberUserDTO getMemberInfo(String userId);

    MemberUser getById(String id);


    Page<MemberUser> findPage(MemberUserDTO memberUserDTO, Page<MemberUser> objectPage);

    void deleteById(String id);

    void save(MemberUserDTO memberUserDTO);

    void modify(MemberUserDTO memberUserDTO);
}
