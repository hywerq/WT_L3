package server.model;

public class Student {
    private String studentId;
    private String name;
    private String surname;
    private String age;
    private String description;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nStudentID: " + studentId + "\n" +
                "Name: " + name + " " + surname + "\n" +
                "Age: " + age + "\n" +
                "Description: " + description + "\n";
    }
}
