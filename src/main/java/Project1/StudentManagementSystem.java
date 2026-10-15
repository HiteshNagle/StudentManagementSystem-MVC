package Project1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

// Student blueprint
class Student implements Serializable {
    private int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age + " | Course: " + course;
    }
}

// Main application
public class StudentManagementSystem {
    private static final String FILE_NAME = "students.dat";
    private static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            int choice = readInt(scanner, "Enter choice: ");

            switch (choice) {
                case 1: addStudent(scanner); break;
                case 2: viewStudents(); break;
                case 3: updateStudent(scanner); break;
                case 4: deleteStudent(scanner); break;
                case 5:
                    saveData();
                    isRunning = false;
                    System.out.println("Data saved. Exiting...");
                    break;
                default: System.out.println("Invalid choice. Please select from 1-5.");
            }
        }
        scanner.close();
    }

    // Helper method to safely read integers
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input! Please enter numbers only.");
                scanner.nextLine(); // Clear bad input
            }
        }
    }

    // Helper method to validate Strings
    private static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Error: Input cannot be empty.");
                continue;
            }

            boolean isValid = true;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (!Character.isLetter(c) && c != ' ') {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                return input;
            } else {
                System.out.println("Error: Invalid input! Please enter alphabets only (No numbers/symbols).");
            }
        }
    }

    // Add new student
    private static void addStudent(Scanner scanner) {
        int id = readInt(scanner, "Enter ID: ");
        String name = readString(scanner, "Enter Name: ");
        int age = readInt(scanner, "Enter Age: ");
        String course = readString(scanner, "Enter Course: ");

        students.add(new Student(id, name, age, course));
        System.out.println("Student added.");
    }

    // View all students
    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Update existing student
    private static void updateStudent(Scanner scanner) {
        int id = readInt(scanner, "Enter Student ID to update: ");

        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(readString(scanner, "Enter new Name: "));
                s.setAge(readInt(scanner, "Enter new Age: "));
                s.setCourse(readString(scanner, "Enter new Course: "));

                System.out.println("Student updated.");
                return;
            }
        }
        System.out.println("ID not found.");
    }

    // Delete a student
    private static void deleteStudent(Scanner scanner) {
        int id = readInt(scanner, "Enter Student ID to delete: ");

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.remove(i);
                System.out.println("Student deleted.");
                return;
            }
        }
        System.out.println("ID not found.");
    }

    // Save data to file
    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // Load data from file
    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}