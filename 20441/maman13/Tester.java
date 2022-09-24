public class Tester {
    public static void main(String[] args) {
        RGBImage image = buildImage();
        System.out.println(image);

        new Test("Some flipping", image) {

            @Override
            public boolean run(RGBImage image) {
                image.rotateClockwise();
                image.flipVertical();
                return image.getPixel(0, 0).equals(new RGBColor(2, 2, 2));
            }

        };

        new Test("Shifting rows and columns", image) {

            @Override
            public boolean run(RGBImage image) {
                image.shiftCol(-1);
                image.shiftRow(-1);
                return image.getPixel(0, 0).equals(new RGBColor(2, 2, 2));
            }

        };

        new Test("Flipping and shifting", image) {

            @Override
            public boolean run(RGBImage image) {
                image.rotateClockwise();
                image.shiftRow(-1);
                image.rotateClockwise();
                image.shiftCol(-1);
                return image.getPixel(0, 0).equals(new RGBColor(2, 2, 2));
            }

        };

    }

    private static RGBImage buildImage() {
        RGBColor[][] pixels = new RGBColor[][] {

                { rgb(0, 0, 0), rgb(1, 1, 1), rgb(2, 2, 2) },

                { rgb(1, 1, 1), rgb(2, 2, 2), rgb(0, 0, 0) },

                { rgb(2, 2, 2), rgb(0, 0, 0), rgb(1, 1, 1) },

                { rgb(0, 0, 0), rgb(1, 1, 1), rgb(2, 2, 2) },

        };
        return new RGBImage(pixels);
    }

    private static RGBColor rgb(int r, int g, int b) {
        return new RGBColor(r, g, b);
    }

    public static class Test {

        public Test(String name, RGBImage image) {
            System.out.print("TESTING: " + name + "... ");
            boolean res = run(new RGBImage(image));
            if (res) {
                System.err.println("SUCCESS");
            } else {
                System.out.println("FAILED");
            }
        }

        public boolean run(RGBImage image) {
            return true;
        }

    }
}
