public class MyTest {

    public static void main(String[] args) {
        String text = "anything you can do i can do better";
        TextList list = new TextList(text);
        System.out.println(list);
        System.out.println(list.mostFrequentWord());
        System.out.println(list.mostFrequentStartingLetter());
    }

}
