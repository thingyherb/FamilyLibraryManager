package familylibrarymanager.zhao.com.familylibrarymanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.constant.SQLConstant;
import familylibrarymanager.zhao.com.familylibrarymanager.utils.DataBaseOpenHelper;

/**
 * Description: 图书dao类
 * Created by zouyulong on 2017/4/24.
 * student number:121
 * Person in charge :  zouyulong
 */

public class LibraryDBDao implements Serializable {
    public static final int SEARCHTYPE_ID = 1;

    private Context mContext;
    private DataBaseOpenHelper mDbOpenHelper;//数据库打开帮助类

    public LibraryDBDao(Context context) {
        mContext = context;
    }

    /**
     * 获取databasehelper实例
     */
    private DataBaseOpenHelper getDataBaseHelper() {
        if (mDbOpenHelper == null)
            mDbOpenHelper = DataBaseOpenHelper.getInstance(mContext, SQLConstant.DB_NAME,
                    SQLConstant.DB_VERSION, getCreateDBSql());
        return mDbOpenHelper;
    }

    public void destoryDB() {
        mDbOpenHelper = null;
        DataBaseOpenHelper.clearInstance();
    }

    /**
     * 获取创建数据库表语句
     *
     * @return
     */
    private List getCreateDBSql() {
        ArrayList sqlList = new ArrayList();
        sqlList.add(SQLConstant.SQL_CREATE);
        return sqlList;
    }

    /**
     * 添加一本书
     *
     * @param book
     */
    public boolean addBook(Book book) {
        if(book==null|| TextUtils.isEmpty(String.valueOf(book.getId())))
            return false;
        //如果数据库中有此id 返回false
        ContentValues values = new ContentValues();
        values.put(SQLConstant.KEY_ID, book.getId());
        values.put(SQLConstant.KEY_BOOK_NAME, book.getBookname());
        values.put(SQLConstant.KEY_AUTHOR, book.getAuthor());
        values.put(SQLConstant.KEY_BORROWER, book.getBorrower());
        values.put(SQLConstant.KEY_PRICE, book.getPrice());
        values.put(SQLConstant.KEY_PUBLICATION_DATE, book.getPublicationDate());
        values.put(SQLConstant.KEY_TYPE, book.getType());
        return getDataBaseHelper().insert(SQLConstant.TABLE_BOOK, values);
    }

    /**
     * 查询所有图书列表
     *
     * @param
     */
    public List<Book> queryAllBookList() {
        Cursor results = getDataBaseHelper().query(SQLConstant.TABLE_BOOK,
                new String[]{SQLConstant.KEY_ID, SQLConstant.KEY_BOOK_NAME,
                        SQLConstant.KEY_AUTHOR, SQLConstant.KEY_BORROWER, SQLConstant.KEY_PUBLICATION_DATE,
                        SQLConstant.KEY_PRICE, SQLConstant.KEY_TYPE},
                null, null, null, null, null);
        return convertToBookList(results);
    }

    /**
     * 更新图书信息
     *
     * @param id
     * @param book
     * @return
     */
    public void updateBookInfo(String id, Book book) {
        ContentValues values = new ContentValues();
        values.put(SQLConstant.KEY_ID, book.getId());
        values.put(SQLConstant.KEY_BOOK_NAME, book.getBookname());
        values.put(SQLConstant.KEY_PUBLICATION_DATE, book.getPublicationDate());
        values.put(SQLConstant.KEY_TYPE, book.getType());
        values.put(SQLConstant.KEY_AUTHOR, book.getAuthor());
        values.put(SQLConstant.KEY_BORROWER, book.getBorrower());
        values.put(SQLConstant.KEY_PRICE, book.getPrice());
        getDataBaseHelper().update(SQLConstant.TABLE_BOOK,
                values, SQLConstant.KEY_ID + "=" + id, null);
    }

    /**
     * 查询图书列表
     *
     * @param columnName 字段名称，可选如下： SQLConstant.TABLE_BOOK,
     *                   SQLConstant.KEY_ID, SQLConstant.KEY_BOOK_NAME,
     *                   SQLConstant.KEY_AUTHOR, SQLConstant.KEY_BORROWER,
     *                   SQLConstant.KEY_PUBLICATION_DATE,
     *                   SQLConstant.KEY_PRICE, SQLConstant.KEY_TYPE
     */
    public List<Book> searchBooks(String columnName, String columnValue) {
        Cursor results = getDataBaseHelper().query(SQLConstant.TABLE_BOOK,
                new String[]{SQLConstant.KEY_ID, SQLConstant.KEY_BOOK_NAME,
                        SQLConstant.KEY_AUTHOR, SQLConstant.KEY_BORROWER,
                        SQLConstant.KEY_PUBLICATION_DATE,
                        SQLConstant.KEY_PRICE, SQLConstant.KEY_TYPE},
                columnName + "=?", new String[]{String.valueOf(columnValue)}, null, null, null);
        return convertToBookList(results);
    }

    /**
     * 多个查询关键字情况
     * @param map
     * @return
     */
    public List<Book> searchBooks(HashMap<String, String> map) {
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        StringBuffer sb = new StringBuffer();
        ArrayList<String> valueList = new ArrayList();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            sb.append(key).append("=?");
            valueList.add(map.get(key));
            if(iterator.hasNext()){
                sb.append(" and ");
            }
        }
        String[] strs = new String[valueList.size()];
        Cursor results = getDataBaseHelper().query(SQLConstant.TABLE_BOOK,
                new String[]{SQLConstant.KEY_ID, SQLConstant.KEY_BOOK_NAME,
                        SQLConstant.KEY_AUTHOR, SQLConstant.KEY_BORROWER,
                        SQLConstant.KEY_PUBLICATION_DATE,
                        SQLConstant.KEY_PRICE, SQLConstant.KEY_TYPE},
                sb.toString(), valueList.toArray(strs), null, null, null);
        return convertToBookList(results);
    }

    /**
     * 删除一本书
     *
     * @param id
     */
    public void removeBook(int id) {
        getDataBaseHelper().delete(SQLConstant.TABLE_BOOK,
                SQLConstant.KEY_ID + "=" + id, null);
    }

    /**
     * 删除所有数据
     */
    public void delAllData() {
        getDataBaseHelper().delete(SQLConstant.TABLE_BOOK, null, null);
    }

    /**
     * 转化游标为Book对象
     *
     * @param cursor
     * @return
     */
    private List<Book> convertToBookList(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Book> mBookList = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            Book book = new Book();
            book.setId(cursor.getString(0));
            book.setBookname(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_BOOK_NAME)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_AUTHOR)));
            book.setBorrower(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_BORROWER)));
            book.setPrice(cursor.getDouble(cursor.getColumnIndex(SQLConstant.KEY_PRICE)));
            book.setType(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_TYPE)));
            book.setPublicationDate(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_PUBLICATION_DATE)));
            mBookList.add(book);
            cursor.moveToNext();
        }
        return mBookList;
    }

    /**
     * 转化游标为Book对象
     *
     * @param cursor
     * @return
     */
    private Book convertToBook(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        Book book = null;
        if (resultCounts > 0) {
            book = new Book();
            book.setId(cursor.getString(0));
            book.setBookname(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_BOOK_NAME)));
            book.setAuthor(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_AUTHOR)));
            book.setBorrower(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_BORROWER)));
            book.setPrice(cursor.getDouble(cursor.getColumnIndex(SQLConstant.KEY_PRICE)));
            book.setType(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_TYPE)));
            book.setPublicationDate(cursor.getString(cursor.getColumnIndex(SQLConstant.KEY_PUBLICATION_DATE)));
            cursor.moveToNext();
        }
        return book;
    }
}
