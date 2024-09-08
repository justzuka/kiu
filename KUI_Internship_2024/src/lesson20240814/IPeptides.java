package lesson20240814;

import java.util.List;
import java.util.Map;

public interface IPeptides<T> {
    public Map<T, List<Integer>> searchLibrary();
}
