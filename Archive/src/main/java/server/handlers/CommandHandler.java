package server.handlers;

import server.model.Student;
import server.model.UserRoleType;
import server.model.dao.StudentFileDAO;

public class CommandHandler {
    private static StudentFileDAO dao;

    public static String sendHelp() {
        return "Commands:\n" +
                "all - prints all student's files\n" +
                "find 'id' - prints student's file if it exists\n" +
                "add 'id' 'name' 'surname' 'age' 'description'  - adds new student's file\n" +
                "edit 'id' 'name' 'surname' 'age' 'description'  - changes student's file\n" +
                "quit" +
                "\n\0";
    }

    public static String showAllStudents(String[] arguments) {
        if(!arguments[0].equalsIgnoreCase(UserRoleType.ADMIN.toString()) &&
                !arguments[0].equalsIgnoreCase(UserRoleType.GUEST.toString())) {
            return "Access denied\n\0";
        }

        dao = new StudentFileDAO();

        StringBuilder result = new StringBuilder();
        for (Student student : dao.getAllStudents()) {
            result.append(student.toString());
        }

        return result.toString()  + "\0";
    }

    public static String findStudentById(String[] arguments) {
        if(!arguments[0].equalsIgnoreCase(UserRoleType.ADMIN.toString()) &&
                !arguments[0].equalsIgnoreCase(UserRoleType.GUEST.toString())) {
            return "Access denied\n\0";
        }

        dao = new StudentFileDAO();

        if(arguments.length > 2) {
            Student student = dao.getStudentById(arguments[2]);
            if(student == null) {
                return "Couldn't find student's file with id=" + arguments[2] + "\n\0";
            } else {
                return student.toString() + "\0";
            }
        } else {
            return "Not enough arguments\n\0";
        }
    }

    public static String editStudent(String[] arguments) {
        if(!arguments[0].equalsIgnoreCase(UserRoleType.ADMIN.toString())) {
            return "Access denied\n\0";
        }

        if(arguments.length < 6) {
            return "Not enough arguments\n\0";
        }

        dao = new StudentFileDAO();

        int studentIndex = dao.getStudentIndex(arguments[2]);
        if(studentIndex == -1) {
            return "Couldn't find student's file with id=" + arguments[2] + "\n\0";
        } else {
            if(dao.insertStudentAt(studentIndex, getNewStudent(arguments))) {
                return "Successfully changed!\n\0";
            } else {
                return "Couldn't change, try later\n\0";
            }
        }
    }

    private static Student getNewStudent(String[] arguments) {
        Student student = new Student();
        student.setStudentId(arguments[2]);
        student.setName(arguments[3]);
        student.setSurname(arguments[4]);
        student.setAge(arguments[5]);

        StringBuilder description = new StringBuilder();
        for (int i = 6; i < arguments.length; i++) {
            description.append(arguments[i]).append(" ");
        }
        student.setDescription(description.toString());

        return student;
    }

    public static String addNewStudent(String[] arguments) {
        if(!arguments[0].equalsIgnoreCase(UserRoleType.ADMIN.toString())) {
            return "Access denied\n\0";
        }

        if(arguments.length < 6) {
            return "Not enough arguments\n\0";
        }

        dao = new StudentFileDAO();
        if(dao.getStudentById(arguments[2]) != null) {
            return "Student's file with such ID already exists!\n\0";
        }

        if(dao.addNewStudent(getNewStudent(arguments)))
        {
            return "Successfully added!\n\0";
        } else {
            return "Couldn't add, try later\n\0";
        }
    }
}
