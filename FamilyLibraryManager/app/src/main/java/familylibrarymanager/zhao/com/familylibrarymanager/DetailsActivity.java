package familylibrarymanager.zhao.com.familylibrarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.dao.LibraryDBDao;

/**
 * Created by linmaosheng on 2017/5/6.
 */

public class DetailsActivity extends AppCompatActivity {

    LibraryDBDao mDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.details);

        Intent intent = getIntent();
        // 拿书
        getBook(intent);

        // 更新
        Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                updateBook(intent);
//                finish();
            }
        });

        // 删除
        Button removeButton = (Button) findViewById(R.id.removeButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                removeBook(intent);
//                finish();
            }
        });
    }

    private void getBook(Intent intent) {
        String bookId = intent.getStringExtra("bookId");
        Double price = intent.getDoubleExtra("price", 0.00);
        TextView bookIdTextView = (TextView) findViewById(R.id.bookNumberEditText);
        bookIdTextView.setText(bookId);bookIdTextView.setFocusable(false);
        TextView bookNameTextView = (TextView) findViewById(R.id.bookNameEditText);
        bookNameTextView.setText(intent.getStringExtra("bookName"));
        TextView typeTextView = (TextView) findViewById(R.id.bookTypeEditText);
        typeTextView.setText(intent.getStringExtra("type"));
        TextView authorTextView = (TextView) findViewById(R.id.bookAuthorEditText);
        authorTextView.setText(intent.getStringExtra("author"));
        TextView priceTextView = (TextView) findViewById(R.id.bookPriceEditText);
        priceTextView.setText(price.toString());
        TextView borrowerTextView = (TextView) findViewById(R.id.bookBorrowerEditText);
        borrowerTextView.setText(intent.getStringExtra("borrower"));
        TextView publicationDateTextView = (TextView) findViewById(R.id.bookDateEditText);
        publicationDateTextView.setText(intent.getStringExtra("publicationDate"));
//        Toast.makeText(DetailsActivity.this, "bookId="+bookId, Toast.LENGTH_SHORT).show();
        //Log.e("DetailsActivity", bookId);
    }

    private void updateBook(Intent intent) {
        mDao = new LibraryDBDao(this);
        TextView bookIdTextView = (TextView) findViewById(R.id.bookNumberEditText);
        String bookId = bookIdTextView.getText().toString();
        TextView bookNameTextView = (TextView) findViewById(R.id.bookNameEditText);
        String bookName = bookNameTextView.getText().toString();
        Toast.makeText(DetailsActivity.this, "更新bookId="+bookId+",bookName="+bookName, Toast.LENGTH_SHORT).show();
        Book book = new Book();
        book.setId(bookId);
        book.setBookname(bookName);
                mDao.updateBookInfo(bookId, book);
//                Toast.makeText(DetailsActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
    }

    private void removeBook(Intent intent) {
        mDao = new LibraryDBDao(this);
        TextView bookIdTextView = (TextView) findViewById(R.id.bookNumberEditText);
        String bookId = bookIdTextView.getText().toString();
        mDao.removeBook(Integer.valueOf(bookId));
    }
}
