package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class Book implements BookInterface {
    private String title, author, genre, synopsis, type;
    private double cost;
    private boolean isAudioBook;

    private static double totalCost;

    // book genre data tracker
    static HashMap<String, Integer> genreCountMap = new HashMap<>();

    public void recordGenre(String genre) {
        if (genreCountMap.containsKey(genre)) {
            genreCountMap.put(genre, genreCountMap.get(genre) + 1);
        } else {
            genreCountMap.put(genre, 1);
        }
    }

    public static void listGenre() {
        for (Map.Entry<String, Integer> entry : genreCountMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // setters
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
        recordGenre(genre);
    }

    public String getGenre() {
        return this.genre;
    }

    public static double totalCost() {
        return totalCost;
    }

    // abstract methods
    abstract double getCost();

    public void setCost(double cost) {
        this.cost = cost;
        totalCost += cost;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // toString method
    public String toString() {
        return title + ";" + author + ";" + genre + ";" + cost + ";" + synopsis;
    }

    public static void showBooks() {
        int i = 1;
        for (Book b : BookInterface.library.values()) {
            System.out.println(i + ". " + b.getTitle() + " - " + b.getAuthor() + " (" + b.getType() + ")");
            i++;
        }
    }

    public void printBookDets(){
        System.out.println("Title: " + this.title + "\n"
                + "Author: " + this.author +"\n"
                + "Genre: " + this.genre +"\n"
                + "Type: " + this.type +"\n"
                + "Synopsis: " + this.synopsis );

    }

    public static void printAllin(String genre){
        System.out.println("All " + genre + " books:");

        int i = 1;
        for (Book b : BookInterface.library.values()) {
            if (b.getGenre().equals(genre)) {
                System.out.println(i + ". " + b.getTitle() + " - " + b.getAuthor() + " (" + b.getType() + ")");
                i++;
            }
        }
    }

    public static boolean isValidAuthor(String author){
        for (Book b : BookInterface.library.values()) {
            if (b.getAuthor().equals(author)) {
                return true;
            }
        }
        return false;
    }
    public static void printAllwith(String author){
        System.out.println("All " + author + " books:");
        int i = 1;
        for (Book b : BookInterface.library.values()) {
            if (b.getAuthor().equals(author)) {
                System.out.println(i + ". " + b.getTitle() + " - " + b.getAuthor() + " (" + b.getType() + ")");
                i++;
            }
        }
    }



    public boolean isBorrowed() throws IOException{
        List<String> lines = Files.readAllLines(Paths.get("BorrowData.txt"));
        boolean status = false;
        for (String line : lines) {
            String[] data = line.split(" - ");
            if (data[0].equals(this.getTitle())) {
                status = true;
            }
        }
        return status;
    }

    public static void loadBooksFromFile(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));

        // Remove the first line
        if (!lines.isEmpty()) {
            lines.remove(0);
        }

        for (String line : lines) {
            String[] details = line.split(";");
            if (details.length != 7) {
                System.out.println("Skipping invalid line: " + line);
                continue;
            }
            String bookType = details[0];
            String title = details[1];
            String author = details[2];
            String genre = details[3];
            String cost = details[4];
            String synopsis = details[5];

            try {
                double durationOrPage = Double.parseDouble(details[6]);

                Book book;
                if (bookType.equalsIgnoreCase("A")) {
                    book = new AudioBook(title, author, genre, durationOrPage, synopsis);
                } else if (bookType.equalsIgnoreCase("P")) {
                    book = new PrintedBook(title, author, genre, (int)durationOrPage, synopsis);
                } else {
                    System.out.println("Unknown book type in line: " + line);
                    continue;
                }
                library.put(title, book);
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid number format in line: " + line);
            }
        }
    }
}