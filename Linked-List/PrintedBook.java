public class PrintedBook extends Book {
    private int page;
    private static double totalPage, numOfPrinted = 0;

    public PrintedBook(String title, String author, String genre, int page) {
        this.page = page;
        totalPage += page;
        numOfPrinted++;

        super.setTitle(title);
        super.setAuthor(author);
        super.setGenre(genre);
        super.setCost(getCost());
    }

    public double getCost() {
        return page * 0.25;
    }

    public static double getAvg() {
        return totalPage * 0.25 / numOfPrinted;
    }

    public String toString() {
        return "P" + ";" + super.toString() + ";" + page;
    }
}