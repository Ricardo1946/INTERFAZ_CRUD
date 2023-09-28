package co.uniminuto.edu.interfaz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Spannable;
import android.util.Log;

import androidx.annotation.Nullable;

public class GestionBD extends SQLiteOpenHelper {
    //name bd
    private static final  String DATABASE_USERS ="dbUsuarios";
    private static final int VERSION = 1;
    //nombre de la tabla
    private static final String TABLE_USERS="usuarios";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE  "+TABLE_USERS+" (USU_DOCUMENTO INTEGER " +
            " PRIMARY KEY, USU_USUARIO varchar(50) NOT NULL, USU_NOMBRES varchar(150) NOT NULL," +
            " USU_APELLIDOS varhcar(50) NOT NULL, USU_CONTRA varchar(25) NOT NULL)";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_USERS;

    public void realizarBackup(int documento) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE USU_DOCUMENTO = " + documento;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // Obtiene los datos del usuario que se va a eliminar
            @SuppressLint("Range") int doc = cursor.getInt(cursor.getColumnIndex("USU_DOCUMENTO"));
            @SuppressLint("Range") String usuario = cursor.getString(cursor.getColumnIndex("USU_USUARIO"));
            @SuppressLint("Range") String nombres = cursor.getString(cursor.getColumnIndex("USU_NOMBRES"));
            @SuppressLint("Range") String apellidos = cursor.getString(cursor.getColumnIndex("USU_APELLIDOS"));
            @SuppressLint("Range") String contra = cursor.getString(cursor.getColumnIndex("USU_CONTRA"));

            // Crea un ContentValues para almacenar los datos del usuario en la tabla de backup
            ContentValues values = new ContentValues();
            values.put("USU_DOCUMENTO", doc);
            values.put("USU_USUARIO", usuario);
            values.put("USU_NOMBRES", nombres);
            values.put("USU_APELLIDOS", apellidos);
            values.put("USU_CONTRA", contra);

            // Inserta los datos del usuario en la tabla de backup
            db.insert("backup_table", null, values);
        }

        // Cierra el cursor y libera recursos
        cursor.close();

        // Elimina el usuario de la tabla original
        String deleteQuery = "DELETE FROM " + TABLE_USERS + " WHERE USU_DOCUMENTO = " + documento;
        db.execSQL(deleteQuery);

        // Cierra la conexión a la base de datos
        db.close();

        Log.i("Backup", "Backup realizado para el documento: " + documento);
    }


    public GestionBD(@Nullable Context context){
        super (context, DATABASE_USERS, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        Log.i("Base de datos","se creo la base de datos");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
