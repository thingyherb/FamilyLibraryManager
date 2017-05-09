package familylibrarymanager.zhao.com.familylibrarymanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
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

import familylibrarymanager.zhao.com.familylibrarymanager.constant.SQLConstant;
import familylibrarymanager.zhao.com.familylibrarymanager.dao.LibraryDBDao;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
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
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDate = Calendar.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        bookNumberEditText = (EditText) view.findViewById(R.id.bookNumberSearchText);
        bookNameEditText = (EditText) view.findViewById(R.id.bookNameSearchText);
        bookAuthorEditText = (EditText) view.findViewById(R.id.bookAuthorSearchText);
        bookTypeEditText = (EditText) view.findViewById(R.id.bookTypeSearchText);
        bookDateEditText = (EditText) view.findViewById(R.id.bookDateSearchText);
        bookPriceEditText = (EditText) view.findViewById(R.id.bookPriceSearchText);
        bookBorrowerEditText = (EditText) view.findViewById(R.id.bookBorrowerSearchText);
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
        View searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearch();
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
     * 搜索按钮点击事件
     */
    public void onClickSearch(){
        Intent intent = new Intent();
        //图书编号
        if(!TextUtils.isEmpty(bookNumberEditText.getText().toString()))
            intent.putExtra(SQLConstant.KEY_ID,bookNumberEditText.getText().toString());
        //图书名称
        if(!TextUtils.isEmpty(bookNameEditText.getText().toString()))
            intent.putExtra(SQLConstant.KEY_BOOK_NAME, bookNameEditText.getText().toString());
        //作者
        if(!TextUtils.isEmpty(bookAuthorEditText.getText().toString()))
            intent.putExtra(SQLConstant.KEY_AUTHOR,bookAuthorEditText.getText().toString());
        //类型
        if(!TextUtils.isEmpty(bookTypeEditText.getText().toString()))
            intent.putExtra(SQLConstant.KEY_TYPE,bookTypeEditText.getText().toString());
        //出版日期
        if(!TextUtils.isEmpty(bookDateEditText.getText().toString()))
            intent.putExtra(SQLConstant.KEY_PUBLICATION_DATE,bookDateEditText.getText().toString());
        //单价Decimal
        if(!TextUtils.isEmpty(bookPriceEditText.getText().toString()))
            intent.putExtra(SQLConstant.KEY_PRICE,bookPriceEditText.getText().toString());
        //借阅人
        if(!TextUtils.isEmpty(bookBorrowerEditText.getText().toString()))
            intent.putExtra(SQLConstant.KEY_BORROWER,bookBorrowerEditText.getText().toString());

        if (intent!=null && intent.getExtras() != null) {
            intent.setClass(getActivity().getApplicationContext(),SearchBookActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(), "请输入至少一个搜索信息", Toast.LENGTH_SHORT).show();
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
