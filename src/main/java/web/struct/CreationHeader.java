package web.struct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CreationHeader {

    X_GENERATOR("x-generator"),
    X_POWERED_BY("x-powered-by"),
    X_POWERED_CMS("x-powered-cms"),
    X_DEVSRV_CMS("x-devsrv-cms"),;

    private final String tag;

}
