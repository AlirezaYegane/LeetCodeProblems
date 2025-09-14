package algorithm.medianOfTwoSortedArrays;

public class Main {
    public static void main(String[] args) {
        MedianService optimal = new MedianService(new PartitionMedianStrategy());
        MedianService simple  = new MedianService(new MergeLinearMedianStrategy());

        int[] a1 = {1, 3};
        int[] b1 = {2};
        int[] a2 = {1, 2};
        int[] b2 = {3, 4};

        System.out.println("Optimal [1,3] & [2]  = " + optimal.compute(a1, b1)); // 2.0
        System.out.println("Simple  [1,3] & [2]  = " + simple.compute(a1, b1));  // 2.0
        System.out.println("Optimal [1,2] & [3,4]= " + optimal.compute(a2, b2)); // 2.5

        // تست‌های سریع دیگر
        System.out.println("[] & [1] = " + optimal.compute(new int[]{}, new int[]{1}));
        System.out.println("[0,0] & [0,0] = " + optimal.compute(new int[]{0,0}, new int[]{0,0}));
        System.out.println("[-5,-3] & [-2,7,9] = " +
                optimal.compute(new int[]{-5,-3}, new int[]{-2,7,9}));
    }
}

