package education.udacity.com.booklistingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import education.udacity.com.booklistingapp.R;
import education.udacity.com.booklistingapp.domain.Author;
import education.udacity.com.booklistingapp.domain.Book;
import education.udacity.com.booklistingapp.ui.recycler.OnItemClickListener;

/**
 * Created by dhiegoabrantes on 23/09/16.
 */
public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private static final int EMPTY_VIEW = 10;

    private final List<Book> books;

    public BookAdapter(List<Book> books) {
        this.books = books != null ? books : new ArrayList<Book>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            EmptyViewHolder evh = new EmptyViewHolder(v);
            return evh;
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BookViewHolder){
            BookViewHolder bookViewHolder = (BookViewHolder) holder;
            Book book = this.books.get(position);
            bookViewHolder.bookTitle.setText(book.getTitle());

            StringBuilder authors = new StringBuilder();
            for (int i = 0; i < book.getAuthors().size(); i++) {
                authors.append(book.getAuthors().get(i).getName());

                if(i < book.getAuthors().size()-1)
                    authors.append(", ");
            }

            bookViewHolder.authors.setText( authors.toString() );

            Glide.with(bookViewHolder.context)
                    .load(book.getThumbnail())
                    .fitCenter()
                    .into(bookViewHolder.thumbnail);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if(this.books != null)
            return this.books.size() > 0 ? this.books.size() : 1;
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (this.books.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        public Context context;
        public TextView bookTitle;
        public TextView authors;
        public ImageView thumbnail;

        public BookViewHolder(View view) {
            super(view);
            context = view.getContext();
            bookTitle = (TextView) view.findViewById(R.id.bookTitle);
            authors = (TextView) view.findViewById(R.id.authors);
            thumbnail = (ImageView) view.findViewById(R.id.bookThumbnail);

        }
    }
}
