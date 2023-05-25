package src;

import java.util.*;
import java.io.*;
import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, CustomException, IOException {

        NumberFormat money = NumberFormat.getCurrencyInstance(); // format for prices later

        File file = new File("src/SaveLibrary.txt"); // target file to read
        Scanner saveLibrary = new Scanner(file);

        String trash = saveLibrary.nextLine(); // throw away the sample format

        while (saveLibrary.hasNextLine()) {
            String next = saveLibrary.nextLine();
            StringTokenizer token = new StringTokenizer(next, ";");

            String type = token.nextToken(); // retrieve the bookType
            
            if (type.compareTo("A") == 0) { // create AudioBook object if bookType from file is "A"
                String title = token.nextToken();
                String author = token.nextToken();
                String genre = token.nextToken();
                double cost = Double.parseDouble(token.nextToken());
                double duration = Double.parseDouble(token.nextToken());

                Book entry = new AudioBook(title, author, genre, duration);
                entry.setCost(cost);
                BookInterface.library.add(entry);

            } else if (type.compareTo("P") == 0) { // create PrintedBook object if bookType from file is "P"
                String title = token.nextToken();
                String author = token.nextToken();
                String genre = token.nextToken();
                double cost = Double.parseDouble(token.nextToken());
                int duration = Integer.parseInt(token.nextToken());

                Book entry = new PrintedBook(title, author, genre, duration);
                entry.setCost(cost);
                BookInterface.library.add(entry);

            } else {
                throw new CustomException("Book type error!"); // if there's a miss input in book type from the txt file
            }
        }

        FileWriter fw = new FileWriter("src/SaveLibrary.txt", true); // to append newly added books from program to file
        PrintWriter out = new PrintWriter(fw);

        Scanner input = new Scanner(System.in);
        boolean repeat = true;

        while (repeat) {
            System.out.println("""
                    ========== MAIN MENU ==========
                    Please select a command:
                    1) Add an audiobook
                    2) Add a printed book
                    3) Get average cost of audiobooks
                    4) Get average cost of printed books
                    5) Close program""");
            int reply = input.nextInt();
            input.nextLine(); // clear token so that it doesn't mess up the next input

            switch (reply) {
                case 3:
                    System.out.println(money.format(AudioBook.getAvg()));
                    break;

                case 4:
                    System.out.println(money.format(PrintedBook.getAvg()));
                    break;

                case 5:
                    System.out.println("Exit? (Y/N)");
                    String redo = input.next();
                    if (redo.compareTo("N") == 0 || redo.compareTo("n") == 0) {
                        repeat = true;
                    } else {
                        repeat = false;
                        System.out.println("SHUT DOWN!");
                    }
                    break;
            } 

            if (reply == 1) {
                System.out.println("Insert title:");
                String title = input.nextLine();

                System.out.println("Insert author:");
                String author = input.nextLine();

                System.out.println("Insert genre:");
                String genre = input.nextLine();

                System.out.println("Insert duration:");
                double duration = input.nextDouble();

                // making the object and adding to library array + writing to file
                Book entry = new AudioBook(title, author, genre, duration);
                out.println(entry.toString());
                BookInterface.library.add(entry);

                boolean menuRepeat = true;
                while (menuRepeat) {
                    System.out.println(
                            """
                                    Choose next command:
                                    1) Print details of recent addition
                                    2) Print list of all recorded genres
                                    3) Print total cost of your library
                                    4) Print average cost of recorded audiobooks
                                    5) Print recent 10 addition
                                    6) Return to main menu""");

                    int choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println(entry.toString());
                            break;

                        case 2:
                            entry.listGenre();
                            break;

                        case 3:
                            System.out.println(money.format(entry.totalCost()));
                            break;

                        case 4:
                            System.out.println(money.format(AudioBook.getAvg()));
                            break;

                        case 5:
                            entry.listTen();
                            break;
                        case 6:
                            System.out.println("Exit to main menu? (Y/N)");
                            String yesno = input.next();
                            if (yesno.compareTo("Y") == 0 || yesno.compareTo("y") == 0) {
                                menuRepeat = false;
                            }
                            break;
                    }
                }

            } else if (reply == 2) {
                System.out.println("Insert title:");
                String title = input.nextLine();

                System.out.println("Insert author:");
                String author = input.nextLine();

                System.out.println("Insert genre:");
                String genre = input.nextLine();

                System.out.println("Insert page count:");
                int page = input.nextInt();

                // making the object and adding to library array + writing file
                Book entry = new PrintedBook(title, author, genre, page);
                out.println(entry.toString());
                BookInterface.library.add(entry);

                boolean menuRepeat = true;
                while (menuRepeat) {
                    System.out.println(
                            """
                                    Choose next command:
                                    1) Print details of recent addition
                                    2) Print list of all recorded genres
                                    3) Print total cost of your library
                                    4) Print average cost of recorded printed books
                                    5) Print recent 10 addition
                                    6) Return to main menu""");

                    int choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println(entry.toString());
                            break;

                        case 2:
                            entry.listGenre();
                            break;

                        case 3:
                            System.out.println(money.format(entry.totalCost()));
                            break;

                        case 4:
                            System.out.println(money.format(PrintedBook.getAvg()));
                            break;

                        case 5:
                            entry.listTen();
                            break;
                    }
                    System.out.println("Exit to main menu? (Y/N)");
                    String yesno = input.next();
                    if (yesno.compareTo("Y") == 0 || yesno.compareTo("y") == 0) {
                        menuRepeat = false;
                    }
                }
            }
        }

        out.close();
    }
}

// careful how you copy