import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Gradecal {

    // ANSI Colors for terminal output
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println(CYAN + "===== STUDENT GRADE CALCULATOR =====" + RESET);

        System.out.print("Enter student name: ");
        String studentName = scan.nextLine();

        System.out.print("Enter total number of subjects: ");
        int numSubjects = scan.nextInt();
        scan.nextLine(); // Clear buffer

        String[] subjectNames = new String[numSubjects];
        int[] marks = new int[numSubjects];

        int totalMarks = 0;

        // Input subject names + marks
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("\nEnter name of subject " + (i + 1) + ": ");
            subjectNames[i] = scan.nextLine();

            System.out.print("Enter marks for " + subjectNames[i] + " (out of 100): ");
            int m = scan.nextInt();

            // Validate marks
            while (m < 0 || m > 100) {
                System.out.println(RED + "Invalid marks! Enter between 0 and 100." + RESET);
                System.out.print("Re-enter marks for " + subjectNames[i] + ": ");
                m = scan.nextInt();
            }
            marks[i] = m;
            totalMarks += m;

            scan.nextLine(); // clear buffer
        }

        // Average percentage with decimals
        double avg = (double) totalMarks / numSubjects;

        // Grade Calculation
        String grade;
        if (avg >= 90) grade = "A+";
        else if (avg >= 80) grade = "A";
        else if (avg >= 70) grade = "B+";
        else if (avg >= 60) grade = "B";
        else if (avg >= 50) grade = "C";
        else if (avg >= 40) grade = "D";
        else grade = "E";

        // PASS / FAIL color output
        String status = avg >= 40 ? GREEN + "PASS" + RESET : RED + "FAIL" + RESET;

        // Display Results
        System.out.println(CYAN + "\n===== RESULT SUMMARY =====" + RESET);
        System.out.println("Student Name: " + studentName);

        System.out.println("\nSubject-wise Marks:");
        for (int i = 0; i < numSubjects; i++) {
            System.out.println("• " + subjectNames[i] + ": " + marks[i]);
        }

        System.out.println("\nTotal Marks: " + totalMarks);
        System.out.printf("Average Percentage: %.2f%%\n", avg);
        System.out.println("Grade: " + grade);
        System.out.println("Result Status: " + status);

        // Save result to file
        saveToFile(studentName, subjectNames, marks, totalMarks, avg, grade);

        System.out.println(YELLOW + "\n✔ Result saved in file: result.txt" + RESET);

        scan.close();
    }

    // Function to save results into a file
    public static void saveToFile(String name, String[] subjects, int[] marks,
                                  int total, double avg, String grade) {

        try {
            FileWriter fw = new FileWriter("result.txt");

            fw.write("===== STUDENT RESULT =====\n");
            fw.write("Name: " + name + "\n\n");

            fw.write("Subject-wise Marks:\n");
            for (int i = 0; i < subjects.length; i++) {
                fw.write(subjects[i] + ": " + marks[i] + "\n");
            }

            fw.write("\nTotal Marks: " + total + "\n");
            fw.write(String.format("Average Percentage: %.2f%%\n", avg));
            fw.write("Grade: " + grade + "\n");

            if (avg >= 40) fw.write("Status: PASS\n");
            else fw.write("Status: FAIL\n");

            fw.close();
        } 
        catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
