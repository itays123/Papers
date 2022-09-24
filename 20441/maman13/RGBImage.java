/**
 * Assignment 13 - Using a class to represent an image made of pixels
 * 
 * @author Itay Schechner
 * @version 04-04-2021
 */
public class RGBImage {

    // constructors
    private RGBColor[][] _image;

    // constructors

    /**
     * Generates a black image with a specified size
     * 
     * @param rows thw height of the image
     * @param cols the width of the image
     */
    public RGBImage(int rows, int cols) {
        _image = new RGBColor[rows][cols];
        fillEmptyPixels();
    }

    /**
     * Generates an image with specified pixels
     * 
     * @param pixels the pixels to be assigned
     */
    public RGBImage(RGBColor[][] pixels) {
        _image = copyOf(pixels);
    }

    /**
     * Copy constructor
     * 
     * @param other the image to be copied
     */
    public RGBImage(RGBImage other) {
        _image = copyOf(other._image);
    }

    // set empty pixels to black
    private void fillEmptyPixels() {
        for (int i = 0; i < _image.length; i++) {
            for (int j = 0; j < _image[i].length; j++) {
                if (_image[i][j] == null) {
                    _image[i][j] = new RGBColor();
                }
            }
        }
    }

    // copy array to prevent aliasing
    private RGBColor[][] copyOf(RGBColor[][] array) {
        int height = array.length;
        int width = array[0].length;
        RGBColor[][] copy = new RGBColor[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (array[i][j] != null)
                    copy[i][j] = new RGBColor(array[i][j]);
                else
                    copy[i][j] = new RGBColor();
            }
        }
        return copy;
    }

    // class methods

    /**
     * Get the height of the image
     * 
     * @return the number of rows in the image
     */
    public int getHeight() {
        return _image.length;
    }

    /**
     * Get the width of the image
     * 
     * @return the number of columns in the image
     */
    public int getWidth() {
        return _image[0].length;
    }

    private boolean areCoordinatesValid(int row, int col) {
        return row >= 0 && col >= 0 && row < getHeight() && col < getWidth();
    }

    /**
     * Get a specific pixel from the image
     * 
     * @param row the row where the pixel is found
     * @param col the column where the pixel is found
     * @return the required pixel, a black pixel if not found
     */
    public RGBColor getPixel(int row, int col) {
        if (!areCoordinatesValid(row, col))
            return new RGBColor();
        return new RGBColor(_image[row][col]);
    }

    /**
     * Change the value of a specific pixel
     * 
     * @param row   the row where the pixel can be found
     * @param col   the column where the pixel can be found
     * @param pixel the new value of the pixel
     */
    public void setPixel(int row, int col, RGBColor pixel) {
        if (areCoordinatesValid(row, col)) { // if coords are valid
            _image[row][col] = new RGBColor(pixel);
        }
    }

    /**
     * Compares the value of two images
     * 
     * @param other the image to be compared with
     * @return false if found a difference between the images, true otherwise
     */
    public boolean equals(RGBImage other) {
        if (getHeight() != other.getHeight() || getWidth() != other.getWidth())
            return false;

        // loop through the image and compare each pixel
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < _image[i].length; j++) {
                if (!getPixel(i, j).equals(other.getPixel(i, j)))
                    return false;
            }
        }

        return true;
    }

    // flipping and rotating

    /**
     * Flip the image horizontally
     */
    public void flipHorizontal() {
        /*
         * In order to flip horizontally, every pixel has to be assigned to the
         * corresponsing pixel in the other side. meaning, for every row of the image:
         * 
         * 1. find the half index. if there is not (i.e, the width is 3), round down and
         * the middle column will remain the same.
         * 
         * 2. switch the values of the two pixels. for a width of 4, row[0] <=> row[3]
         */
        int halfIndex = getWidth() / 2;
        int matchingIndex;
        RGBColor temp;
        RGBColor[] row;
        for (int i = 0; i < getHeight(); i++) {
            row = _image[i];
            for (int j = 0; j < halfIndex; j++) {
                matchingIndex = row.length - 1 - j; // max pixel index - i;

                // switch the values
                temp = row[j];
                row[j] = row[matchingIndex];
                row[matchingIndex] = temp;
            }
        }
    }

    /**
     * Flip the image vertically
     */
    public void flipVertical() {
        /*
         * In order to flip verticallly, every row has to be assigned to the
         * corresponsing row in the other side.
         * 
         * 1. find the half index. if there is not (i.e, the height is 3), round down
         * and the middle row will remain the same.
         * 
         * 2. switch the values of the two rows. for a height of 4, _image[0] <=>
         * _image[3]
         */
        int halfIndex = getHeight() / 2;
        int matchingIndex;
        RGBColor[] temp;
        for (int i = 0; i < halfIndex; i++) {
            matchingIndex = _image.length - 1 - i; // max row index - i;

            // switch the values
            temp = _image[i];
            _image[i] = _image[matchingIndex];
            _image[matchingIndex] = temp;
        }
    }

    /**
     * Invert every pixel of the image
     */
    public void invertColors() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                _image[i][j].invert();
            }
        }
    }

    private void flipDiagonal() {
        RGBColor[][] newImage = new RGBColor[getWidth()][getHeight()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                newImage[j][i] = _image[i][j];
            }
        }
        _image = newImage;
    }

    /**
     * Rotate the image clockwise
     */
    public void rotateClockwise() {
        /*
         * In order to rotate the image by 90 degrees, it is possible to flip it twice
         * so that the angle between the flips would equal 45 dgrees. refer to:
         */
        // https://he.wikipedia.org/wiki/%D7%A9%D7%99%D7%A7%D7%95%D7%A3_(%D7%9E%D7%AA%D7%9E%D7%98%D7%99%D7%A7%D7%94)
        flipDiagonal();
        flipHorizontal();
    }

    /**
     * Rotate the image counter clockwise
     */
    public void rotateCounterClockwise() {
        /*
         * In order to rotate the image by -90 degrees, it is possible to flip it twice
         * so that the angle between the flips would equal -45 dgrees. refer to:
         */
        // https://he.wikipedia.org/wiki/%D7%A9%D7%99%D7%A7%D7%95%D7%A3_(%D7%9E%D7%AA%D7%9E%D7%98%D7%99%D7%A7%D7%94)
        flipHorizontal();
        flipDiagonal();
    }

    /**
     * Add n black columns to the left of the image
     * 
     * @param offset the number of columns to be added
     */
    public void shiftCol(int offset) {
        flipDiagonal();
        shiftRow(offset);
        flipDiagonal();
    }

    /**
     * Add n black rows to the top of the image
     * 
     * @param offset the number of rows to be added
     */
    public void shiftRow(int offset) {
        if (Math.abs(offset) <= getHeight()) { // if offset is valid
            RGBColor[][] newPixels = new RGBColor[getHeight()][getWidth()];

            for (int i = 0; i < getHeight(); i++) {
                int newRowIndex = offset + i;

                // if the new row index is valid, assign it to the corresponding row
                if (newRowIndex < getHeight() && newRowIndex >= 0) {
                    newPixels[newRowIndex] = _image[i];
                }
            }
            _image = newPixels;
            fillEmptyPixels();
        }
    }

    /**
     * Convert the image to grayscale
     * 
     * @return a bidimentional double array representing the converted grayscale
     *         values.
     */
    public double[][] toGrayscaleArray() {
        double[][] result = new double[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                result[i][j] = getPixel(i, j).convertToGrayscale();
            }
        }
        return result;
    }

    /**
     * Builds a string format of the image.
     * 
     * @return a string containing the colors in the order of rows and columns.
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                result += getPixel(i, j).toString();
                if (j + 1 != getWidth()) // if current pixel is not last in its row
                    result += " ";
            }
            if (i + 1 != getHeight()) // if current row is not last
                result += "\n";
        }
        return result;
    }

    /**
     * Returns the _image bidimentional array;
     * 
     * @return the value of the pixels color array.
     */
    public RGBColor[][] toRGBColorArray() {
        return copyOf(_image);
    }

}
