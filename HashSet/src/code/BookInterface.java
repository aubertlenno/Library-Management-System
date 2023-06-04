package code;

import java.util.*;

public interface BookInterface {
    HashSet<Book> library = new HashSet<>();

    default void listTen() {
        // Convert the HashSet to an ArrayList
        ArrayList<Book> bookList = new ArrayList<>(library);

        // calculate the maximum number of books to list (up to 10)
        int maxBooks = Math.min(10, bookList.size());

        // print the specified number of books
        for (int i = 0; i < maxBooks; i++) {
            System.out.println(bookList.get(i).toString());
        }
    }
}
