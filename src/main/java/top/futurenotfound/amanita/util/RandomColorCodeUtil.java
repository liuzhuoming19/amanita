package top.futurenotfound.amanita.util;

import cn.hutool.core.util.RandomUtil;
import top.futurenotfound.amanita.env.Constant;

/**
 * 随机颜色代码工具
 *
 * @author liuzhuoming
 */
public class RandomColorCodeUtil {

    private RandomColorCodeUtil() {
    }

    /**
     * 随机获取颜色代码
     *
     * @return 颜色代码
     */
    public static String next() {
        int index = RandomUtil.randomInt(Constant.COLOR_CODES.size());
        return Constant.COLOR_CODES.get(index);
    }
}
