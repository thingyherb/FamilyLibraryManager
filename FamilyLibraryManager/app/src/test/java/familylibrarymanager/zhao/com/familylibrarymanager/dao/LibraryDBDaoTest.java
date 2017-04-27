package familylibrarymanager.zhao.com.familylibrarymanager.dao;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.HashMap;
import java.util.List;

import familylibrarymanager.zhao.com.familylibrarymanager.BuildConfig;
import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.constant.SQLConstant;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk=18)
public class LibraryDBDaoTest extends InstrumentationTestCase {
    private LibraryDBDao mDao;
    private Context mContext;
    private String TAG = "unittest";

    @org.junit.Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
        mContext =  RuntimeEnvironment.application.getApplicationContext();
        mDao = new LibraryDBDao(mContext);
    }

    private void saveData(){
        Book book1 = new Book();
        book1.setId("1");
        book1.setBookname("三国演义");
        book1.setPublicationDate("2011-1-1");
        book1.setType("小说");
        book1.setPrice(20.59);
        book1.setBorrower("邹玉龙");
        book1.setAuthor("罗贯中");

        Book book2 = new Book();
        book2.setId("2");
        book2.setBookname("西游记");
        book2.setPublicationDate("2011-1-1");
        book2.setType("小说");
        book2.setPrice(19.21);
        book2.setBorrower("张三");
        book2.setAuthor("吴承恩");

        Book book3 = new Book();
        book3.setId("3");
        book3.setBookname("封神榜");
        book3.setPublicationDate("2011-1-1");
        book3.setType("小说");
        book3.setPrice(33.33);
        book3.setBorrower("李四");
        book3.setAuthor("陆西星");

        Book book4 = new Book();
        book4.setId("4");
        book4.setBookname("水浒传");
        book4.setPublicationDate("2011-1-1");
        book4.setType("小说");
        book4.setPrice(26.43);
        book4.setBorrower("王五");
        book4.setAuthor("施耐庵");

        boolean succ1 = mDao.addBook(book1);
        boolean succ2 = mDao.addBook(book2);
        boolean succ3 = mDao.addBook(book3);
        boolean succ4 = mDao.addBook(book4);
        Log.d(TAG, "succ1:"+succ1);
        Log.d(TAG, "succ2:"+succ2);
        Log.d(TAG, "succ3:"+succ3);
        Log.d(TAG, "succ4:"+succ4);
    }

    private void traverseData(){
        List<Book> booklist = mDao.queryAllBookList();
        if(booklist!=null) {
            for (Book book : booklist) {
                printfBook(book);
            }
        }
    }

    private void printfBook(Book book){
        Log.d(TAG, "id:"+book.getId());
        Log.d(TAG, "name:"+book.getBookname());
        Log.d(TAG, "author:"+book.getAuthor());
        Log.d(TAG, "borrower:"+book.getBorrower());
        Log.d(TAG, "type:"+book.getType());
        Log.d(TAG, "price:"+book.getPrice());
        Log.d(TAG, "publicationdate:"+book.getPublicationDate());
    }

    @Test
    public void testSaveAndQueryAllBook() throws Exception {
//        saveData();
        traverseData();
        mDao.destoryDB();
    }

    @Test
    public void testSearchBook() throws Exception {
        saveData();
//        Book book = mDao.searchBook(SQLConstant.KEY_BOOK_NAME, "封神榜");
//        if(book!=null)
//            printfBook(book);
        HashMap map = new HashMap();
        map.put(SQLConstant.KEY_BOOK_NAME, "封神榜");
        map.put(SQLConstant.KEY_TYPE, "小说");
        map.put(SQLConstant.KEY_PRICE, "33.33");
        Book book2 = mDao.searchBook(map);
        if(book2!=null)
            printfBook(book2);
        mDao.destoryDB();
    }
//
//    @Test
//    public void testupdateData() throws Exception {
//        saveData();
//        Book book = new Book();
//        book.setId("1");
//        book.setBookname("史记");
//        book.setType("小说");
//        book.setPublicationDate("2011-1-2");
//        book.setAuthor("司马迁");
//        book.setPrice(59.99);
//        book.setBorrower("虎子");
//        mDao.updateBookInfo(book.getId(), book);
//        traverseData();
//        mDao.destoryDB();
//    }
}