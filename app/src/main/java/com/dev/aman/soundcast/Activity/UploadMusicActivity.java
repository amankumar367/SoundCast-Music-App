package com.dev.aman.soundcast.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.aman.soundcast.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UploadMusicActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 101;
    private static final int REQ_CODE_PICK_SOUNDFILE = 102;
    private EditText mTitle;
    private ImageView mImage, mMusic;
    private TextView mUpload, mImageText, mMusicText;
    private byte[] imageByte = null;
    private byte[] musicByte = null;
    private ProgressDialog mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_music);
        init();
        onClick();

        mProgressBar = new ProgressDialog(this);
//        mProgressBar.setTitle("Loading Songs");
        mProgressBar.setMessage("Uploading Song");
        mProgressBar.setCanceledOnTouchOutside(false);
    }

    private void init() {
        mTitle = findViewById(R.id.addtitle);
        mImage = findViewById(R.id.selecteImage);
        mMusic = findViewById(R.id.selecteMusic);
        mImageText = findViewById(R.id.selecteImageText);
        mMusicText = findViewById(R.id.selecteMusicText);
        mUpload = findViewById(R.id.upload);
    }

    private void onClick() {

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        mMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/mpeg");
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_audio_file_title)), REQ_CODE_PICK_SOUNDFILE);
            }
        });

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidContents()){
                    uploadFile();
                    mProgressBar.show();
                }else {
                    Toast.makeText(UploadMusicActivity.this, "Complete all process", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mImageText.setText(getFilename(selectedImage));
            mImage.setImageBitmap(bmp);
            try {
                imageByte = parseIntoByte(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQ_CODE_PICK_SOUNDFILE && resultCode == Activity.RESULT_OK){
            if ((data != null) && (data.getData() != null)){
                Uri audioFileUri = data.getData();
                mMusicText.setText(getFilename(audioFileUri));
                try {
                    musicByte = parseIntoByte(audioFileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public String getFilename(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        result = result.replaceAll("[^A-Za-z0-9.]","");
//        result = result.substring(0, result.length() - 4);

        return result;
    }

    private boolean checkValidContents() {
        boolean flag = false;

        if(!mTitle.getText().toString().equals("") &&  !mMusicText.getText().toString().equals("Upload Music") && !mImageText.getText().toString().equals("Upload Image")){
            flag = true;
        }
        return flag;
    }

    private void uploadFile() {

        final ParseObject entity = new ParseObject("songs_library");
        ParseFile audio = new ParseFile(mMusicText.getText().toString(), musicByte);
        final ParseFile thumbnail = new ParseFile(mImageText.getText().toString(), imageByte);

        entity.put("title", mTitle.getText().toString());
        entity.put("link", "A string");
        entity.put("thumbnail", mImageText.getText().toString());
        entity.put("music_file", new ParseFile(mMusicText.getText().toString(), musicByte));
        entity.put("thumbnail_file", new ParseFile(mImageText.getText().toString(), imageByte));

        audio.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    thumbnail.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                entity.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e == null){
                                            Toast.makeText(UploadMusicActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                            mProgressBar.dismiss();
                                            finish();
                                        }else {
                                            Toast.makeText(UploadMusicActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }

    private byte[] parseIntoByte(Uri uri) throws IOException {

        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

}
