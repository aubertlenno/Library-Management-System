package code;

import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Admin {
    private String username, password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return password;
    }

    public void AdminMenu() throws IOException {
        NumberFormat money = NumberFormat.getCurrencyInstance();
        Scanner input = new Scanner(System.in);
        boolean menuRepeat = true;

        while (menuRepeat) {
            FileWriter fw = new FileWriter("src/SaveLibrary.txt", true);
            PrintWriter out = new PrintWriter(fw);
            System.out.println("Welcome " + this.getUsername() + "!\n\n" +
                    "========== MAIN MENU ==========\n" +
                    "Please select a command:\n" +
                    "1) Add an audiobook\n" +
                    "2) Add a printed book\n" +
                    "3) Get average cost of audiobooks\n" +
                    "4) Get average cost of printed books\n" +
                    "5) Print whole catalogue\n" +
                    "6) Print list of all recorded genres\n" +
                    "7) Print total cost of your library\n" +
                    "8) Create new admin\n" +
                    "9) Delete a book\n" +
                    "10) Exit to main menu");

            int command = input.nextInt();
            Scanner scn = new Scanner(System.in);

            clearConsole();

            switch (command) {
                case 1:
                    System.out.println("Insert title:");
                    String title = scn.nextLine();

                    System.out.println("Insert author:");
                    String author = scn.nextLine();

                    System.out.println("Insert genre:");
                    String genre = scn.nextLine();

                    System.out.println("Insert synopsis:");
                    String synopsis = scn.nextLine();

                    System.out.println("Insert duration:");
                    double duration = scn.nextDouble();

                    Book entry = new AudioBook(title, author, genre, duration, synopsis);
                    out.println(entry.toString());
                    BookInterface.library.add(entry);
                    System.out.println("Book added successfully");

                    break;

                case 2:
                    System.out.println("Insert title:");
                    String title2 = scn.nextLine();

                    System.out.println("Insert author:");
                    String author2 = scn.nextLine();

                    System.out.println("Insert genre:");
                    String genre2 = scn.nextLine();

                    System.out.println("Insert synopsis:");
                    String synopsis2 = scn.nextLine();

                    System.out.println("Insert page count:");
                    int page = scn.nextInt();

                    Book entry2 = new PrintedBook(title2, author2, genre2, page, synopsis2);
                    out.println(entry2.toString());
                    BookInterface.library.add(entry2);
                    System.out.println("Book added successfully");


                    break;

                case 3:
                    System.out.println(money.format(AudioBook.getAvg()));
                    break;

                case 4:
                    System.out.println(money.format(PrintedBook.getAvg()));
                    break;

                case 5:
                    String filePath = "src/SaveLibrary.txt";

                    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        br.readLine(); // Skip the header line
                        while ((line = br.readLine()) != null) {
                            StringTokenizer st = new StringTokenizer(line, ";");

                            String bookType = st.nextToken();
                            String bookTitle = st.nextToken();
                            String bookAuthor = st.nextToken();
                            String bookGenre = st.nextToken();
                            String bookCost = st.nextToken();
                            String bookSynopsis = st.nextToken();
                            String bookDurationOrPage = st.nextToken();

                            if ("A".equalsIgnoreCase(bookType)) {
                                System.out.println("Type: Audio Book");
                            } else if ("P".equalsIgnoreCase(bookType)) {
                                System.out.println("Type: Printed Book");
                            }

                            System.out.println("Title: " + bookTitle);
                            System.out.println("Author: " + bookAuthor);
                            System.out.println("Genre: " + bookGenre);
                            System.out.println("Cost: " + bookCost);
                            System.out.println("Synopsis: " + bookSynopsis);

                            if ("A".equalsIgnoreCase(bookType)) {
                                System.out.println("Duration: " + bookDurationOrPage);
                            } else if ("P".equalsIgnoreCase(bookType)) {
                                System.out.println("Pages: " + bookDurationOrPage);
                            }

                            System.out.println();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 6:
                    Book.listGenre();
                    break;

                case 7:
                    System.out.println(money.format(Book.totalCost()));
                    break;

                case 8:
                    Scanner inpt = new Scanner(System.in);
                    System.out.println("Username: ");
                    String usrInpt = inpt.nextLine();

                    System.out.println("Password: ");
                    String passInpt = inpt.nextLine();

                    Admin newAdmin = new Admin(usrInpt, passInpt);
                    newAdmin.addToList();
                    break;

                case 9:
                    System.out.println("Insert title of the book to delete:");
                    String titleToDelete = scn.nextLine();
                    deleteBook(titleToDelete);
                    break;

                case 10:
                    System.out.println("Exit program? (Y/N)");
                    String yesno = input.next();
                    if (yesno.compareTo("Y") == 0 || yesno.compareTo("y") == 0) {
                        System.out.println("Exiting...");
                        menuRepeat = false;
                    }
                    break;
            }
            out.close();
        }
    }

    public void addToList() throws IOException {
        Scanner input = new Scanner(System.in);
        FileWriter fw = new FileWriter("src/adminList.txt", true);
        PrintWriter out = new PrintWriter(fw);
        String file = "src/adminList.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            StringTokenizer tokenizer = new StringTokenizer(username + ";" + password, ";");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                writer.write(token);
                if (tokenizer.hasMoreTokens()) {
                    writer.write(";");
                }
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String title) {
        File inputFile = new File("src/SaveLibrary.txt");

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to open the file.");
            e.printStackTrace();
        }

        // Buffer for storing the modified contents
        StringBuilder contents = new StringBuilder();

        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                String[] bookDetails = trimmedLine.split(";");
                if(bookDetails.length > 1 && bookDetails[1].equals(title)) continue;
                contents.append(currentLine).append(System.getProperty("line.separator"));
            }

            reader.close();

            // Overwrite the original file
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
            writer.write(contents.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading or writing the file.");
            e.printStackTrace();
        }
    }

    public void addBooksAutomatically(int numOfBooks) throws IOException {
        FileWriter fw = new FileWriter("src/SaveLibraryBenchmark.txt", true);
        PrintWriter out = new PrintWriter(fw);
        for (int i = 0; i < numOfBooks; i++) {
            String title = "Book" + (i+1);
            String author = "Author" + (i+1);
            String genre = "Genre" + (i+1);
            String synopsis = "Synopsis" + (i+1);
            int page = 100;

            Book entry = new PrintedBook(title, author, genre, page, synopsis);
            out.println(entry.toString());
            BookInterface.library.add(entry);
        }
        out.close();
    }

    public static void adminLogin() throws IOException {
        File file2 = new File("src/adminList.txt");
        Scanner saveAdmin = new Scanner(file2);
        Scanner input2 = new Scanner(System.in);
        HashMap<String, String> adminList = new HashMap<>();

        while (saveAdmin.hasNextLine()) {
            String next2 = saveAdmin.nextLine();
            StringTokenizer token = new StringTokenizer(next2, ";");
            if (token.countTokens() != 2) {
                System.out.println("Incorrect data format on line: " + next2);
                continue;
            }
            String username = token.nextToken();
            String pass = token.nextToken();

            adminList.put(username, pass);
        }

        if (adminList.isEmpty()) {
            System.out.println("No admin user found, please create a new account");

            System.out.println("Username: ");
            String userInpt = input2.nextLine();

            System.out.println("Password: ");
            String passInpt = input2.nextLine();

            Admin currentAdmin = new Admin(userInpt, passInpt);
            currentAdmin.addToList();
            adminList.put(userInpt, passInpt);
            Admin.clearConsole();
            currentAdmin.AdminMenu();
        } else {
            boolean validUser = false;
            System.out.println("Username: ");
            String userInpt = input2.nextLine();
            System.out.println("Password: ");
            String passInpt = input2.nextLine();

            if (adminList.containsKey(userInpt)) {
                if (adminList.get(userInpt).equals(passInpt)) {
                    validUser = true;
                }
            }

            if (validUser) {
                Admin currentAdmin = new Admin(userInpt, passInpt);
                Admin.clearConsole();
                currentAdmin.AdminMenu();
            } else {
                Admin.clearConsole();
                System.out.println("Invalid username/password, please try again");
            }
        }
    }
}