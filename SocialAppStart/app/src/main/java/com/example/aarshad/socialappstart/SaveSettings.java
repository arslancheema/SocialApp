package com.example.aarshad.socialappstart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class SaveSettings {
    public  static String UserID="";

    Context context;
    SharedPreferences ShredRef;
    public  SaveSettings(Context context){
        this.context=context;
        ShredRef=context.getSharedPreferences("myRef",Context.MODE_PRIVATE);
    }

    void saveData(String UserID){

        SharedPreferences.Editor editor=ShredRef.edit();
        editor.putString("UserID",UserID);
         editor.commit();
        loadData();
    }

    void loadData(){
        UserID= ShredRef.getString("UserID","0");
        if (UserID.equals("0")){

            Intent intent=new Intent(context, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}
