    package src;

    public class PrintedBook extends Book {
        private int page;
        private static double totalPage, numOfPrinted = 0;

        public PrintedBook(String title, String author, String genre, int page, String synopsis) {
            this.page = page;
            totalPage += page;
            numOfPrinted++;

            super.setTitle(title);
            super.setAuthor(author);
            super.setGenre(genre);
            super.setCost(getCost());
            super.setSynopsis(synopsis);
            super.setType("Printed book");
        }

        public double getCost() {
            return page * 1000;
        }

        public static double getAvg() {
            return totalPage * 1000 / numOfPrinted;
        }

        public String toString() {
            return "P" + ";" + super.toString() + ";" + page;
        }
    }