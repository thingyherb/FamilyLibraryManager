package familylibrarymanager.zhao.com.familylibrarymanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.dao.LibraryDBDao;

import static familylibrarymanager.zhao.com.familylibrarymanager.R.id.bookAuthorEditText;
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
    private EditText bookDateEditText;
    private Calendar showDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        Intent intent = getIntent();
        // 拿书
        getBook(intent);
        showDate = Calendar.getInstance();
        bookDateEditText = (EditText)this.findViewById(R.id.bookDateEditText);
        bookDateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    onClickPublicationTime();
                    return true;
                } else {
                    return false;
                }
            }
        });
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
        findViewById(R.id.iv_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 点击选择出版时间
     *
     */
    public void onClickPublicationTime() {
        String publicationDateStr = bookDateEditText.getText().toString();
        if (!TextUtils.isEmpty(publicationDateStr)&&publicationDateStr.contains("-")) {
            String[] split = publicationDateStr.split("-");
            showDate.set(Integer.parseInt(split[0]),
                    Integer.parseInt(split[1])-1, Integer.parseInt(split[2]));
        }
        else{
            showDate.setTimeInMillis(System.currentTimeMillis());
        }
        showDatePickerDialog();
    }

    /**
     * 显示时间捡取器
     */
    private void showDatePickerDialog() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showDate.set(Calendar.YEAR, year);
                showDate.set(Calendar.MONTH, monthOfYear);
                showDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                bookDateEditText.setText(DateFormat.format("yyyy-MM-dd", showDate));
            }
        }, showDate.get(Calendar.YEAR), showDate.get(Calendar.MONTH), showDate.get(Calendar.DAY_OF_MONTH)).show();
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
        TextView publicationDateTextView = (TextView) findViewById(R.id.bookDateEditText);
        publicationDateTextView.setText(intent.getStringExtra("publicationDate"));
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
        TextView publicationDateTextView = (TextView) findViewById(R.id.bookDateEditText);
        // new book
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
        }
//        else if(TextUtils.isEmpty(((TextView) findViewById(R.id.bookBorrowerEditText)).getText())){
//            isNotNull = false;
//        }
        else if(TextUtils.isEmpty(((TextView) findViewById(R.id.bookDateEditText)).getText())){
            isNotNull = false;
        }
        return isNotNull;
    }
}
