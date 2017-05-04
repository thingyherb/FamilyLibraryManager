package familylibrarymanager.zhao.com.familylibrarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.HashMap;
import java.util.List;
import familylibrarymanager.zhao.com.familylibrarymanager.adapter.BookAdapter;
import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.constant.SQLConstant;
import familylibrarymanager.zhao.com.familylibrarymanager.dao.LibraryDBDao;

public class SearchBookActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    private ListView lvBook;

    private List<Book> books;

    private BookAdapter bookAdapter;

    private HashMap<String, String>  searchMap;

    LibraryDBDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_list);
        mDao = new LibraryDBDao(this);
        Intent intent = this.getIntent();
        searchMap = getSearchCondition(intent);
        lvBook = (ListView) findViewById(R.id.lv_book_list);
        books = mDao.searchBooks(searchMap);
        bookAdapter = new BookAdapter(SearchBookActivity.this,books);
        lvBook.setAdapter(bookAdapter);
        lvBook.setOnItemClickListener(this);
        View searchBack = findViewById(R.id.search_back);
        searchBack.setOnClickListener(this);
    }


    /**
     * 获得搜索条件
     * @return
     */
    private HashMap<String, String> getSearchCondition(Intent intent) {
        HashMap<String, String> search = new HashMap<String, String>();
        String bookId = intent.getStringExtra("bookId");
        String bookname = intent.getStringExtra("bookname");
        String type = intent.getStringExtra("type");
        String author = intent.getStringExtra("author");
        String price = intent.getStringExtra("price");
        String borrower = intent.getStringExtra("borrower");
        String publicationDate = intent.getStringExtra("publicationDate");
        if(null != bookId && !"".equals(bookId)){
            search.put(SQLConstant.KEY_ID,bookId);
        }
        if(null != bookname && !"".equals(bookname)){
            search.put(SQLConstant.KEY_BOOK_NAME,bookname);
        }
        if(null != type && !"".equals(type)){
            search.put(SQLConstant.KEY_TYPE,type);
        }
        if(null != author && !"".equals(author)){
            search.put(SQLConstant.KEY_AUTHOR,author);
        }
        if(null != price && !"".equals(price)){
            search.put(SQLConstant.KEY_PRICE,price);
        }
        if(null != borrower && !"".equals(borrower)){
            search.put(SQLConstant.KEY_BORROWER,borrower);
        }
        if(null != publicationDate && !"".equals(publicationDate)){
            search.put(SQLConstant.KEY_PUBLICATION_DATE,publicationDate);
        }
        return search;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //@Todo
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookId", books.get(position).getBookname());
        intent.putExtras(bundle);
        this.startActivity(intent);
        //Toast.makeText(SearchBookActivity.this,books.get(position).getBookname(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
