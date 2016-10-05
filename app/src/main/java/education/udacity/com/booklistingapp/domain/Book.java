package education.udacity.com.booklistingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhiegoabrantes on 19/09/16.
 */
public class Book implements Parcelable{

    private String id;
    private String title;
    private String publisher;
    private String description;
    private String pageCount;
    private String thumbnail;
    private List<Author> authors;

    public Book() {
        this.id = "";
        this.title = "";
        this.publisher = "";
        this.description = "";
        this.pageCount = "";
        this.thumbnail = "";
        this.authors = new ArrayList<>();
    }

    public Book(String id, String title, String publisher, String description, String pageCount, String thumbnail, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.authors = authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id != null)
            this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title != null)
            this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if(publisher != null)
            this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description != null)
            this.description = description;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        if(pageCount != null)
            this.pageCount = pageCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        if(thumbnail != null)
            this.thumbnail = thumbnail;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        if(authors != null)
            this.authors = authors;
    }

/*
     * Parcelable particular
     */

    public static final String PARCELABLE_KEY = "book";

    public static final Parcelable.Creator<Book> CREATOR
            = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        publisher = in.readString();
        description = in.readString();
        pageCount = in.readString();
        thumbnail = in.readString();
        in.readTypedList(authors, Author.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeString(description);
        dest.writeString(pageCount);
        dest.writeString(thumbnail);
        dest.writeTypedList(authors);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
