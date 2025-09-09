package medianOfTwoSortedArrays;

public final class ArrayValidator {
    private ArrayValidator() {}

    public static void ensureSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                throw new IllegalArgumentException("Array not sorted");
            }
        }
    }
}

