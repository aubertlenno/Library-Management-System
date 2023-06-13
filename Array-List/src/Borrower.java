package src;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Borrower {
    private String username, password;

    public Borrower(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return password;
    }

    public void returnBook() {
        Scanner input = new Scanner(System.in);
        ArrayList<String> borrowData = new ArrayList<>();
        String returnEntry = "";

        try (Scanner fileScanner = new Scanner(new File("BorrowData.txt"))) {
            while (fileScanner.hasNextLine()) {
                borrowData.add(fileScanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String returnerName = this.getUsername();

        System.out.println("Enter the title of the book you want to return:");
        String returnTitle = input.nextLine();
        Admin.clearConsole();

        for (String line : borrowData) {
            String[] data = line.split(" - ");
            if (data[0].equals(returnTitle) && data[1].equals(returnerName)) {
                returnEntry = line;
                break;
            }
        }

        if (returnEntry.isEmpty()) {
            System.out.println("This book is not listed as borrowed by you.");
            return;
        }

        borrowData.remove(returnEntry);

        try (FileWriter fw = new FileWriter("BorrowData.txt", false);
             PrintWriter pw = new PrintWriter(fw)) {
            for (String line : borrowData) {
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Thank you for returning the book!");
    }

    public void borrowingStatus() throws IOException {
        String borrower = this.getUsername();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        List<String> lines = Files.readAllLines(Paths.get("BorrowData.txt"));
        boolean hasBorrowed = false;
        System.out.println("Borrowed book:\n");

        for (String line : lines) {
            String[] data = line.split(" - ");

            if (data[1].equals(borrower)) {
                hasBorrowed = true;
                LocalDate borrowDate = LocalDate.parse(data[2], dtf);
                Period p = Period.between(borrowDate, currentDate);

                if (p.getDays() > 7) {
                    System.out.println("You have overdue book: " + data[0] + "\n");
                } else {
                    int remainingDays = 7 - p.getDays();
                    System.out.println(data[0] + ", please return in " + remainingDays + " days.");
                }
            }
        }

        if (!hasBorrowed) {
            System.out.println("You currently do not have a borrowed book!\n");
        }
    }

    public void addToList() throws IOException {
        Scanner input = new Scanner(System.in);
        FileWriter fw = new FileWriter("src/usersList.txt", true);
        PrintWriter out = new PrintWriter(fw);
        String file = "src/usersList.txt";

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

    public void borrowBook(Book book) throws IOException {
        if (!book.isBorrowed()) {
            Scanner input = new Scanner(System.in);
            String borrower = this.getUsername();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate borrowDate = LocalDate.now();

            try (FileWriter fw = new FileWriter("BorrowData.txt", true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(book.getTitle() + " - " + borrower + " - " + dtf.format(borrowDate));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Thank you! You have successfully borrowed the book.");
        } else {
            List<String> lines = Files.readAllLines(Paths.get("BorrowData.txt"));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (String line : lines) {
                String[] data = line.split(" - ");

                if (data[0].equals(book.getTitle())) {
                    LocalDate borrowDate = LocalDate.parse(data[2], dtf);
                    System.out.println("Sorry, this book is currently being borrowed. Please wait until " + borrowDate.plusWeeks(1));
                }
            }
        }
    }

    public void getBook(String title, String type) throws IOException {
        Scanner in = new Scanner(System.in);

        if (type.equalsIgnoreCase("A")) {
            Book foundbook = null;
            for (Book book : BookInterface.library) {
                if (book.getTitle().equalsIgnoreCase(title) && book instanceof AudioBook) {
                    foundbook = book;
                    break;
                }
            }
            if (foundbook != null) {
                foundbook.printBookDets();
                System.out.println("\n" + "Borrow? (Y/N)");
                String yesno = in.nextLine();

                if (yesno.equalsIgnoreCase("Y")) {
                    this.borrowBook(foundbook);
                }
            } else {
                System.out.println("Sorry, we currently do not have this audiobook in our catalogue");
            }
        } else if (type.equalsIgnoreCase("P")) {
            Book foundbook = null;
            for (Book book : BookInterface.library) {
                if (book.getTitle().equalsIgnoreCase(title) && book instanceof PrintedBook) {
                    foundbook = book;
                    break;
                }
            }
            if (foundbook != null) {
                foundbook.printBookDets();
                System.out.println("\n" + "Borrow? (Y/N)");
                String yesno = in.nextLine();

                if (yesno.equalsIgnoreCase("Y")) {
                    this.borrowBook(foundbook);
                }
            } else {
                System.out.println("Sorry, we currently do not have this printed book in our catalogue");
            }
        } else {
            System.out.println("Sorry, we currently do not have this book in our catalogue");
        }
    }

    public void getBookBenchmark(String title) throws IOException {
        boolean found = false;
        for (Book book : BookInterface.library) {
            if (book.getTitle().equals(title)) {
                System.out.println("Found!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Not found!");
        }
    }

    public void userMenu() throws IOException {
        boolean repeat2 = true;

        while (repeat2) {
            Scanner in = new Scanner(System.in);
            System.out.println("Welcome " + this.getUsername() + "!\n" +
                    "========== MENU ==========\n" +
                    "Please select a command:\n" +
                    "1) Borrow a book\n" +
                    "2) Return book\n" +
                    "3) Borrow status\n" +
                    "4) Exit to main menu");

            int command = in.nextInt();
            Admin.clearConsole();

            switch (command) {
                case 1:
                    System.out.println("Look for books:\n" +
                            "1) Show all books\n" +
                            "2) Based on genre\n" +
                            "3) Based on author\n" +
                            "4) Search");

                    int command2 = in.nextInt();
                    in.nextLine();
                    Admin.clearConsole();

                    switch (command2) {
                        case 1:
                            Book.showBooks();
                            System.out.println("Enter the title of the book you want to borrow: ");
                            String inpt = in.nextLine();
                            System.out.println("Is it an audiobook or printed book? (A/P)");
                            String inpt2 = in.nextLine();
                            this.getBook(inpt, inpt2);
                            break;

                        case 2:
                            Book.listGenre();
                            System.out.println("Select the genre you want to choose: ");
                            String genreChosen = in.nextLine();
                            Admin.clearConsole();
                            Book.printAllin(genreChosen);
                            System.out.println("Enter the title of the book you want to borrow: ");
                            String titleFromGenre = in.nextLine();
                            System.out.println("Is it an audiobook or printed book? (A/P)");
                            String typeFromGenre = in.nextLine();
                            this.getBook(titleFromGenre, typeFromGenre);
                            Admin.clearConsole();
                            break;

                        case 3:
                            System.out.println("Enter the name of the author you are looking for: ");
                            String authorIn = in.nextLine();
                            Admin.clearConsole();

                            if (Book.isValidAuthor(authorIn)) {
                                Book.printAllwith(authorIn);
                                System.out.println("Enter the title of the book you want to borrow: ");
                                String titleFromAuthor = in.nextLine();
                                System.out.println("Is it an audiobook or printed book? (A/P)");
                                String typeFromAuthor = in.nextLine();
                                this.getBook(titleFromAuthor, typeFromAuthor);
                            } else {
                                System.out.println("Sorry, we currently don't have books written by " + authorIn);
                            }
                            break;

                        case 4:
                            System.out.println("Enter the title of the book you are looking for: ");
                            String titleSearch = in.nextLine();
                            System.out.println("Is it an audiobook or printed book? (A/P)");
                            String typeFromSearch = in.nextLine();
                            this.getBook(titleSearch, typeFromSearch);
                            break;
                    }
                    break;

                case 2:
                    this.returnBook();
                    break;

                case 3:
                    this.borrowingStatus();
                    break;

                case 4:
                    repeat2 = false;
                    break;
            }
        }
    }

    public static void userLogin() throws FileNotFoundException, IOException {
        File file2 = new File("src/usersList.txt");
        Scanner saveUser = new Scanner(file2);
        Scanner input2 = new Scanner(System.in);
        HashMap<String, String> usersList = new HashMap<>();

        while (saveUser.hasNextLine()) {
            String next2 = saveUser.nextLine();
            StringTokenizer token = new StringTokenizer(next2, ";");

            if (token.countTokens() != 2) {
                System.out.println("Incorrect data format on line: " + next2);
                continue;
            }

            String username = token.nextToken();
            String pass = token.nextToken();

            usersList.put(username, pass);
        }

        System.out.println("1) Log in\n" + "2) Make an account");
        int input = input2.nextInt();
        Scanner scn = new Scanner(System.in);

        switch (input) {
            case 1:
                boolean validUser = false;
                System.out.println("Username: ");
                String userInpt = scn.nextLine();
                System.out.println("Password: ");
                String passInpt = scn.nextLine();

                if (usersList.containsKey(userInpt)) {
                    if (usersList.get(userInpt).equals(passInpt)) {
                        validUser = true;
                    }
                }

                if (validUser) {
                    Borrower currentUser = new Borrower(userInpt, passInpt);
                    Admin.clearConsole();
                    currentUser.userMenu();
                } else {
                    Admin.clearConsole();
                    System.out.println("Invalid username/password, please try again");
                }
                break;

            case 2:
                System.out.println("Username: ");
                String userInpt2 = scn.nextLine();
                System.out.println("Password: ");
                String passInpt2 = scn.nextLine();
                Borrower currentUser = new Borrower(userInpt2, passInpt2);
                currentUser.addToList();
                usersList.put(userInpt2, passInpt2);
                Admin.clearConsole();
                currentUser.userMenu();
                break;
        }
    }
}
