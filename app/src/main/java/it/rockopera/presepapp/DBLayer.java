package it.rockopera.presepapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;







public class DBLayer  {





    Context context;



    public  enum TipoQuery {
        Selezione,
        Comando
    }



    public static final String DATABASE_NAME = "presepapp.db";

    public static final int DATABASE_VERSION = 1;


  // private static String DATABASE_PATH = "/data/data/it.rockopera.presepapp/databases/";
  //private static String DATABASE_PATH ="";

   // private static String DATABASE_NAME = "db";




    ArrayList<DataModel> dataModels;



    private DbHelper ourHelper;
    private  static Context ourContext;
    private static SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub



        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE 'sw_schedule' ('id' INTEGER, 'timer_start' INTEGER, 'sw_name' TEXT, 'sw_state' INTEGER);");

           Log.e("DB tabella ", "creata");


        }




        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLECN);
            //onCreate(db);
        }






    }

    public DBLayer(Context c){
        this.ourContext = c;
        // handler = null;
    }

    public DBLayer open() throws SQLException {
        this.ourHelper = new DbHelper(ourContext);

       // String myPath = DATABASE_PATH + DATABASE_NAME;
      //  this.ourDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        this.ourHelper = new DbHelper(ourContext);
        this.ourDatabase = ourHelper.getWritableDatabase();
        return this;

    }







    public void close(){
        this.ourHelper.close();
    }






    public  void updateDB(String LAB, Integer STAT)
    {

        ourDatabase.execSQL("UPDATE Cues_table set CueSTATE = " + STAT + " WHERE CueLABEL = '" + LAB + "'" );


        // MainActivity main = new MainActivity(); // create new instance and set reference



        //    adapter.notifyDataSetChanged();



        // main.UpdateLV(); //Code goes here
    }
















    public void deleteAll()
    {

        ourDatabase.execSQL("DELETE from Cues_table");


    }


    public Integer CountRecords()
    {


        Cursor cc = ourDatabase.rawQuery("SELECT * from Cues_table",null);
        int recCount = cc.getCount();


        return recCount;
    }





    public Cursor Execute(String Query, TipoQuery tipoCmd){
        Cursor c = null;

        try{
            switch(tipoCmd){
                case Comando:
                    ourDatabase.execSQL(Query);
                    break;
                case Selezione:
                    c = ourDatabase.rawQuery(Query,null);
                    break;
            }
        }catch (Exception e){}

        return c;
    }


}