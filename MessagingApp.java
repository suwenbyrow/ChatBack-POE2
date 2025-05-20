/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.messagingapp;

/**
 *
 * @author RC_Student_lab
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessagingApp {

    private static List<Message> messages = new ArrayList<>();
    private static int numMessagesSent = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to QuickChat.");

        // Simulate user login (no actual login implemented)
        boolean loggedIn = true;

        if (loggedIn) {
            while (true) {
                System.out.println("\nPlease choose an option:");
                System.out.println("1. Send Message");
                System.out.println("2. Show recently sent messages (Coming Soon...)");
                System.out.println("3. Quit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        sendMessage(scanner);
                        break;
                    case 2:
                        System.out.println("This feature is currently under development.");
                        break;
                    case 3:
                        System.out.println("Thank you for using QuickChat!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Login failed. Application will now exit.");
        }
        scanner.close();
    }

    public static void sendMessage(Scanner scanner) {
        System.out.println("\n--- Send New Message ---");

        // Get recipient number
        String recipientCell;
        while (true) {
            System.out.print("Enter recipient cell number (must start with '+' and have no more than 10 digits after): ");
            recipientCell = scanner.nextLine();
            if (isValidRecipientCell(recipientCell)) {
                break;
            } else {
                System.out.println("Invalid cell phone number format. Please try again.");
            }
        }

        // Get message
        String messageText;
        while (true) {
            System.out.print("Enter your message (maximum 250 characters): ");
            messageText = scanner.nextLine();
            if (messageText.length() <= 250) {
                break;
            } else {
                System.out.println("Message exceeds the maximum length. Please shorten it.");
            }
        }

        // Generate unique message ID
        String messageId = generateUniqueMessageId();

        // Generate message hash (simplified for demonstration)
        String messageHash = generateMessageHash(messageId, messageText);

        // Create and store the message
        Message newMessage = new Message(messageId, numMessagesSent + 1, recipientCell, messageText, messageHash);
        messages.add(newMessage);
        numMessagesSent++;

        System.out.println("\nMessage sent successfully!");
        System.out.println("Message Details:");
        System.out.println("  Message ID: " + newMessage.getMessageId());
        System.out.println("  Message Hash: " + newMessage.getMessageHash());
        System.out.println("  Recipient: " + newMessage.getRecipientCell());
        System.out.println("  Message: " + newMessage.getMessage());

        // Options after sending a message
        System.out.println("\nChoose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message (Not implemented)");
        System.out.println("3. Store Message to send later (Not implemented)");

        int postSendOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (postSendOption) {
            case 1:
                sendMessage(scanner);
                break;
            case 2:
                System.out.println("Disregard Message functionality is not yet implemented.");
                break;
            case 3:
                System.out.println("Store Message for later functionality is not yet implemented.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public static boolean isValidRecipientCell(String cellNumber) {
        Pattern pattern = Pattern.compile("^\\+\\d{1,10}$");
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
        if (message.length() < 5) {
            return "INVALID_MESSAGE_LENGTH"; // Or handle this case differently
        }
        return messageId.substring(0, 2) + ":" + message.charAt(0) + message.charAt(1) + ":" +
               message.substring(message.length() - 2).toUpperCase();
    }
}

class Message {
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

    public String getMessageId() {
        return messageId;
    }

    public int getNumSent() {
        return numSent;
    }

    public String getRecipientCell() {
        return recipientCell;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

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