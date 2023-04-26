package web.analyzer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class DissectorResult {

    private final String[] paths;
    private final boolean isOverWrittenBasePath;

}