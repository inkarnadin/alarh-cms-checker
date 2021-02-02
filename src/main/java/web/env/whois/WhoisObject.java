package web.env.whois;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties
public class WhoisObject implements Serializable {

    private String status;
    private String country;
    private String countryCode;
    private String region;
    private String regionName;
    private String city;
    private String zip;
    private Double lat;
    private Double lon;
    private String timezone;
    private String isp;
    private String org;
    private String as;
    private String query;
    private String message;

    @SneakyThrows
    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n", "   [\n", "\n   ]");
        Field[] fields = WhoisObject.class.getDeclaredFields();

        for (Field field : fields) {
            Object value = field.get(this);
            if (Objects.nonNull(value) && !"".equals(value.toString()))
                stringJoiner.add(String.format("    %s = %s", field.getName(), value.toString()));
        }
        return stringJoiner.toString();
    }

}
