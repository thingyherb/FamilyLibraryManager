package familylibrarymanager.zhao.com.familylibrarymanager.constant;

/**
 * Description: 数据库常量类
 * Created by zouyulong on 2017/4/24.
 * student number:121
 * Person in charge :  zouyulong
 */

public class SQLConstant {
    public static final String DB_NAME = "library.db";//数据库名称
    public static final String TABLE_BOOK = "bookinfo";//数据表名称
    public static final int DB_VERSION = 1;//数据库版本

    public static String KEY_ID = "id";
    public static String KEY_BOOK_NAME = "bookname";
    public static String KEY_TYPE = "type";
    public static String KEY_AUTHOR = "author";
    public static String KEY_PRICE = "price";
    public static String KEY_BORROWER = "borrower";//借阅人
    public static String KEY_PUBLICATION_DATE = "publication_date";//出版时间

    public static final String SQL_CREATE = "create table if not exists " +
            TABLE_BOOK + " (" + KEY_ID + " integer primary key autoincrement, " +
            KEY_BOOK_NAME + " text not null, " + KEY_TYPE + " text not null, " + KEY_PRICE + " float, "+
            KEY_AUTHOR + " text not null, " + KEY_BORROWER + " text, " +
            KEY_PUBLICATION_DATE + " long" + ");";
}
