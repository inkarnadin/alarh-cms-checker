package web.cms;

import com.google.inject.Inject;
import web.module.annotation.Cms;
import web.struct.AbstractChecker;
import web.struct.Connector;
import web.struct.Determinant;
import web.struct.Params;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CMSChecker extends AbstractChecker {

    private final Determinant determinant;

    private final List<CMSType> types = new ArrayList<>();

    @Inject
    CMSChecker(@Cms Determinant determinant) {
        this.determinant = determinant;
    }

    @Override
    public void check(Params params) {
        types.addAll(
                Objects.isNull(params.getCmsType()) || "".equals(params.getCmsType())
                    ? determinant.define(params)
                    : Collections.singletonList(CMSType.search(params.getCmsType()))
        );

        for (CMSType type : types) {
            Connector connector = CMSFactory.getCMSConnector(type);
            connector.configure(params);
            connector.checkPlugins();
        }
    }

}
