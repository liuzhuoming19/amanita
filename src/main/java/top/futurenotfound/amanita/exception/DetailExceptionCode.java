package top.futurenotfound.amanita.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.futurenotfound.amanita.util.JsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述详情的异常错误码
 *
 * @author liuzhuoming
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailExceptionCode implements ExceptionCode {
    private String code;
    private String msg;

    @Override
    public String toString() {
        Map<String, String> r = new LinkedHashMap<>(2);
        r.put("code", code);
        r.put("msg", msg);
        return JsonUtil.toJson(r);
    }
}
