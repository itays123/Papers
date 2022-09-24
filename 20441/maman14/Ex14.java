/**
 * Assignment 14 - doing complex recursion and efficency calculations.
 * 
 * @author Itay Schechner
 * @version 12-04-2021
 */
public class Ex14 {

    /**
     * Searches for the maximal drop in a given array
     * 
     * @param a the array to check
     * @return the maximal distance between two values, so that the lower value
     *         comes after the bigger one.
     */
    public static int maximalDrop(int[] a) {
        int peakIndex = 0, maxDrop = 0, currentDrop = 0; // Memory complexity: O(1)
        for (int i = 0; i < a.length; i++) {
            // Time complexity: 0(n)

            if (a[i] > a[peakIndex]) // if drop is negative
                peakIndex = i;

            else { // drop is positive and might be bigger than the maximum so far
                currentDrop = a[peakIndex] - a[i];
                maxDrop = currentDrop > maxDrop ? currentDrop : maxDrop;
            }

        }
        return maxDrop;
    }

    /**
     * Searches for a "sink" in a certain matrix of 0s and 1s.
     * 
     * @param mat
     * @return the index of the sink
     */

    public static int isSink(int[][] mat) {
        int min = 0, max = mat.length - 1; // Memory complexity: O(1)
        while (min < max) { // the loop will be entered 0.5n times

            // check min value
            if (!nthRowCanBeSink(mat, min, max) || !nthColCanBeSink(mat, min, max))
                min++;

            // check max value
            if (!nthRowCanBeSink(mat, max, min) || !nthColCanBeSink(mat, max, min))
                max--;

        } // Time complexity: O(n)

        if (min == max)
            return nthIndexIsSink(mat, min) ? min : -1; // n times
        else // max < min
            return -1;
    }

    /*
     * check if the nth row can be a sink
     */
    private static boolean nthRowCanBeSink(int[][] mat, int n, int compareWith) {
        return mat[n][n] == 0 && mat[n][compareWith] == 0;
    }

    /*
     * check if the nth col can be sink
     */
    private static boolean nthColCanBeSink(int[][] mat, int n, int compareWith) {
        return mat[compareWith][n] == 1 || n == compareWith;
    }

    /*
     * check if the nth index is a sink
     */
    private static boolean nthIndexIsSink(int[][] mat, int n) {
        for (int i = 0; i < mat.length; i++) {
            if (mat[n][i] != 0 || // row is invalid
                    (i != n && mat[i][n] != 1)) // col is invalid
                return false;
        }
        return true;
    }

    /**
     * Checks the size of a hole in a given point
     * 
     * @param mat the given area
     * @param x   the x coord of the point
     * @param y   the y coors of the point
     * @return the size of the hole
     */
    public static int size(boolean[][] mat, int x, int y) {
        boolean[][] path = new boolean[mat.length][mat[0].length];
        return size(mat, path, x, y);
    }

    /*
     * An overload of the method above that uses a marking matrix
     */
    private static int size(boolean[][] mat, boolean[][] path, int x, int y) {
        if (!areValidIndexes(mat, x, y) || !mat[x][y] || path[x][y]) // current area doesn't exist, clean or visited
            return 0;

        // current area is a subset of a hole and was not visited before
        path[x][y] = true; // set the current path to true to avoid rechecking this.
        return 1 + size(mat, path, x - 1, y - 1) + size(mat, path, x - 1, y) + size(mat, path, x - 1, y + 1)
                + size(mat, path, x, y - 1) + size(mat, path, x, y + 1) + size(mat, path, x + 1, y - 1)
                + size(mat, path, x + 1, y) + size(mat, path, x + 1, y + 1);

    }

    /*
     * Check if x and y are valid coordinates
     */
    private static boolean areValidIndexes(boolean[][] mat, int x, int y) {
        return x >= 0 && y >= 0 && x < mat.length && y < mat[0].length;
    }

    /**
     * Checks if two arrays have the exact same elements
     * 
     * @param a the first array to be checked
     * @param b the second array to be checked
     * @return the check's result
     */
    public static boolean isPermutation(int[] a, int[] b) {
        if (a.length != b.length)
            return false;
        return isPermutation(a, b, 0, 0);
    }

    private static boolean isPermutation(int[] a, int[] b, int index, int searchAt) {

        if (index >= a.length) // if all indexes were searched
            return true;

        if (searchAt >= b.length) // if the search index is not valid
            return false;

        if (a[index] == b[searchAt]) {

            // swap the values of b to prevent from finding the same element twice
            int temp = b[index];
            b[index] = b[searchAt];
            b[searchAt] = temp;

            boolean result = isPermutation(a, b, index + 1, index + 1);

            // restore values
            b[searchAt] = b[index];
            b[index] = temp;

            return result;
        }

        // searchAt is valid, but the searched element is not found
        return isPermutation(a, b, index, searchAt + 1);
    }

}
