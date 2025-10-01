package algorithm.threeSumClosest;

/**
 * Interface for solving the 3Sum Closest problem.
 */
public interface IThreeSumClosestSolver {
    /**
     * Finds the sum of three integers in nums closest to target.
     *
     * @param nums   array of integers
     * @param target target integer
     * @return closest sum
     */
    int threeSumClosest(int[] nums, int target);
}