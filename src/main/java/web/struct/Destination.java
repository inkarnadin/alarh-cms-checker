package web.struct;

import java.util.List;

public interface Destination {

    void insert(Integer index, String row);
    List<String> fetch();
    Boolean isFull();

}