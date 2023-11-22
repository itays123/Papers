/**
 * Represents a student in Java
 * 
 * @author Itay Schechner 328197462
 */
public class Student implements Comparable<Student> {

    private int id, birthYear;
    private String firstName, surName;

    /**
     * Creates a new student
     */
    public Student(int id, int birthYear, String firstName, String surName) {
        this.id = id;
        this.birthYear = birthYear;
        this.firstName = firstName;
        this.surName = surName;
    }

    public int getId() {
        return id;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", birthYear=" + birthYear + ", firstName=" + firstName + ", surName=" + surName
                + "]";
    }

    @Override
    public int compareTo(Student o) {
        return id - o.id;
    }

}
