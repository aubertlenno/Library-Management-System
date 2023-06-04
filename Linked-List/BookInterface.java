import java.util.*;

public interface BookInterface {
    ArrayList<Book> library = new ArrayList<Book>();

    default void listTen() {
        if (library.size() >= 10) { // 
            for (int i = library.size() - 10; i < library.size(); i++) {    // if array size is greater than 10, print recent 10
                System.out.println(library.get(i).toString());
            }
        } else {
            for (int i = 0; i < library.size(); i++) {    // if array size is less than 10, print everything
                System.out.println(library.get(i).toString());
            }
        }
    }

    double totalCost();
    void listGenre();
}