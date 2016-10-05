package education.udacity.com.booklistingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dhiegoabrantes on 19/09/16.
 */
public class Author implements Parcelable {

    private String name;

    public Author(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
     * Parcelable particular
     */

    public static final String PARCELABLE_KEY = "author";

    public static final Parcelable.Creator<Author> CREATOR
            = new Parcelable.Creator<Author>() {
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    private Author(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
