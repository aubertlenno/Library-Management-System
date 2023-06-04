package src;

import java.util.*;

public interface BookInterface {
    TreeSet<Book> library = new TreeSet<>();

    static void listTen() {
        Iterator<Book> itr = library.iterator();
        int counter = 1;
        while (itr.hasNext() && counter<10) {
        System.out.println(itr.next());
           counter++;
        }
    }
}