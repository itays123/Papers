/**
 * 
 */

/**
 * @author Course tutors
 *
 */
public class StudentTester {

	public static void main(String[] args) {

		System.out.println("Black Image Constructor:");
		RGBImage rgbImg0 = new RGBImage(3, 4);
		System.out.println(rgbImg0);

		System.out.println("Constructor with RGBColor[][] Array Parameter:");
		RGBColor[][] rgbArray1 = new RGBColor[3][4];
		for (int i = 0; i < rgbArray1.length; i++)
			for (int j = 0; j < rgbArray1[0].length; j++)
				rgbArray1[i][j] = new RGBColor(i, i, i);
		RGBImage rgbImg1 = new RGBImage(rgbArray1);
		System.out.println(rgbImg1);

		System.out.println("Copy Constructor:");
		RGBImage rgbImg2 = new RGBImage(rgbImg1);
		System.out.println(rgbImg2);

		// rgbImg1 testing
		System.out.println("getHeight:");
		System.out.println(rgbImg1.getHeight() + "\n");
		System.out.println("getWidth:");
		System.out.println(rgbImg1.getWidth() + "\n");

		System.out.println("rotateClockwise:");
		rgbImg1.rotateClockwise();
		System.out.println(rgbImg1);
		System.out.println("rotateCounterClockwise:");
		rgbImg1.rotateCounterClockwise();
		System.out.println(rgbImg1);
		System.out.println("rotateCounterClockwise:");
		rgbImg1.rotateCounterClockwise();
		System.out.println(rgbImg1);
		System.out.println("rotateClockwise:");
		rgbImg1.rotateClockwise();
		System.out.println(rgbImg1);

		System.out.println("flipHorizontal:");
		rgbImg1.flipHorizontal();
		System.out.println(rgbImg1);
		System.out.println("flipHorizontal:");
		rgbImg1.flipHorizontal();
		System.out.println(rgbImg1);

		System.out.println("shiftRow -1:");
		rgbImg1.shiftRow(-1);
		System.out.println(rgbImg1);
		System.out.println("shiftCol 3:");
		rgbImg1.shiftCol(3);
		System.out.println(rgbImg1);
		System.out.println("shiftCol -2:");
		rgbImg1.shiftCol(-2);
		System.out.println(rgbImg1);

		System.out.println("flipVertical:");
		rgbImg1.flipVertical();
		System.out.println(rgbImg1);
		System.out.println("flipVertical:");
		rgbImg1.flipVertical();
		System.out.println(rgbImg1);

		System.out.println("flipHorizontal:");
		rgbImg1.flipHorizontal();
		System.out.println(rgbImg1);
		System.out.println("flipHorizontal:");
		rgbImg1.flipHorizontal();
		System.out.println(rgbImg1);

		// rgbImg2 testing
		System.out.println("toRGBColorArray:");
		RGBColor[][] rgbArray2 = rgbImg2.toRGBColorArray();
		for (int i = 0; i < rgbArray2.length; i++) {
			for (int j = 0; j < rgbArray2[0].length; j++)
				System.out.print(rgbArray2[i][j] + "  ");
			System.out.println();
		}
		System.out.println();

		System.out.println("invertColors:");
		rgbImg2.invertColors();
		System.out.println(rgbImg2);

		System.out.println("setPixel + getPixel:");
		// next 4 lines set last line of rgbImg2 to be (0,0,0)
		rgbImg2.setPixel(2, 0, new RGBColor(0, 0, 0));
		rgbImg2.setPixel(2, 1, new RGBColor(0, 0, 0));
		rgbImg2.setPixel(2, 2, new RGBColor(0, 0, 0));
		rgbImg2.setPixel(2, 3, new RGBColor(0, 0, 0));
		System.out.println(rgbImg2.getPixel(2, 0));
		System.out.println();

		System.out.println("toGrayscaleArray:");
		double[][] grayscaleArray = rgbImg2.toGrayscaleArray();
		for (int i = 0; i < grayscaleArray.length; i++) {
			for (int j = 0; j < grayscaleArray[0].length; j++)
				System.out.print(grayscaleArray[i][j] + "  ");
			System.out.println();
		}
		System.out.println();

		System.out.println("equals:");
		// compare rgbImg1 with rgbImg2
		System.out.println(rgbImg1.equals(rgbImg2) ? true : false);

		System.out.println("Have a Nice Work!");
	}
}
