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

    @Override
    protected void onRestart() {
        super.onRestart();
        books = mDao.searchBooks(searchMap);
        bookAdapter = new BookAdapter(SearchBookActivity.this,books);
        lvBook.setAdapter(bookAdapter);
    }

    /**
     * 获得搜索条件
     * @return
     */
    private HashMap<String, String> getSearchCondition(Intent intent) {
        HashMap<String, String> search = new HashMap<String, String>();
        String bookId = intent.getStringExtra("id");
        String bookname = intent.getStringExtra("bookname");
        String type = intent.getStringExtra("type");
        String author = intent.getStringExtra("author");
        String price = intent.getStringExtra("price");
        String borrower = intent.getStringExtra("borrower");
        String publicationDate = intent.getStringExtra("publication_date");
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

    // 详情
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookId", books.get(position).getId());
        bundle.putSerializable("bookName", books.get(position).getBookname());
        bundle.putSerializable("type", books.get(position).getType());
        bundle.putSerializable("author", books.get(position).getAuthor());
        bundle.putSerializable("price", books.get(position).getPrice());
        bundle.putSerializable("borrower", books.get(position).getBorrower());
        bundle.putSerializable("publicationDate", books.get(position).getPublicationDate());
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
