package StudentManagementSystem;

import java.io.*;
import java.util.ArrayList;

public class StudentManager {
    private static final String FILE_NAME = "students.dat";
    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
        loadData();
    }

    // 5. Corrupted File Handling (Exception catch-all)
    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Warning: Data file corrupted or incompatible. Starting fresh.");
            students = new ArrayList<>();
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Duplicate ID check
    public boolean isIdExists(int id) {
        for (Student s : students) {
            if (s.getId() == id) return true;
        }
        return false;
    }

    public String addStudent(int id, String name, int age, String course) {
        if (id <= 0) return "Error: ID must be a positive number (>0).";
        if (isIdExists(id)) return "Error: A student with ID " + id + " already exists.";
        if (age < 15 || age > 100) return "Error: Please enter a valid age (15-100).";

        students.add(new Student(id, name, age, course));
        saveData(); // 6. Save after successful add
        return "Success: Student added successfully.";
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public String updateStudent(int id, String newName, int newAge, String newCourse) {
        if (newAge < 15 || newAge > 100) return "Error: Please enter a valid age (15-100).";

        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(newName);
                s.setAge(newAge);
                s.setCourse(newCourse);
                saveData(); // 6. Save after successful update
                return "Success: Student updated successfully.";
            }
        }
        return "Error: Student ID not found.";
    }

    public String deleteStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.remove(i);
                saveData(); // 6. Save after successful delete
                return "Success: Student deleted successfully.";
            }
        }
        return "Error: Student ID not found.";
    }
}