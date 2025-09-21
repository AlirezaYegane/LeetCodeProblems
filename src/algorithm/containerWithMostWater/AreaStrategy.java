package algorithm.containerWithMostWater;

import java.util.List;

/** Strategy interface for computing max water area between lines. */
public interface AreaStrategy {
    int maxArea(List<Integer> height);
    String name();
}
