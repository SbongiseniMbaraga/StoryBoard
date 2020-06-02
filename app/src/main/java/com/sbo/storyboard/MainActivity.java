package com.sbo.storyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    EditText etUrl;
    Button btClear, btSubmit;
    ImageView ivResult;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = findViewById(R.id.et_urlEditText);
        btClear = findViewById(R.id.ClearBtn);
        btSubmit = findViewById(R.id.SubmitBtn);
        ivResult = findViewById(R.id.iv_resultImageView);
        test = findViewById(R.id.TesttextView);

        final String imageOne = "https://assets3.thrillist.com/v1/image/2855064/414x310/scale;jpeg_quality=65.jpg";
        final String imageTwo = "https://www.verywellfamily.com/thmb/HboW-xK_RTHGfvTM82hoJXNyeLk=/1500x1000/filters:no_upscale():max_bytes(150000):strip_icc()/seven-social-skills-for-kids-4589865_V3-01-b5ac3238909241adbb3e2fa6ebacde18.png";

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUrl.setText("");
                ivResult.setImageBitmap(null);
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlLink = etUrl.getText().toString();
                if (urlLink.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Url ! ", Toast.LENGTH_SHORT).show();
                }else {
                    LoadImage loadImage = new LoadImage(ivResult);
                    loadImage.execute(urlLink);
                }
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUrl.setText(imageOne);
            }
        });
    }
    //
    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public LoadImage (ImageView ivResult){
            this.imageView = ivResult;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try (InputStream inputStream = new java.net.URL(urlLink).openStream()) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivResult.setImageBitmap(bitmap);
        }
    }
}