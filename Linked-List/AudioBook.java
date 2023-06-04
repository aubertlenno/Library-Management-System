public class AudioBook extends Book {
    private double duration;
    private static double totalDuration, numOfAudio = 0;

    public AudioBook(String title, String author, String genre, double duration) {
        this.duration = duration;
        totalDuration += duration;
        numOfAudio++;

        super.setTitle(title);
        super.setAuthor(author);
        super.setGenre(genre);
        super.setCost(getCost());
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