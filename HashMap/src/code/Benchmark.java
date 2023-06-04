package code;

import java.io.IOException;
import java.util.Scanner;

public class Benchmark {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose what to measure:\n" +
                "1. Adding Books\n" +
                "2. Removing Books\n" +
                "3. Search Book");
        int input = sc.nextInt();
        switch(input) {
            case 1:
                try {
                    Admin admin = new Admin("Lenno", "123");  // replace with actual admin username and password
                    System.out.println("Enter the number of books to add: ");
                    int numOfBooks = sc.nextInt();   // read the number of books to add from user
                    long startTime, endTime, totalTime = 0;

                    for (int i = 0; i < numOfBooks; i++) {
                        // you can replace these strings with actual values or generate them dynamically
                        String title = "Title" + i;
                        String author = "Author" + i;
                        String genre = "Genre" + i;
                        String synopsis = "Synopsis" + i;
                        double duration = i;
                        int pages = i;

                        // Add an AudioBook
                        startTime = System.nanoTime();
                        Book audioBook = new AudioBook(title, author, genre, duration, synopsis);
                        BookInterface.library.put(audioBook.getTitle(), audioBook);
                        endTime = System.nanoTime();
                        totalTime += (endTime - startTime);

                        // Add a PrintedBook
                        startTime = System.nanoTime();
                        Book printedBook = new PrintedBook(title, author, genre, pages, synopsis);
                        BookInterface.library.put(printedBook.getTitle(), printedBook);
                        endTime = System.nanoTime();
                        totalTime += (endTime - startTime);
                    }

                    System.out.println("Time taken to add " + numOfBooks + " books (both audio and printed): " + totalTime + " ns");
                } catch (NumberFormatException e) {
                    System.err.println("Please provide a valid integer when prompted.");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case 2:
                Admin admin = new Admin("Lenno", "123");

                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter the number of books to add and delete: ");
                int numberOfBooks = scanner.nextInt();

                // Add books
                try {
                    admin.addBooksAutomatically(numberOfBooks);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Validate the entered number of books to delete
                if(numberOfBooks > BookInterface.library.size()){
                    System.out.println("You're trying to delete more books than the library has. Exiting...");
                    return;
                }

                System.out.println("Deleting " + numberOfBooks + " books...");

                long startTime = System.nanoTime();

                for (int i = 0; i < numberOfBooks; i++) {
                    String titleToDelete = (String) BookInterface.library.keySet().toArray()[0]; // delete the first book in the library
                    admin.deleteBook(titleToDelete);
                }

                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;

                System.out.println("Books deleted successfully, time taken: " + elapsedTime + "ns");

                break;

            case 3:
                // Load books from SaveLibrary.txt into BookInterface.library
                try {
                    Book.loadBooksFromFile("src/SaveLibrary.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Create a borrower
                Borrower borrower = new Borrower("Franz", "Fr123");

                // The book title you want to search for
                String title = "The Lord of the Rings";

                // Capture the start time
                long startTime2 = System.nanoTime();

                // Perform the search operation
                try {
                    borrower.getBookBenchmark(title);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Capture the end time
                long endTime2 = System.nanoTime();

                // Calculate the duration in milliseconds
                long duration = (endTime2 - startTime2);

                System.out.println("The search operation took " + duration + " nanoseconds.");

                break;
        }
    }
}
