

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ATMExceptionDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            File file = new File("cardreader.txt");
            Scanner reader = new Scanner(file);
            reader.close();

            System.out.print("Enter account number: ");
            int account = sc.nextInt();

            System.out.print("Enter withdrawal amount: ");
            int amount = sc.nextInt();

            int balance = 5000;

            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }

            if (amount > balance) {
                throw new ArithmeticException("Insufficient balance");
            }

            System.out.println("Withdrawal successful!");
            System.out.println("Account Number: " + account);
            System.out.println("Remaining Balance: " + (balance - amount));

        } catch (FileNotFoundException e) {
            System.out.println("Error: Card reader file missing");

        } catch (java.util.InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());

        } catch (ArithmeticException e) {
            System.out.println("Transaction failed: " + e.getMessage());

        } finally {
            System.out.println("ATM session completed.");
        }

        sc.close();
    }
}
