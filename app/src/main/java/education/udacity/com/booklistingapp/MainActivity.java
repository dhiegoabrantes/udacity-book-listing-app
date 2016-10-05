package education.udacity.com.booklistingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import education.udacity.com.booklistingapp.adapter.BookAdapter;
import education.udacity.com.booklistingapp.domain.Book;
import education.udacity.com.booklistingapp.domain.BookDTO;
import education.udacity.com.booklistingapp.service.AsyncTaskDelegator;
import education.udacity.com.booklistingapp.service.BookService;
import education.udacity.com.booklistingapp.ui.recycler.DividerItemDecoration;
import education.udacity.com.booklistingapp.ui.recycler.OnItemClickListener;
import education.udacity.com.booklistingapp.util.Utils;

public class MainActivity extends AppCompatActivity implements AsyncTaskDelegator {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private BookAdapter mAdapter;
    private BookDTO bookDTO;

    private boolean loading = false;
    private static final int PAGE_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText keyword = (EditText) findViewById(R.id.bookTitleEditText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewBooks);

        bookDTO = new BookDTO();

        this.mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(this.mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int pastVisiblesItems;
            int visibleItemCount;
            int totalItemCount;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //verificando se houve scrolling
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (!loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount && totalItemCount < bookDTO.getTotalItems())
                        {
                            loading = true;
                            getBooks(recyclerView.getContext(), keyword.getText().toString(), (bookDTO.incrementCurrentPage() * PAGE_SIZE), PAGE_SIZE);
                        }
                    }
                }
            }
        });


        Button findButton = (Button) findViewById(R.id.findButton);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String keywordStr = keyword.getText().toString();
                if(keywordStr != null && keywordStr.length() > 0){
                    bookDTO = new BookDTO();
                    setupRecyclerAdapter(bookDTO.getBooks());
                    getBooks(v.getContext(), keywordStr, 0, PAGE_SIZE);
                }else{
                    Toast.makeText(MainActivity.this, R.string.enter_book_title, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BookDTO.PARCELABLE_KEY, this.bookDTO);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.bookDTO = savedInstanceState.getParcelable(BookDTO.PARCELABLE_KEY);
        setupRecyclerAdapter(this.bookDTO.getBooks());
    }

    @Override
    public void processFinish(Object obj) {
        if(obj != null){

            //Seria mais simples associar o 'obj' diretamente à variável 'bookDTO'.
            //No entanto, perderíamos performance pois não seria possível carregar página por página, sendo necessário carregar as páginas de forma acumulativa.
            //Por exemplo: Carregar página 1 (30 itens); No momento de carregar a página 2, teria que carregar 60 itens (30 da 1a + 30 da 2a).
            //Da forma como está implementado, são carregados os ítens apenas da página atual e estes são concatenados com os itens já carregados anteriormente.
            //COOOOOL!
            BookDTO bookDTOResult = (BookDTO) obj;

            this.bookDTO.setTotalItems( bookDTOResult.getTotalItems() );
            this.bookDTO.getBooks().addAll( bookDTOResult.getBooks() );
            mAdapter.notifyDataSetChanged();
            loading = false;
        }
    }

    private void getBooks(Context ctx, String keyword, int startIndex, int maxResults){
        if(Utils.checkConn(ctx)) {
            BookService service = new BookService(MainActivity.this, MainActivity.this);
            service.execute(keyword, startIndex, maxResults);
        }
        else{
            Toast.makeText(ctx, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void setupRecyclerAdapter(List<Book> books){
        mAdapter = new BookAdapter(books);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
