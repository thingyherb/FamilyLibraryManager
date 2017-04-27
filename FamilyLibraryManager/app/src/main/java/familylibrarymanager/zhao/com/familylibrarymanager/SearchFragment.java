package familylibrarymanager.zhao.com.familylibrarymanager;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Fragment;

import java.sql.Date;

import familylibrarymanager.zhao.com.familylibrarymanager.constant.IntentConstant;
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
    public static SearchFragment newInstance(LibraryDBDao dao) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putSerializable(IntentConstant.INTENT_DAO, dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDao = (LibraryDBDao) getArguments().getSerializable(IntentConstant.INTENT_DAO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button searchButton = (Button)getActivity().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框内容，全部为空提示异常
                //图书编号
                EditText bookid = (EditText)getActivity().findViewById(R.id.bookNumberEditText);
                //图书名称
                EditText bookname = (EditText)getActivity().findViewById(R.id.bookNameEditText);
                String name = bookname.getText().toString();
                //作者
                EditText bookauth = (EditText)getActivity().findViewById(R.id.bookAuthorEditText);
                String auth = bookauth.getText().toString();
                //类型
                EditText booktype = (EditText)getActivity().findViewById(R.id.bookTypeEditText);
                String type = booktype.getText().toString();
                //出版日期
                EditText bookdate = (EditText) getActivity().findViewById(R.id.bookDateEditText);
                //Date.parse(bookdate.getText().toString());
                //单价Decimal
                EditText bookprice= (EditText)getActivity().findViewById(R.id.bookPriceEditText);
                String priceStr = bookprice.getText().toString();
                Double price;
                if("".equals(priceStr)){
                    price = 0.0;
                }else{
                    price = Double.valueOf(priceStr);
                }

                //借阅人
                EditText bookborrower= (EditText)getActivity().findViewById(R.id.bookBorrowerEditText);
                String borrower = bookborrower.getText().toString();

                //根据输入框到库中查询

                //返回结果
                Toast.makeText(getActivity(), borrower, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), price.toString(), Toast.LENGTH_SHORT).show();


                Toast.makeText(getActivity(), "搜索00成功", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
