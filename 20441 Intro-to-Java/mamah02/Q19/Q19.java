public class Q19 {

    static abstract class A {
        public abstract boolean f(int x);
    }

    static class B extends A {

        public boolean f(double x) {
            return x == 2.0;
        }

        @Override
        public boolean f(int x) {
            return false;
        }
    }

    public static void main(String[] args) {
        A a = new B();
        a.f(2);
    }
}
