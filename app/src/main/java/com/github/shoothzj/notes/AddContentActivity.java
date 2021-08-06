package com.github.shoothzj.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContentActivity extends AppCompatActivity implements View.OnClickListener {
    private String val;
    private Button saveButton, cancelButton;
    private EditText editText;
    private ImageView contentPicture;
    private VideoView contentVideo;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;
    private File phoneFile, videoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);
        val = getIntent().getStringExtra("flag");
        saveButton = (Button) findViewById(R.id.save);
        cancelButton = (Button) findViewById(R.id.cancel);
        editText = (EditText) findViewById(R.id.editText);
        contentPicture = (ImageView) findViewById(R.id.contentPicture);
        contentVideo = (VideoView) findViewById(R.id.contentVideo);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        initView();
    }

    public void initView() {
        if (val.equals("1")) {
            contentPicture.setVisibility(View.GONE);
            contentVideo.setVisibility(View.GONE);
        }
        if (val.equals("2")) {
            contentPicture.setVisibility(View.VISIBLE);
            contentVideo.setVisibility(View.GONE);
            Intent iImg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            phoneFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + getTime() + ".jpg");
            iImg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phoneFile));
            startActivityForResult(iImg, 1);
        }
        if (val.equals("3")) {
            contentPicture.setVisibility(View.GONE);
            contentVideo.setVisibility(View.VISIBLE);
            Intent iVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + getTime() + ".3gp");
            iVideo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
            startActivityForResult(iVideo, 2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                addDB();
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            default:
                break;
        }
    }

    public void addDB() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesDB.CONTENT, editText.getText().toString());
        contentValues.put(NotesDB.TIME, getTime());
        contentValues.put(NotesDB.PATH, phoneFile + "");
        contentValues.put(NotesDB.VIDEO, videoFile + "");
        dbWriter.insert(NotesDB.TABLE_NAME, null, contentValues);
    }

    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Bitmap bitmap = BitmapFactory.decodeFile(phoneFile.getAbsolutePath());
            contentPicture.setImageBitmap(bitmap);
        }
        if (requestCode == 2) {
        }
    }
}

