package web.env.whois;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties
public class WhoisDto implements Serializable {

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

}
