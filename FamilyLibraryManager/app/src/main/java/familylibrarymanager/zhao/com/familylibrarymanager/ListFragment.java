package familylibrarymanager.zhao.com.familylibrarymanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.dao.LibraryDBDao;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    //数据库操作变量
    private LibraryDBDao mDao;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListFragment.
     */
    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        traverseData();
    }

    //test 可删除
    private void traverseData(){
        List<Book> booklist = mDao.queryAllBookList();
        Log.d(TAG, "size:"+(booklist==null?0:booklist.size()));
        if(booklist!=null) {
            for (Book book : booklist) {
                printfBook(book);
            }
        }
    }
    private String TAG = "test";

    //test 可删除
    private void printfBook(Book book){
        Log.d(TAG, "id:"+book.getId());
        Log.d(TAG, "name:"+book.getBookname());
        Log.d(TAG, "author:"+book.getAuthor());
        Log.d(TAG, "borrower:"+book.getBorrower());
        Log.d(TAG, "type:"+book.getType());
        Log.d(TAG, "price:"+book.getPrice());
        Log.d(TAG, "publicationdate:"+book.getPublicationDate());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

}
