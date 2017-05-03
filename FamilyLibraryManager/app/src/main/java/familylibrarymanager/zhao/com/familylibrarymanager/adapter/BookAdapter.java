package familylibrarymanager.zhao.com.familylibrarymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import familylibrarymanager.zhao.com.familylibrarymanager.R;
import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;

/**
 * Created by admin on 2017/4/28.
 */

public class BookAdapter extends BaseAdapter {

    private List<Book> bookList;

    private LayoutInflater layoutInflater;

    public BookAdapter(Context context,List<Book> bookList){
        this.bookList = bookList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookList != null && !bookList.isEmpty() ? bookList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return bookList != null && !bookList.isEmpty() ? bookList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.fragment_search_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book book = bookList.get(position);

        if (book != null){
            holder.tvName.setText(book.getBookname());
            holder.tvAuthor.setText("作者:"+book.getAuthor());
            holder.tvId.setText("书号:"+book.getId());
            holder.tvBorrowPerson.setText(book.getBorrower());
            holder.tvType.setText(book.getType());
            holder.tvPrice.setText(book.getPrice()+"元");
            holder.tvTime.setText(book.getPublicationDate());
        }

        return convertView;
    }

    class ViewHolder{

        TextView tvName;
        TextView tvAuthor;
        TextView tvId;
        TextView tvBorrowPerson;
        TextView tvType;
        TextView tvPrice;
        TextView tvTime;

        public ViewHolder(View view){
            tvName = (TextView) view.findViewById(R.id.tv_book_name);
            tvAuthor = (TextView) view.findViewById(R.id.tv_book_author);
            tvId = (TextView) view.findViewById(R.id.tv_book_id);
            tvBorrowPerson = (TextView) view.findViewById(R.id.tv_borrow_person);
            tvType = (TextView) view.findViewById(R.id.tv_book_type);
            tvPrice = (TextView) view.findViewById(R.id.tv_book_price);
            tvTime = (TextView) view.findViewById(R.id.tv_book_time);
        }

    }
}
