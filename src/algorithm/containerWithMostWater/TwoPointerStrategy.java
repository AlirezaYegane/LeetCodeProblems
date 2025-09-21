package algorithm.containerWithMostWater;


import java.util.List;

/** Optimal O(n) / O(1) strategy using two pointers. */
public final class TwoPointerStrategy implements AreaStrategy {

    @Override
    public int maxArea(List<Integer> height) {
        if (height == null || height.size() < 2) return 0;
        int l = 0, r = height.size() - 1;
        int best = 0;
        while (l < r) {
            int hl = height.get(l), hr = height.get(r);
            int h = Math.min(hl, hr);
            best = Math.max(best, h * (r - l));
            if (hl < hr) l++; else r--;
        }
        return best;
    }

    @Override
    public String name() { return "two-pointers"; }
}