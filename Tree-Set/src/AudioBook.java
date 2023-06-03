package src;

public class AudioBook extends Book {
    private double duration;
    private static double totalDuration, numOfAudio = 0;

    public AudioBook(String title, String author, String genre, double duration, String synopsis) {
        this.duration = duration;
        totalDuration += duration;
        numOfAudio++;

        super.setTitle(title);
        super.setAuthor(author);
        super.setGenre(genre);
        super.setCost(getCost());
        super.setSynopsis(synopsis);
        super.setType("Audio book");
    }

    public double getCost() {
        return duration * 700;
    }

    public static double getAvg() {
        return totalDuration * 700 / numOfAudio;
    }

    public String toString() {
        return "A" + ";" + super.toString() + ";" + duration;
    }
}