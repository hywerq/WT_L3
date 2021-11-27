package server.model.dao;

import server.model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StudentFileDAO {
    private final ReadWriteLock lock;
    private ArrayList<Student> listOfStudents;

    public StudentFileDAO()
    {
        lock = new ReentrantReadWriteLock();

        try {
            listOfStudents = StudentFileSerDes.deserialize();
        }
        catch (IOException e) {
            listOfStudents = new ArrayList<>();
        }
    }

    public List<Student> getAllStudents() {
        return listOfStudents;
    }

    public Student getStudentById(String id) {
        if(listOfStudents.isEmpty())
            return null;

        for (Student student : listOfStudents) {
            if (student.getStudentId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public int getStudentIndex(String id) {
        if(listOfStudents.isEmpty())
            return -1;

        for (Student student : listOfStudents) {
            if (student.getStudentId().equals(id)) {
                return listOfStudents.indexOf(student);
            }
        }
        return -1;
    }

    public boolean insertStudentAt(int index, Student student) {
        boolean result = true;
        try {
            lock.writeLock().lock();
            listOfStudents.remove(index);
            listOfStudents.add(index, student);
            updateStudentFile();
        } catch (IOException e) {
            result = false;
        } finally {
            lock.writeLock().unlock();
        }
        return result;
    }

    public boolean addNewStudent(Student student) {
        boolean result = true;
        try {
            lock.writeLock().lock();
            listOfStudents.add(student);
            updateStudentFile();
        } catch (IOException e) {
            result = false;
        } finally {
            lock.writeLock().unlock();
        }
        return result;
    }

    private void updateStudentFile() throws IOException {
        StudentFileSerDes.serialize(listOfStudents);
    }
}
