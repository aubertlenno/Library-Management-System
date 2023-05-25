import java.util.*;

public abstract class Book implements BookInterface {
    private String title, author, genre;
    public double cost;

    private static double totalCost;

    // book genre data tracker
    static HashMap<String, Integer> genreMap = new HashMap<String, Integer>(); // stores genre and their count

    // setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
        recordGenre(genre);
    }

    public void recordGenre(String genre) {
        genreMap.put(genre, genreMap.getOrDefault(genre, 0) + 1);
    }

    // inherited methods
    public void listGenre() {
        for (Map.Entry<String, Integer> entry : genreMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public double totalCost() {
        return totalCost;
    }

    // abstract methods
    abstract double getCost();

    // toString method
    public String toString() {
        return title + ";" + author + ";" + genre + ";" + cost;
    }
}