package familylibrarymanager.zhao.com.familylibrarymanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 数据库工具类
 * Created by zouyulong on 2017/4/24.
 * Student number:121
 * Person in charge :  zouyulong
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static Map<String, DataBaseOpenHelper> dbMaps = new HashMap<String, DataBaseOpenHelper>();
    private OnSqliteUpdateListener onSqliteUpdateListener;
    /**
     * 建表语句列表
     */
    private List<String> createTableList;
    private String nowDbName;

    private DataBaseOpenHelper(Context context, String dbName, int dbVersion, List<String> tableSqls) {
        super(context, dbName, null, dbVersion);
        nowDbName = dbName;
        createTableList = new ArrayList<String>();
        createTableList.addAll(tableSqls);
    }

    /**
     * @param @param  context
     * @param @param  userId
     * @param @return
     * @return DataBaseOpenHelper
     * @Title: getInstance
     * @Description: 获取数据库实例
     * @author lihy
     */
    public static DataBaseOpenHelper getInstance(Context context, String dbName, int dbVersion, List<String> tableSqls) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(dbName);
        if (dataBaseOpenHelper == null) {
            dataBaseOpenHelper = new DataBaseOpenHelper(context, dbName, dbVersion, tableSqls);
        }
        dbMaps.put(dbName, dataBaseOpenHelper);
        return dataBaseOpenHelper;
    }

    public static void clearInstance(){
        if(dbMaps!=null)
            dbMaps.clear();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sqlString : createTableList) {
            db.execSQL(sqlString);
        }
    }

    /**
     * @param @param sql
     * @param @param bindArgs
     * @return void
     * @Title: execSQL
     * @Description: Sql写入
     * @author lihy
     */
    public void execSQL(String sql, Object[] bindArgs) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.execSQL(sql, bindArgs);
        }
    }

    /**
     * @param @param  sql查询
     * @param @param  bindArgs
     * @param @return
     * @return Cursor
     * @Title: rawQuery
     * @Description:
     * @author lihy
     */
    public Cursor rawQuery(String sql, String[] bindArgs) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, bindArgs);
            return cursor;
        }
    }

    /**
     * @param @param table
     * @param @param contentValues 设定文件
     * @return void 返回类型
     * @throws
     * @Title: insert
     * @Description: 插入数据
     * @author lihy
     */
    public void insert(String table, ContentValues contentValues) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.insert(table, null, contentValues);
        }
    }

    /**
     * @param @param table
     * @param @param values
     * @param @param whereClause
     * @param @param whereArgs 设定文件
     * @return void 返回类型
     * @throws
     * @Title: update
     * @Description: 更新
     */
    public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.update(table, values, whereClause, whereArgs);
        }
    }

    /**
     * @param @param table
     * @param @param whereClause
     * @param @param whereArgs
     * @return void
     * @Title: delete
     * @Description:删除
     * @author lihy
     */
    public void delete(String table, String whereClause, String[] whereArgs) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.delete(table, whereClause, whereArgs);
        }
    }

    /**
     * @param @param table
     * @param @param columns
     * @param @param selection
     * @param @param selectionArgs
     * @param @param groupBy
     * @param @param having
     * @param @param orderBy
     * @return void
     * @Title: query
     * @Description: 查
     * @author lihy
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            return cursor;
        }
    }

    /**
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return Cursor
     * @Description:查
     * @exception:
     * @author: lihy
     * @time:2015-4-3 上午9:37:29
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            return cursor;
        }
    }

    /**
     * @param @return
     * @return Cursor
     * @Description 查询，方法重载,table表名，sqlString条件
     * @author lihy
     */
    public Cursor query(String tableName, String sqlString) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from " + tableName + " " + sqlString, null);

            return cursor;
        }
    }

    /**
     * @see android.database.sqlite.SQLiteOpenHelper#close()
     */
    public void clear() {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        dataBaseOpenHelper.close();
        dbMaps.remove(dataBaseOpenHelper);
    }

    /**
     * onUpgrade()方法在数据库版本每次发生变化时都会把用户手机上的数据库表删除，然后再重新创建。<br/>
     * 一般在实际项目中是不能这样做的，正确的做法是在更新数据库表结构时，还要考虑用户存放于数据库中的数据不会丢失,从版本几更新到版本几。(非
     * Javadoc)
     *
     * @see android.database.sqlite.SQLiteOpenHelper
     * #onUpgrade(android.database.sqlite
     * .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        if (onSqliteUpdateListener != null) {
            onSqliteUpdateListener.onSqliteUpdateListener(db, arg1, arg2);
        }
    }

    public void setOnSqliteUpdateListener(OnSqliteUpdateListener onSqliteUpdateListener) {
        this.onSqliteUpdateListener = onSqliteUpdateListener;
    }

    public interface OnSqliteUpdateListener {
        public void onSqliteUpdateListener(SQLiteDatabase db, int oldVersion, int newVersion);
    }
}
