package com.example.mmatejam.drugikolokvij;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String IDKOLACA = "_id";
    static final String NAZIV = "naziv";
    static final String VRSTA = "vrsta";
    static final String SASTOJAK = "sastojak";
    static final String IDSASTOJKA = "idSastojak";
    static final String CIJENA = "cijena";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "kolaci";
    static final String DATABASE_TABLE2 = "sastojci";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
            "create table kolaci (_id integer primary key autoincrement, "
                    + "naziv text not null, vrsta text not null, sastojak text not null);";
    static final String DATABASE_CREATE2 =
            "create table sastojci (idSastojak integer primary key autoincrement, "
                    + "cijena integer, sastojak text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE2);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS kolaci");
            db.execSQL("DROP TABLE IF EXISTS sastojci");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertPodatak(String naziv, String vrsta, String sastojak)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put(NAZIV, naziv);
        initialValues.put(VRSTA, vrsta);
        initialValues.put(SASTOJAK, sastojak);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    public long insertPodatak2( int cijena, String sastojak)
    {
        ContentValues initialValues2 = new ContentValues();

        initialValues2.put(CIJENA, cijena);
        initialValues2.put(SASTOJAK, sastojak);

        return db.insert(DATABASE_TABLE2, null, initialValues2);
    }

    public boolean delete1(long rowId)
    {
        return db.delete(DATABASE_TABLE, IDKOLACA + "=" + rowId, null) > 0;
    }

    public boolean delete2(long rowId)
    {
        return db.delete(DATABASE_TABLE2, IDSASTOJKA + "=" + rowId, null) > 0;
    }

    public Cursor getKolaci()
    {
        return db.query(DATABASE_TABLE, new String[] {IDKOLACA, NAZIV,
                VRSTA, SASTOJAK}, null, null, null, null, null);
    }
    public Cursor getSastojci()
    {
        return db.query(DATABASE_TABLE2, new String[] {IDSASTOJKA, CIJENA,
                SASTOJAK}, null, null, null, null, null);
    }

    public Cursor getKolac(String sastojak) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {IDKOLACA,
                                NAZIV, VRSTA, SASTOJAK}, SASTOJAK + "=" + sastojak, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}