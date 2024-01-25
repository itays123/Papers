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
        System.out.println("\n\nInitial list:");
        printStudentList(averageScores);

        // Add new student
        Student sarah = new Student(1112, 2000, "Sarah", "Israeli");
        averageScores.add(sarah, 100);
        System.out.println("\n\nAdded new student Sarah:");
        printStudentList(averageScores);

        // update average score
        averageScores.add(jacob, 80);
        System.out.println("\n\nUpdated jacob's score:");
        printStudentList(averageScores);

        // delete student
        averageScores.remove(issac);
        System.out.println("\n\nRemoved Issac:");
        printStudentList(averageScores);

    }

    public static void printStudentList(AssociationTable<Student, Integer> list) {
        Iterator<Student> stuIterator = list.keyIterator();
        Student currStudent;
        while (stuIterator.hasNext()) {
            currStudent = stuIterator.next();
            System.out.println("Student: " + currStudent + " ;Average: " + list.get(currStudent));
        }
    }
}
