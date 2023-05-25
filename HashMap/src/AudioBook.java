public class AudioBook extends Book {
    private double duration;
    private static double totalDuration, numOfAudio = 0;

    public AudioBook(String title, String author, String genre, double duration) {
        super.setTitle(title);
        super.setAuthor(author);
        super.setGenre(genre);

        this.duration = duration;
        totalDuration += duration;
        numOfAudio++;

        this.cost = getCost();  // Set cost directly here
    }

    public double getCost() {
        return duration * 0.125;
    }

    public static double getAvg() {
        return totalDuration * 0.125 / numOfAudio;
    }

    public String toString() {
        return "A" + ";" + super.toString() + ";" + duration;
    }
}