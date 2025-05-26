/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatappmain;

/**
 * @author RC_Student_lab
 */
import java.util.*;
import java.util.regex.*;

public class ChatAppMain {

    // ========================== Message Class ==========================
    static class Message {
        private String messageId;
        private int numSent;
        private String recipientCell;
        private String message;
        private String messageHash;

        public Message(String messageId, int numSent, String recipientCell, String message, String messageHash) {
            this.messageId = messageId;
            this.numSent = numSent;
            this.recipientCell = recipientCell;
            this.message = message;
            this.messageHash = messageHash;
        }
        
        public String getMessageId() { return messageId; }
        public int getNumSent() { return numSent; }
        public String getRecipientCell() { return recipientCell; }
        public String getMessage() { return message; }
        public String getMessageHash() { return messageHash; }

        @Override
        public String toString() {
            return "Message{" +
                    "messageId='" + messageId + '\'' +
                    ", numSent=" + numSent +
                    ", recipientCell='" + recipientCell + '\'' +
                    ", message='" + message + '\'' +
                    ", messageHash='" + messageHash + '\'' +
                    '}';
        }
    }

    // ========================== User Class ==========================
    static class User {
        String firstName;
        String lastName;
        String username;
        String password;
        String phoneNumber;

        public User(String firstName, String lastName, String username, String password, String phoneNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.phoneNumber = phoneNumber;
        }

        public void welcomeUser() {
            System.out.println("Welcome " + firstName + " " + lastName + ", it is great to see you again.");
        }
    }

      
    // ========================== Login Class ==========================
    static class Login {
        private String storedUsername;
        private String storedPassword;
        private String storedPhone;

        public boolean checkUserName(String username) {
            return username.contains("_") && username.length() <= 20;
        }

        public boolean checkPasswordComplexity(String password) {
            boolean hasUpper = !password.equals(password.toLowerCase());
            boolean hasDigit = password.matches(".*\\d.*");
            boolean hasSpecial = password.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*");
            boolean isLengthOk = password.length() >= 8;
            return hasUpper && hasDigit && hasSpecial && isLengthOk;
        }

         public boolean checkCellPhoneNumber(String phone) {
            return phone.matches("^\\+27\\d{9}$");
        }

        public boolean loginUser(String username, String password) {
            return username.equals(this.storedUsername) && password.equals(this.storedPassword);
        }

        public String returnLoginStatus(boolean success) {
            return success ? "Login successful. Welcome back!" : "Login failed. Incorrect credentials.";
        }
    }

    // ========================== Messaging Utilities ==========================
    static List<Message> messages = new ArrayList<>();
    static int numMessagesSent = 0;

   
    public static boolean isValidRecipientCell(String cellNumber) {
        Pattern pattern = Pattern.compile("^\\+27\\d{9}$");
        Matcher matcher = pattern.matcher(cellNumber);
        return matcher.matches();
    }
   
    public static String generateUniqueMessageId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String generateMessageHash(String messageId, String message) {
        if (message.length() < 5) return "INVALID";
        return messageId.substring(0, 2) + ":" + message.charAt(0) + message.charAt(1) + ":" +
                message.substring(message.length() - 2).toUpperCase();
    }

    // ========================== Send Message Flow ==========================
    public static void sendMessage(Scanner scanner) {
        System.out.println("\n--- Send New Message ---");

        String recipientCell;
        while (true) {
            System.out.print("Enter recipient cell number (e.g. +27831234567): ");
            recipientCell = scanner.nextLine();
            if (isValidRecipientCell(recipientCell)) break;
            else System.out.println("Invalid number. Try again.");
        }

        String messageText;
        while (true) {
            System.out.print("Enter your message (max 250 characters): ");
            messageText = scanner.nextLine();
            if (messageText.length() <= 250) break;
            else System.out.println("Message too long. Try again.");
        }

        String messageId = generateUniqueMessageId();
        String messageHash = generateMessageHash(messageId, messageText);
        Message newMessage = new Message(messageId, ++numMessagesSent, recipientCell, messageText, messageHash);
        messages.add(newMessage);

        System.out.println("\nMessage sent successfully!");
        System.out.println("ID: " + newMessage.getMessageId());
        System.out.println("Hash: " + newMessage.getMessageHash());
        System.out.println("To: " + newMessage.getRecipientCell());
        System.out.println("Message: " + newMessage.getMessage());
    }

    // ========================== Main App Entry ==========================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

       
        String firstName;
        String lastName;
        String username;
        String password;
        String phoneNumber;
        
        //Debug variable
        int debug=0;
  
        //Check debug status
        switch (debug) {
           case 0:
                        // Registration
                        System.out.print("Enter First Name: ");
                        firstName = scanner.nextLine();

                        System.out.print("Enter Last Name: ");
                        lastName = scanner.nextLine();

                        System.out.print("Enter Username (must contain _): ");
                        username = scanner.nextLine();
                        while (!login.checkUserName(username)){ 
                                System.out.print("Username is incorrectly formatted.");
                                System.out.println("\nEnter Username (must contain _): ");
                                username = scanner.nextLine();
                        }
                        System.out.print("Enter Password: ");
                        password = scanner.nextLine();
                        while (!login.checkPasswordComplexity(password)){
                                System.out.print("Password is incorrectly formatted.");
                                System.out.println("\nPassword must be > 8 characters and all lower case. ");
                                password = scanner.nextLine();
                        }

                        System.out.print("Enter Cellphone (+27...): ");
                        phoneNumber = scanner.nextLine();
                        while (!login.checkCellPhoneNumber(phoneNumber)){
                                System.out.print("Phone number is incorrect and must start with +27");
                                System.out.println("\nPhone number must start with +27");
                                phoneNumber = scanner.nextLine();
                        }      
                        default:    
                        // Registration
                        firstName = "Suwen";
                        lastName = "Byrow";
                        username = "suwenb";
                        password = "YruS123#";
                        phoneNumber = "+27835678906";
        }

        String registrationMsg = "User has been registered successfully.";
        System.out.println(registrationMsg);
        scanner.close();  
        
        User user = new User(firstName, lastName, username, password, phoneNumber);
        user.welcomeUser();

        // Login Loop
        boolean isLoggedIn = false;
                 
        while (!isLoggedIn) {
            
            System.out.println("\nLogin to Continue:");
            System.out.print("Username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Password: ");
            String loginPassword = scanner.nextLine();

            isLoggedIn = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returnLoginStatus(isLoggedIn));
        }
            
        // Messaging Menu
        while (true) {
            System.out.println("\n1. Send Message\n2. Show Sent Count\n3. Quit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: sendMessage(scanner); break;
                case 2: System.out.println("Messages sent: " + numMessagesSent); break;
                case 3:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}