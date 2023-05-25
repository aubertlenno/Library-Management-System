import java.util.*;

public interface BookInterface {
    HashMap<String, Book> library = new HashMap<String, Book>();

    default void listTen() {
        // extract values (Book instances) from the map into an ArrayList
        ArrayList<Book> bookList = new ArrayList<>(library.values());

        // calculate the starting index (if less than 10 books, start at 0, otherwise start at size-10)
        int start = Math.max(0, bookList.size() - 10);

        // print the last 10 (or fewer) books
        for (int i = start; i < bookList.size(); i++) {
            System.out.println(bookList.get(i).toString());
        }
    }

    void listGenre();
}