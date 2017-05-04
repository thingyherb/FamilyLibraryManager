package familylibrarymanager.zhao.com.familylibrarymanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import familylibrarymanager.zhao.com.familylibrarymanager.dao.LibraryDBDao;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private EditText bookNumberSearchText;
    private EditText bookNameSearchText;
    private EditText bookAuthorSearchText;
    private EditText bookTypeSearchText;
    private EditText bookDateSearchText;
    private EditText bookPriceSearchText;
    private EditText bookBorrowerSearchText;

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
        if (mListener != null) {
            mDao = mListener.getDao();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        bookNumberSearchText = (EditText) view.findViewById(R.id.bookNumberSearchText);
        bookNameSearchText = (EditText) view.findViewById(R.id.bookNameSearchText);
        bookAuthorSearchText = (EditText) view.findViewById(R.id.bookAuthorSearchText);
        bookTypeSearchText = (EditText) view.findViewById(R.id.bookTypeSearchText);
        bookDateSearchText = (EditText) view.findViewById(R.id.bookDateSearchText);
        bookPriceSearchText = (EditText) view.findViewById(R.id.bookPriceSearchText);
        bookBorrowerSearchText = (EditText) view.findViewById(R.id.bookBorrowerSearchText);
        View searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearch();
            }
        });
    }

    /**
     * 点击搜索按钮
     *
     */
    public void onClickSearch() {
        Intent intent = new Intent(getActivity(), SearchBookActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookId", bookNumberSearchText.getText().toString());
        bundle.putSerializable("bookname", bookNameSearchText.getText().toString());
        bundle.putSerializable("type", bookTypeSearchText.getText().toString());
        bundle.putSerializable("author", bookAuthorSearchText.getText().toString());
        bundle.putSerializable("price", bookPriceSearchText.getText().toString());
        bundle.putSerializable("borrower", bookBorrowerSearchText.getText().toString());
        bundle.putSerializable("publicationDate", bookDateSearchText.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof familylibrarymanager.zhao.com.familylibrarymanager.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
