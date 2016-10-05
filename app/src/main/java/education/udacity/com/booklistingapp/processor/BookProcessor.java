package education.udacity.com.booklistingapp.processor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import education.udacity.com.booklistingapp.domain.Author;
import education.udacity.com.booklistingapp.domain.Book;
import education.udacity.com.booklistingapp.domain.BookDTO;

/**
 * Created by dhiegoabrantes on 20/09/16.
 */
public class BookProcessor {

    public static BookDTO process(String input) {

        BookDTO bookDTO = new BookDTO();

        Integer totalItems = 0;
        List<Book> books = new ArrayList<>();
        try {
            if(input != null && input.startsWith("{")){
                JSONObject parentObject = new JSONObject(input);

                totalItems = parentObject.optInt("totalItems", 0);
                JSONArray jsonArray = (JSONArray) parentObject.get("items");

                if( jsonArray.length() > 0 ){
                    books = new ArrayList<>();

                    Book book;
                    List<Author> authors;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        book = new Book();
                        authors = new ArrayList<>();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        book.setId(jsonObject.optString("id"));
                        JSONObject volumeInfo = jsonObject.optJSONObject("volumeInfo");

                        if(volumeInfo != null){
                            book.setTitle( volumeInfo.optString("title") );
                            book.setPublisher( volumeInfo.optString("publisher") );
                            book.setDescription( volumeInfo.optString("description") );
                            book.setPageCount( volumeInfo.optString("pageCount") );


                            JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                            if(imageLinks != null){
                                book.setThumbnail( imageLinks.optString("thumbnail") );

                                if(book.getThumbnail() == null)
                                    book.setThumbnail( imageLinks.optString("smallThumbnail") );
                            }


                            JSONArray authorsJson = volumeInfo.optJSONArray("authors");

                            if(authorsJson != null){
                                for (int j = 0; j < authorsJson.length(); j++) {
                                    authors.add(new Author( authorsJson.get(j).toString() ));
                                }
                            }

                            book.setAuthors(authors);
                        }

                        books.add(book);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        bookDTO.setTotalItems(totalItems);
        bookDTO.setBooks(books);
        return bookDTO;
    }

}
