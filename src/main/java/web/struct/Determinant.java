package web.struct;

import java.util.Map;

public interface Determinant<T, E> {

    Map<T, E> define(Params params);

}
