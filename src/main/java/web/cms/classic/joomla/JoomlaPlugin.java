package web.cms.classic.joomla;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JoomlaPlugin {

    COM_SIGE                        (JoomlaPluginCategoryConst.PHOTO,                 "com_sige"),
    COM_JCE                         (JoomlaPluginCategoryConst.EDITING,               "com_jce"),
    COM_UNITEREVOLUTION_2           (JoomlaPluginCategoryConst.UNDEF,                 "com_uniterevolution2"),
    ;

    private final String category;
    private final String path;

}
