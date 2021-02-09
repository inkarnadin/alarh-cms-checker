package web.cms.joomla;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JoomlaModule {

    MOD_ICEMEGAMENU                ("mod_icemegamenu"),
    MOD_JOOMSPIRIT_SLIDER          ("mod_joomspirit_slider"),
    ;

    private final String path;

}
