package familylibrarymanager.zhao.com.familylibrarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.dao.LibraryDBDao;

import static familylibrarymanager.zhao.com.familylibrarymanager.R.id.bookAuthorEditText;
import static familylibrarymanager.zhao.com.familylibrarymanager.R.id.bookDateEditText;
import static familylibrarymanager.zhao.com.familylibrarymanager.R.id.bookNameEditText;
import static familylibrarymanager.zhao.com.familylibrarymanager.R.id.bookNumberEditText;
import static familylibrarymanager.zhao.com.familylibrarymanager.R.id.bookPriceEditText;
import static familylibrarymanager.zhao.com.familylibrarymanager.R.id.bookTypeEditText;

/**
 * Created by linmaosheng on 2017/5/6.
 */

public class DetailsActivity extends AppCompatActivity {
    LibraryDBDao mDao;
    private TextView dateEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Intent intent = getIntent();
        // 拿书
        getBook(intent);

//        // 日期校验
//        dateEdit = (TextView) findViewById(R.id.bookDateEditText);
//        dateEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(DetailsActivity.this, "日期", Toast.LENGTH_SHORT).show();
//                String showDate = dateEdit.getText().toString();
//                new DatePickerDialog(DetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        final String showDate = dateEdit.getText().toString();
//                        showDate.set(Calendar.YEAR, year);
//                        showDate.set(Calendar.MONTH, monthOfYear);
//                        showDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        bookDateEditText.setText(DateFormat.format("yyyy-MM-dd", showDate));
//                    }
//                }, showDate.get(Calendar.YEAR), showDate.get(Calendar.MONTH), showDate.get(Calendar.DAY_OF_MONTH)).show();
//
//            }
//        });

        // 更新
        Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                // 校验
                if(!checkNotNull()){
                    Toast.makeText(DetailsActivity.this, "请检查数据，字段不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    updateBook(intent);
                    finish();
                }

                // 返回刷新上一页???

            }
        });

        // 删除
        Button removeButton = (Button) findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                removeBook();

                // 返回刷新上一页???

                finish();
            }
        });
    }

    private void getBook(Intent intent) {
        String bookId = intent.getStringExtra("bookId");
        Double price = intent.getDoubleExtra("price", 0.00);
        TextView bookIdTextView = (TextView) findViewById(bookNumberEditText);
        bookIdTextView.setText(bookId);bookIdTextView.setFocusable(false);
        TextView bookNameTextView = (TextView) findViewById(bookNameEditText);
        bookNameTextView.setText(intent.getStringExtra("bookName"));
        TextView typeTextView = (TextView) findViewById(bookTypeEditText);
        typeTextView.setText(intent.getStringExtra("type"));
        TextView authorTextView = (TextView) findViewById(bookAuthorEditText);
        authorTextView.setText(intent.getStringExtra("author"));
        TextView priceTextView = (TextView) findViewById(bookPriceEditText);
        priceTextView.setText(price.toString());
        TextView borrowerTextView = (TextView) findViewById(R.id.bookBorrowerEditText);
        borrowerTextView.setText(intent.getStringExtra("borrower"));
        TextView publicationDateTextView = (TextView) findViewById(bookDateEditText);
        publicationDateTextView.setText(intent.getStringExtra("publicationDate"));
//        Toast.makeText(DetailsActivity.this, "bookId="+bookId, Toast.LENGTH_SHORT).show();
    }

    private void updateBook(Intent intent) {
        mDao = new LibraryDBDao(this);
        TextView bookIdTextView = (TextView) findViewById(bookNumberEditText);
        String bookId = bookIdTextView.getText().toString();
        TextView bookNameTextView = (TextView) findViewById(bookNameEditText);
        TextView typeTextView = (TextView) findViewById(bookTypeEditText);
        TextView authorTextView = (TextView) findViewById(bookAuthorEditText);
        TextView priceTextView = (TextView) findViewById(bookPriceEditText);
        TextView borrowerTextView = (TextView) findViewById(R.id.bookBorrowerEditText);
        TextView publicationDateTextView = (TextView) findViewById(bookDateEditText);

//        Toast.makeText(DetailsActivity.this, "更新bookId="+bookId+",bookName="+bookName, Toast.LENGTH_SHORT).show();
        Book book = new Book();
        book.setId(bookId);
        book.setBookname(bookNameTextView.getText().toString());
        book.setType(typeTextView.getText().toString());
        book.setAuthor(authorTextView.getText().toString());
        book.setPrice(Double.valueOf(priceTextView.getText().toString()));
        book.setBorrower(borrowerTextView.getText().toString());
        book.setPublicationDate(publicationDateTextView.getText().toString());
        mDao.updateBookInfo(bookId, book);
        Toast.makeText(DetailsActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
    }

    private void removeBook() {
        mDao = new LibraryDBDao(this);
        TextView bookIdTextView = (TextView) findViewById(bookNumberEditText);
        String bookId = bookIdTextView.getText().toString();
        mDao.removeBook(Integer.valueOf(bookId));
        Toast.makeText(DetailsActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    private boolean checkNotNull(){
        boolean isNotNull = true;
        if(TextUtils.isEmpty(((TextView) findViewById(bookNumberEditText)).getText())){
            isNotNull = false;
        } else if(TextUtils.isEmpty(((TextView) findViewById(bookNameEditText)).getText())){
            isNotNull = false;
        } else if(TextUtils.isEmpty(((TextView) findViewById(bookTypeEditText)).getText())){
            isNotNull = false;
        } else if(TextUtils.isEmpty(((TextView) findViewById(bookAuthorEditText)).getText())){
            isNotNull = false;
        } else if(TextUtils.isEmpty(((TextView) findViewById(bookPriceEditText)).getText())){
            isNotNull = false;
        } else if(TextUtils.isEmpty(((TextView) findViewById(R.id.bookBorrowerEditText)).getText())){
            isNotNull = false;
        } else if(TextUtils.isEmpty(((TextView) findViewById(R.id.bookDateEditText)).getText())){
            isNotNull = false;
        }
        return isNotNull;
    }
}
