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

import java.util.List;

import familylibrarymanager.zhao.com.familylibrarymanager.BuildConfig;
import familylibrarymanager.zhao.com.familylibrarymanager.bean.Book;
import familylibrarymanager.zhao.com.familylibrarymanager.constant.SQLConstant;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk=18)
public class LibraryDBDaoTest extends InstrumentationTestCase {
    private LibraryDBDao mDao;
    private Context mContext;


    @org.junit.Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
        mContext =  RuntimeEnvironment.application.getApplicationContext();
        mDao = new LibraryDBDao(mContext);
    }

    private void saveData(){
        Book book1 = new Book();
        book1.setBookname("三国演义");
        book1.setPublicationDate(System.currentTimeMillis());
        book1.setType("小说");
        book1.setPrice(20.59);
        book1.setBorrower("邹玉龙");
        book1.setAuthor("罗贯中");

        Book book2 = new Book();
        book2.setBookname("西游记");
        book2.setPublicationDate(System.currentTimeMillis());
        book2.setType("小说");
        book2.setPrice(19.21);
        book2.setBorrower("张三");
        book2.setAuthor("吴承恩");

        Book book3 = new Book();
        book3.setBookname("封神榜");
        book3.setPublicationDate(System.currentTimeMillis());
        book3.setType("小说");
        book3.setPrice(33.33);
        book3.setBorrower("李四");
        book3.setAuthor("陆西星");

        Book book4 = new Book();
        book4.setBookname("水浒传");
        book4.setPublicationDate(System.currentTimeMillis());
        book4.setType("小说");
        book4.setPrice(26.43);
        book4.setBorrower("王五");
        book4.setAuthor("施耐庵");

        mDao.addBook(book1);
        mDao.addBook(book2);
        mDao.addBook(book3);
        mDao.addBook(book4);
    }

    private void traverseData(){
        List<Book> booklist = mDao.queryAllBookList();
        for(Book book : booklist){
            printfBook(book);
        }
    }

    private void printfBook(Book book){
        Log.d("test", "id:"+book.getId());
        Log.d("test", "name:"+book.getBookname());
        Log.d("test", "author:"+book.getAuthor());
        Log.d("test", "borrower:"+book.getBorrower());
        Log.d("test", "type:"+book.getType());
        Log.d("test", "price:"+book.getPrice());
        Log.d("test", "publicationdate:"+book.getPublicationDate());
    }

    @Test
    public void testSaveAndQueryAllBook() throws Exception {
        saveData();
        traverseData();
        mDao.resetDbOpenHelper();
    }

    @Test
    public void testSearchBook() throws Exception {
        saveData();
        Book book = mDao.searchBook(SQLConstant.KEY_BOOK_NAME, "封神榜");
        printfBook(book);
        mDao.resetDbOpenHelper();
    }

    @Test
    public void testupdateData() throws Exception {
        saveData();
        Book book = new Book();
        book.setBookname("史记");
        book.setType("小说");
        book.setPublicationDate(System.currentTimeMillis());
        book.setAuthor("司马迁");
        book.setPrice(59.99);
        book.setBorrower("虎子");
        mDao.updateBookInfo(1, book);
        traverseData();
        mDao.resetDbOpenHelper();
    }
}