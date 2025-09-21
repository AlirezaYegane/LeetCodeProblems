package algorithm.containerWithMostWater;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** Facade service around AreaStrategy for easy use / testing. */
public final class ContainerService {

    private AreaStrategy strategy;

    public ContainerService(AreaStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(AreaStrategy strategy) {
        this.strategy = strategy;
    }

    public int maxArea(List<Integer> heights) {
        return strategy.maxArea(heights);
    }

    public static List<Integer> parseHeights(String csv) {
        if (csv == null || csv.trim().isEmpty()) return List.of();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}