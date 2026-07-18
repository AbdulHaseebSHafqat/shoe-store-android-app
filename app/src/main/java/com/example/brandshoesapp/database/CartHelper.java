package com.example.brandshoesapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.brandshoesapp.model.MyCartsModel;

import java.util.ArrayList;
import java.util.List;

public class CartHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CartDB";
    private static final int DATABASE_VERSION = 3; // Incremented to support local images

    private static final String TABLE_CART = "cart";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_TOTAL_QUANTITY = "totalQuantity";
    private static final String COLUMN_TOTAL_PRICE = "totalPrice";
    private static final String COLUMN_IMG_URL = "img_url";
    private static final String COLUMN_IMG_RES = "imageRes"; // New column

    public CartHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_TOTAL_QUANTITY + " TEXT,"
                + COLUMN_TOTAL_PRICE + " TEXT,"
                + COLUMN_IMG_URL + " TEXT,"
                + COLUMN_IMG_RES + " INTEGER" + ")";
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_CART + " ADD COLUMN " + COLUMN_IMG_URL + " TEXT");
        }
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_CART + " ADD COLUMN " + COLUMN_IMG_RES + " INTEGER DEFAULT 0");
        }
    }

    public void addToCart(MyCartsModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, model.getProductName());
        values.put(COLUMN_PRICE, model.getProductPrice());
        values.put(COLUMN_DATE, model.getCurrentDate());
        values.put(COLUMN_TIME, model.getCurrentTime());
        values.put(COLUMN_TOTAL_QUANTITY, model.getTotalQuantity());
        values.put(COLUMN_TOTAL_PRICE, model.getTotalPrice());
        values.put(COLUMN_IMG_URL, model.getImg_url());
        values.put(COLUMN_IMG_RES, model.getImageRes());

        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public List<MyCartsModel> getAllCartItems() {
        List<MyCartsModel> cartList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MyCartsModel model = new MyCartsModel();
                model.setDocumentId(String.valueOf(cursor.getInt(0)));
                model.setProductName(cursor.getString(1));
                model.setProductPrice(cursor.getString(2));
                model.setCurrentDate(cursor.getString(3));
                model.setCurrentTime(cursor.getString(4));
                model.setTotalQuantity(cursor.getString(5));
                model.setTotalPrice(cursor.getString(6));
                model.setImg_url(cursor.getString(7));
                model.setImageRes(cursor.getInt(8));
                cartList.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartList;
    }

    public void deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }

    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }
}
