package StudentManagementSystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager(); // Connecting to business logic
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
                case 1:
                    int id = readInt(scanner, "Enter ID: ");
                    String name = readString(scanner, "Enter Name: ");
                    int age = readInt(scanner, "Enter Age: ");
                    String course = readString(scanner, "Enter Course: ");

                    System.out.println(manager.addStudent(id, name, age, course));
                    break;

                case 2:
                    ArrayList<Student> list = manager.getAllStudents();
                    if (list.isEmpty()) {
                        System.out.println("No records found.");
                    } else {
                        for (Student s : list) System.out.println(s);
                    }
                    break;

                case 3:
                    int updateId = readInt(scanner, "Enter Student ID to update: ");
                    String newName = readString(scanner, "Enter new Name: ");
                    int newAge = readInt(scanner, "Enter new Age: ");
                    String newCourse = readString(scanner, "Enter new Course: ");

                    System.out.println(manager.updateStudent(updateId, newName, newAge, newCourse));
                    break;

                case 4:
                    int deleteId = readInt(scanner, "Enter Student ID to delete: ");
                    System.out.println(manager.deleteStudent(deleteId));
                    break;

                case 5:
                    isRunning = false;
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Please select from 1-5.");
            }
        }
        scanner.close();
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input! Please enter numbers only.");
                scanner.nextLine();
            }
        }
    }

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

            if (isValid) return input;
            System.out.println("Error: Invalid input! Please enter alphabets only.");
        }
    }
}