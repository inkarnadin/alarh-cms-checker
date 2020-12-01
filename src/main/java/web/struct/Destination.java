package web.struct;

import web.analyzer.Importance;

import java.util.List;

public interface Destination {

    void setImportance(Importance importance);
    Importance getImportance();

    void insert(Integer index, String row);
    List<String> fetch();
    Boolean isFull();

}