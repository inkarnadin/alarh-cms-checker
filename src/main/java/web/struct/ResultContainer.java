package web.struct;

import web.analyzer.Importance;

import java.util.List;

public interface ResultContainer {

    void setImportance(Importance importance);

    Importance getImportance();

    void add(Integer index, String row);

    List<String> fetch();

    boolean isSuccess();

}