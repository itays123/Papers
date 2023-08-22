/**
 * Generate random methods to see if this works
 */
public class RGBColor {

    private int _red;
    private int _green;
    private int _blue;

    public RGBColor() {
        _red = 0;
        _green = 0;
        _blue = 0;
    }

    public RGBColor(int _red, int _green, int _blue) {
        this._red = _red;
        this._green = _green;
        this._blue = _blue;
    }

    public RGBColor(RGBColor other) {
        _red = other._red;
        _green = other._green;
        _blue = other._blue;
    }

    public int getRed() {
        return this._red;
    }

    public void setRed(int _red) {
        this._red = _red;
    }

    public int getGreen() {
        return this._green;
    }

    public void setGreen(int _green) {
        this._green = _green;
    }

    public int getBlue() {
        return this._blue;
    }

    public void setBlue(int _blue) {
        this._blue = _blue;
    }

    @Override
    public String toString() {
        return "(" + _red + "," + _green + "," + _blue + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RGBColor)) {
            return false;
        }
        RGBColor rGBColor = (RGBColor) o;
        return _red == rGBColor._red && _green == rGBColor._green && _blue == rGBColor._blue;
    }

    public double convertToGrayscale() {
        return 0.3 * _red + 0.59 * _green + 0.11 * _blue;
    }

    public void invert() {
        setRed(255 - _red);
        setGreen(255 - _green);
        setBlue(255 - _blue);
    }

}
