package education.udacity.com.booklistingapp.service;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import education.udacity.com.booklistingapp.domain.Book;
import education.udacity.com.booklistingapp.domain.BookDTO;
import education.udacity.com.booklistingapp.processor.BookProcessor;

/**
 * Created by dhiegoabrantes on 20/09/16.
 */
public class BookService extends AsyncTask<Object, String, BookDTO> {

    private final String KEY = "AIzaSyBrNoCrM_ytnQ9qisA3Vi0p9NpKPxk-xhY";

    private AsyncTaskDelegator delegate = null;

    public BookService(Context context, AsyncTaskDelegator responder){
        this.delegate = responder;
    }

    @Override
    protected BookDTO doInBackground(Object... objects) {
        try {

            String keyword = objects[0].toString();
            String startIndex = objects[1].toString();
            String maxResults = objects[2].toString();

            String urlAPI = String.format("https://www.googleapis.com/books/v1/volumes?q=inauthor:%s&startIndex=%s&maxResults=%s&key=%s", keyword, startIndex, maxResults, KEY);

            URL url = new URL(urlAPI);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            InputStream is = connection.getInputStream();
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                return BookProcessor.process(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(BookDTO bookDTO) {
        super.onPostExecute(bookDTO);
        if(delegate != null)
            delegate.processFinish(bookDTO);
    }
}
