import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create table with 3 students
        Student abraham = new Student(1111, 2000, "Abraham", "Israeli");
        Student issac = new Student(2222, 1997, "Issac", "Israeli");
        Student jacob = new Student(3333, 2003, "Jacob", "Israeli");
        AssociationTable<Student, Integer> averageScores = new AssociationTable<Student, Integer>(
                new Student[] { jacob, abraham, issac },
                new Integer[] { 90, 89, 93 });
        printStudentList(averageScores);

        // Add new student
        Student sarah = new Student(1112, 2000, "Sarah", "Israeli");
        averageScores.add(sarah, 100);
        printStudentList(averageScores);

        // update average score
        averageScores.add(jacob, 80);
        printStudentList(averageScores);

        // delete student
        averageScores.remove(issac);
        printStudentList(averageScores);

    }

    public static void printStudentList(AssociationTable<Student, ?> list) {
        System.out.println("Students: ");
        Iterator<Student> stuIterator = list.keyIterator();
        while (stuIterator.hasNext()) {
            System.out.println(stuIterator.next());
        }
    }
}
