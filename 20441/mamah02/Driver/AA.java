public class AA {
    private int _val = 0;

    public AA() {
        _val = 5;
    }

    public AA(int val) {
        _val = val;
    }

    public int getVal() {
        return _val;
    }

    public void setVal(int val) {
        _val = val;
    }

    public String toString() {
        return "val=" + _val;
    }
}
