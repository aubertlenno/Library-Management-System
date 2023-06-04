package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, CustomException, IOException {
        File file = new File("src/SaveLibrary.txt"); // target file to read
        Scanner saveLibrary = new Scanner(file);

        String trash = saveLibrary.nextLine(); // throw away the sample format

        while (saveLibrary.hasNextLine()) {
            String next = saveLibrary.nextLine();
            StringTokenizer token = new StringTokenizer(next, ";");

            if (token.countTokens() != 7) { // Check for the correct number of data elements
                System.out.println("Incorrect data format on line: " + next);
                continue; // Skip this line and move to the next
            }

            String type = token.nextToken(); // retrieve the bookType
            if (type.compareTo("A") == 0) { // create AudioBook object if bookType from file is "A"
                String title = token.nextToken();
                String author = token.nextToken();
                String genre = token.nextToken();
                double cost = Double.parseDouble(token.nextToken());
                String synopsis = token.nextToken();
                double duration = Double.parseDouble(token.nextToken());

                Book entry = new AudioBook(title, author, genre, duration, synopsis);
                entry.setCost(cost);
                BookInterface.library.add(entry);  // Add to HashSet
            } else if (type.compareTo("P") == 0) {
                String title = token.nextToken();
                String author = token.nextToken();
                String genre = token.nextToken();
                double cost = Double.parseDouble(token.nextToken());

                String synopsis = token.nextToken();
                int page = Integer.parseInt(token.nextToken());

                Book entry = new PrintedBook(title, author, genre, page, synopsis);
                entry.setCost(cost);
                BookInterface.library.add(entry); // Add to HashSet
            } else {
                throw new CustomException("Book type error!"); // if there's a miss input in book type from the txt file
            }
        }

        Scanner userInput = new Scanner(System.in);
        boolean repeat = true;

        while (repeat) {
            System.out.println("  __  __  ___  ____  _   _ ___              ");
            System.out.println(" |  \\/  |/ _ \\/ ___|| | | |_ _|             ");
            System.out.println(" | |\\/| | | | \\___ \\| |_| || |              ");
            System.out.println(" | |  | | |_| |___) |  _  || |              ");
            System.out.println(" |_|  |_|\\___/|____/|_| |_|___|             ");
            System.out.println("  _     ___ ____  ____      _    ______   __");
            System.out.println(" | |   |_ _| __ )|  _ \\    / \\  |  _ \\ \\ / /");
            System.out.println(" | |    | ||  _ \\| |_) |  / _ \\ | |_) \\ V / ");
            System.out.println(" | |___ | || |_) |  _ <  / ___ \\|  _ < | |  ");
            System.out.println(" |_____|___|____/|_| \\_\\/_/   \\_\\_| \\_\\|_|");
            System.out.println("");
            System.out.println("Log in as a: ");
            System.out.println("1. Admin");
            System.out.println("2. Borrower");
            System.out.println("3. Close program");

            int logInpt = 0;
            logInpt = userInput.nextInt();

            Admin.clearConsole();

            switch (logInpt) {
                case 1:
                    Admin.adminLogin();
                    break;

                case 2:
                    Borrower.userLogin();
                    break;

                case 3:
                    System.out.println("Exit program? (Y/N)");
                    String yesno = userInput.next();
                    if (yesno.compareTo("Y") == 0 || yesno.compareTo("y") == 0) {
                        System.out.println("Exiting...");
                        repeat = false;
                    }
                    break;

                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }
}
