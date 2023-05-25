package src;

import java.util.*;

public abstract class Book implements BookInterface {
    private String title, author, genre;
    private double cost;

    private static double totalCost;

    // book genre data tracker
    static ArrayList<String> genreList = new ArrayList<String>(); // stores all recorded genre
    static ArrayList<Integer> genreCount = new ArrayList<Integer>(); // array that counts the corresponding genre above

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

    public void setCost(double cost) {
        this.cost = cost;
        totalCost += cost;
    }

    public void recordGenre(String genre) {
        for (int i = 0; i < genreList.size(); i++) {
            if (genreList.get(i).compareTo(genre) == 0) {
                genreCount.set(i, genreCount.get(i) + 1);
                return;
            } // counts every time recorded genre is found
        }

        genreList.add(genre);
        genreCount.add(1); // insert new genre entry to array, and set count to 1
    }

    // inherited methods
    public void listGenre() {
        for (int i = 0; i < genreList.size(); i++) {
            System.out.println(genreList.get(i) + ": " + genreCount.get(i));
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