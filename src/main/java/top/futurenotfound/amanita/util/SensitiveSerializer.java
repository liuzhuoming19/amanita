package top.futurenotfound.amanita.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.springframework.util.StringUtils;
import top.futurenotfound.amanita.annotation.Sensitive;
import top.futurenotfound.amanita.env.SensitiveType;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

/**
 * 敏感字段序列化
 *
 * @author liuzhuoming
 */
public class SensitiveSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏策略
     */
    private static final Map<SensitiveType, Function<String, String>> SENSITIVE_STRATEGY = Map.of(
            SensitiveType.FULL, val -> "******",
            SensitiveType.PHONE_NUM, val -> val.substring(0, 3) + "****" + val.substring(7)
    );

    private SensitiveType sensitiveType;

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        Sensitive sensitive = property.getAnnotation(Sensitive.class);
        if (sensitive != null) {
            sensitiveType = sensitive.type();
        }
        return this;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (StringUtils.hasText(value)) {
            value = SENSITIVE_STRATEGY.get(sensitiveType).apply(value);
        }
        jsonGenerator.writeString(value);
    }
}

