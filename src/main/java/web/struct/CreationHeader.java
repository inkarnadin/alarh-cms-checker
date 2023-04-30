package web.struct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CreationHeader {

    X_GENERATOR("x-generator"),
    X_POWERED_BY("x-powered-by");

    private final String tag;

}
