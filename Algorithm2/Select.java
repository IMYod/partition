package Algorithm2;

import java.awt.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Select {
    public static <T> T selectFirst(Collection<T> collection) {
        return collection.iterator().next();
    }

    public static <T> T selectUniform(Collection<T> collection) {
        List<T> list = collection.stream().toList();
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public static <T> T rankingProportional(Collection<T> collection, Comparator<T> comparator) {
        int n = collection.size();
        int range = (n + 1) * n / 2;
        int randomInRange = ThreadLocalRandom.current().nextInt(range);
        int k = (int) (-1 + Math.sqrt(1 + 8*randomInRange)) / 2;
        return collection.stream().sorted(comparator).toList().get(k);
    }
}
