package com.example.mmiazi.uav_v2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView userPhoto;
    private EditText et_Name;
    private EditText et_Email;
    private Button createButton;
    private Button cancelButton;
    private Bitmap bitmap;
    private User user;
    private static int CAM_REQ = 0x1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Be a Beacon!");

        userPhoto = findViewById(R.id.pick_camera);
        et_Name = findViewById(R.id.text_Name);
        et_Email = findViewById(R.id.text_Email);
        createButton = findViewById(R.id.button_register);
        cancelButton = findViewById(R.id.button_cancel);

        userPhoto.setOnClickListener(new takePhoto());
        cancelButton.setOnClickListener(this);
        createButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_cancel){
            Log.d("test","Cancel pressed");
            finish();
        } else if(view.getId()==R.id.button_register){
            String userName = et_Name.getText().toString().trim();
            String email = et_Email.getText().toString().trim();
            boolean inputValid = true;

            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            }
            else{
                et_Email.setError("Put a valid Email address!");
//                Toast.makeText(this,"Please put a valid Email address!!!", Toast.LENGTH_SHORT).show();
                inputValid = false;
            }

            if(userName.equals("")||email.equals("")) {
                inputValid = false;
                Toast.makeText(this,"Please fill up all the fields", Toast.LENGTH_SHORT).show();
                if(inputValid){
                    Bitmap userPhoto = bitmap;
                    //((ImageView)getView().findViewById(R.id.testView)).setImageBitmap(userPhoto);
                    user = new User(userName, email);
                    userSignUp(user);
//                    ((MenuItem)getView().findViewById(R.id.nav_signUp)).setTitle(R.string.logOut);
//                    ((MenuItem)getView().findViewById(R.id.nav_signUp)).setIcon(R.mipmap.logout_foreground);
                    finish();
                }
            }

        }
    }
    private void userSignUp(User user) {
        Log.d("test", user.getUserEmail() + " " + user.getUserName());
    }

    class takePhoto implements ImageButton.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAM_REQ);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQ) {
            bitmap = (Bitmap) data.getExtras().get("data");
            userPhoto.setImageBitmap(bitmap);
        }
    }
}
