public class Test {

    public static void main(String[] args) {
        System.out.println("Testing Q1...");
        System.out.println(Ex14.maximalDrop(new int[] { 5, 21, 3, 22, 12, 7, 27, 6, 4 }));
        System.out.println(Ex14.maximalDrop(new int[] { 5, 21, 3, 22, 12, 7, 26, 14 }));
        System.out.println(Ex14.maximalDrop(new int[] { 5, 15, 3, 22, 12, 7, 27, 14 }));
        System.out.println();

        int[][] matQ2 = new int[][] {

                { 0, 1, 0, 1, 1, 0, 1 },

                { 1, 0, 1, 1, 0, 0, 1 },

                { 0, 0, 0, 1, 0, 1, 1 },

                { 0, 0, 0, 0, 0, 0, 0 },

                { 1, 0, 1, 1, 0, 0, 1 },

                { 0, 1, 0, 1, 1, 1, 1 },

                { 0, 1, 0, 1, 1, 1, 1 },

        };
        System.out.println(Ex14.isSink(matQ2));

        boolean[][] mat = new boolean[][] {

                { false, false, false, false, false },

                { false, false, false, true, true },

                { false, false, true, true, false },

                { false, false, false, true, true },

                { false, false, false, false, false }, };
        System.out.println(Ex14.size(mat, 3, 4));

        int[] a = new int[] { 1, 2, 4, 2 };
        int[] b = new int[] { 2, 2, 4, 1 };
        System.out.println(Ex14.isPermutation(a, b));
    }
}
