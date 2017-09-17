package com.example.aarshad.socialappstart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Login extends AppCompatActivity {
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    ImageView ivUserImage;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    int RESULT_LOAD_IMAGE = 346;

    public static final String TAG = "AnonymousTag";
    FirebaseAuth mAuth ;

    FirebaseAuth.AuthStateListener mAuthStateListener ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         etName=(EditText)findViewById(R.id.etName);
          etEmail=(EditText)findViewById(R.id.etEmail);
          etPassword=(EditText)findViewById(R.id.etPassword);
         ivUserImage=(ImageView) findViewById(R.id.ivUserImage);
        ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserPermission();
            }
        });

        mAuth  = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    Log.d(TAG,"onAuthStateChanged: SignedIn: " + user.getUid());
                } else{

                    Log.d(TAG,"onAuthStateChanged: SignedOut: ");
                }
            }
        };

    }

    public void loginButtonPressed(View view) {
        showProgressDialog();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://friendlychat-51815.appspot.com");
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyHHmmss");
        Date dateobj = new Date();

        final String ImagePath= df.format(dateobj) +".jpg";
        StorageReference mountainsRef = storageRef.child("images/"+ ImagePath);
        ivUserImage.setDrawingCacheEnabled(true);
        ivUserImage.buildDrawingCache();
        // Bitmap bitmap = imageView.getDrawingCache();
        BitmapDrawable drawable=(BitmapDrawable)ivUserImage.getDrawable();
        Bitmap bitmap =drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                String name = "";
                try {
                    // Name with space and URL as it is won't work if we pass so we need encoding
                    name = java.net.URLEncoder.encode( etName.getText().toString() , "UTF-8");
                    downloadUrl= java.net.URLEncoder.encode(downloadUrl , "UTF-8");

                } catch (UnsupportedEncodingException e) {

                    Log.e(TAG,"Exception in Encoding");
                }

                String url="http://10.0.2.2:8888/SocialAppServer/Register.php?first_name="+name+"&password="+etPassword.getText().toString()+"&picture_path=abc&email="+etEmail.getText().toString();
                Log.d(TAG, "URL: " + url );
                new MyAsyncTaskgetNews().execute(url);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
        signInAnonymously();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        hideProgressDialog();
    }
    private void signInAnonymously() {
        // Firstly we need to enable Anonymous login on Firebase.
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());

                        }

                    }
                });
    }

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    void checkUserPermission(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        LoadImage();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission has been granted
                    LoadImage();
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Permission is denied " , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void LoadImage(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ivUserImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }



    // get news from server
    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(10000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    Operations operations=new Operations(getApplicationContext());
                    NewsData = operations.ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } catch (IOException e){
                    Log.e(TAG,"Error in Reading " + e.getMessage());
                }
                finally {
                    //end connection
                    urlConnection.disconnect();
                }

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try {
                Log.d(TAG,"Result : " +progress[0]);
                JSONObject json = new JSONObject(progress[0]);
                //display response data
                if (json.getString("msg") == null)
                    return;


                if (json.getString("msg").equalsIgnoreCase("user is added")) {
                    Log.d(TAG,"User has been Added");
                    Toast.makeText(getApplicationContext(), json.getString("msg"), Toast.LENGTH_LONG).show();

                    String url="http://10.0.2.2:8888/SocialAppServer/Login.php?email="+etEmail.getText().toString()+"&password="+etPassword.getText().toString() ;

                    new MyAsyncTaskgetNews().execute(url);
                }

                if (json.getString("msg").equalsIgnoreCase("Pass Login")) {
                    Log.d(TAG,"User Logged in");
                    JSONArray UserInfo=new JSONArray( json.getString("info"));
                    JSONObject UserCreintal= UserInfo.getJSONObject(0);
                    //Toast.makeText(getApplicationContext(),UserCreintal.getString("user_id"),Toast.LENGTH_LONG).show();
                    hideProgressDialog();
                    SaveSettings saveSettings= new SaveSettings(getApplicationContext());
                    saveSettings.saveData(UserCreintal.getString("user_id"));
                    finish(); //close this activity
                }

            } catch (Exception ex) {
                Log.d("er",  ex.getMessage());
            }
        }

        protected void onPostExecute(String  result2){

        }

    }

}
