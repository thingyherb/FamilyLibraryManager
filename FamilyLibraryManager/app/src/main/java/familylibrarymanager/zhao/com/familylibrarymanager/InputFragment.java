package familylibrarymanager.zhao.com.familylibrarymanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.constant.SQLConstant;
import familylibrarymanager.zhao.com.familylibrarymanager.dao.LibraryDBDao;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private EditText bookNumberEditText;
    private EditText bookNameEditText;
    private EditText bookAuthorEditText;
    private EditText bookTypeEditText;
    private EditText bookDateEditText;
    private EditText bookPriceEditText;
    private EditText bookBorrowerEditText;

    private Calendar showDate;
    private LibraryDBDao mDao;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InputFragment.
     */
    public static InputFragment newInstance() {
        InputFragment fragment = new InputFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener!= null) {
            mDao = mListener.getDao();
        }
        showDate = Calendar.getInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        initView(view);
        String newKeyId=mDao.getNotOccupiedKeyId();
        bookNumberEditText.setText(newKeyId);
        bookNumberEditText.setFocusable(false);
        return view;
    }

    private void initView(View view) {
        bookNumberEditText = (EditText) view.findViewById(R.id.bookNumberEditText);
        bookNameEditText = (EditText) view.findViewById(R.id.bookNameEditText);
        bookAuthorEditText = (EditText) view.findViewById(R.id.bookAuthorEditText);
        bookTypeEditText = (EditText) view.findViewById(R.id.bookTypeEditText);
        bookDateEditText = (EditText) view.findViewById(R.id.bookDateEditText);
        bookPriceEditText = (EditText) view.findViewById(R.id.bookPriceEditText);
        bookBorrowerEditText = (EditText) view.findViewById(R.id.bookBorrowerEditText);
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
        View inputButton = view.findViewById(R.id.inputButton);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInput();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 点击确认
     *
     */
    public void onClickInput() {
        if(!checkNotNull()){
            Toast.makeText(getActivity(), "请检查数据，字段不能为空", Toast.LENGTH_SHORT).show();
        }
        else {
            Book book = makeBook();
            if(mDao.searchBooks(SQLConstant.KEY_ID, String.valueOf(book.getId()))!=null){
                Toast.makeText(getActivity(), "图书编号已被占用，请修改！！！", Toast.LENGTH_SHORT).show();
            }
            else {
                if (mDao.addBook(book)) {
                    Toast.makeText(getActivity(), "添加图书成功！！！", Toast.LENGTH_SHORT).show();
                    String newKeyId=mDao.getNotOccupiedKeyId();
                    bookNumberEditText.setText(newKeyId);
                } else {
                    Toast.makeText(getActivity(), "添加图书失败！！！", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
                    Integer.parseInt(split[1]), Integer.parseInt(split[2]));
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
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showDate.set(Calendar.YEAR, year);
                showDate.set(Calendar.MONTH, monthOfYear);
                showDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                bookDateEditText.setText(DateFormat.format("yyyy-MM-dd", showDate));
            }
        }, showDate.get(Calendar.YEAR), showDate.get(Calendar.MONTH), showDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 检测edittext文本是否为非空
     * @return
     */
    private boolean checkNotNull(){
        boolean isNotNull = true;
        if(TextUtils.isEmpty(bookNumberEditText.getText().toString())){
            isNotNull = false;
        }
        else if(TextUtils.isEmpty(bookNameEditText.getText().toString())){
            isNotNull = false;
        }
        else if(TextUtils.isEmpty(bookAuthorEditText.getText().toString())){
            isNotNull = false;
        }
        else if(TextUtils.isEmpty(bookTypeEditText.getText().toString())){
            isNotNull = false;
        }
        else if(TextUtils.isEmpty(bookDateEditText.getText().toString())){
            isNotNull = false;
        }
        else if(TextUtils.isEmpty(bookPriceEditText.getText().toString())){
            isNotNull = false;
        }
        return isNotNull;
    }

    /**
     * 构建book对象
     * @return
     */
    private Book makeBook(){
        Book book = new Book();
        book.setId(bookNumberEditText.getText().toString());
        book.setBookname(bookNameEditText.getText().toString());
        book.setAuthor(bookAuthorEditText.getText().toString());
        book.setType(bookTypeEditText.getText().toString());
        book.setPublicationDate(bookDateEditText.getText().toString());
        book.setPrice(Double.parseDouble(bookPriceEditText.getText().toString()));
        if(bookBorrowerEditText.getText()!=null) {
            book.setBorrower(bookBorrowerEditText.getText().toString());
        }
        return book;
    }
}
