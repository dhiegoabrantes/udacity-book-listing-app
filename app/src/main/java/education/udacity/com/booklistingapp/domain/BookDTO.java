package education.udacity.com.booklistingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiegoabrantes on 24/09/16.
 */

public class BookDTO implements Parcelable {

    private int totalItems;
    private List<Book> books;


    private int currentPage;

    public BookDTO() {
        this.totalItems = 0;
        this.currentPage = 1;
        this.books = new ArrayList<>();
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int incrementCurrentPage(){
        return (this.currentPage++);
    }

    /*
     * Parcelable particular
     */

    public static final String PARCELABLE_KEY = "book_dto";

    public static final Parcelable.Creator<BookDTO> CREATOR
            = new Parcelable.Creator<BookDTO>() {
        public BookDTO createFromParcel(Parcel in) {
            return new BookDTO(in);
        }

        public BookDTO[] newArray(int size) {
            return new BookDTO[size];
        }
    };

    private BookDTO(Parcel in) {
        totalItems = in.readInt();
        currentPage = in.readInt();
        in.readTypedList(books, Book.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalItems);
        dest.writeInt(currentPage);
        dest.writeTypedList(books);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
