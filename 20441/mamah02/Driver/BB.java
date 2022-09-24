public class BB extends AA {
    private String _st;

    public BB() {
        _st = "bb";
    }

    public BB(String st, int val) {
        super(val);
        _st = st;
    }

    public String getSt() {
        return _st;
    }

    public boolean equals(Object ob) // 1 שיטה
    {
        System.out.print("Method used: 1, result: ");
        if ((ob != null) && (ob instanceof BB)) {
            if (_st.equals(((BB) ob)._st) && (getVal() == ((BB) ob).getVal()))
                return true;
        }
        return false;
    }

    public boolean equals(AA ob) // 2 שיטה
    {
        System.out.print("Method used: 2, result: ");
        if ((ob != null) && (ob instanceof BB)) {
            if (_st.equals(((BB) ob)._st) && (getVal() == ((BB) ob).getVal()))
                return true;
        }
        return false;
    }

    public boolean equals(BB ob) // 3 שיטה
    {
        System.out.print("Method used: 3, result:  ");
        if (ob != null) {
            if (_st.equals(((BB) ob)._st) && (getVal() == ((BB) ob).getVal()))
                return true;
        }
        return false;
    }

}
