package web.cms.joomla;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static web.cms.joomla.JoomlaPluginCategoryConst.*;

@Getter
@RequiredArgsConstructor
public enum JoomlaPlugin {

    COM_SIGE                        (PHOTO,                 "com_sige"),
    COM_JCE                         (EDITING,               "com_jce"),
    COM_UNITEREVOLUTION_2           (UNDEF,                 "com_uniterevolution2"),
    ;

    private final String category;
    private final String path;

}
