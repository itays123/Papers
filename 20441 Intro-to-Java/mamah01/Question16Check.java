class Question16Check {
    public static void main(String[] args) {
        int num = 0, a = 0, b = 0, c = 0;
        while (a == b && a == c) {
            if (num % 10000 == 0)
                System.out.println(num);
            a = num % 100 / 10;
            b = (num - num / 100 * 100) / 10;
            c = num / 10 % 10;
            num++;
        }
        System.out.println(num);
    }
}