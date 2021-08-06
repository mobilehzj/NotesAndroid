package com.github.shoothzj.notes;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {
    private Button deleteButton, backButton;
    private ImageView imageView;
    private VideoView videoView;
    private TextView textView;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;

    public SelectActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        deleteButton = (Button) findViewById(R.id.s_delete);
        backButton = (Button) findViewById(R.id.s_back);
        imageView = (ImageView) findViewById(R.id.s_img);
        videoView = (VideoView) findViewById(R.id.s_video);
        textView = (TextView) findViewById(R.id.s_tv);
        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        backButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        if (getIntent().getStringExtra(NotesDB.PATH).equals("null")) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra(NotesDB.VIDEO).equals("null")) {
            videoView.setVisibility(View.GONE);
        } else {
            videoView.setVisibility(View.VISIBLE);
        }
        textView.setText(getIntent().getStringExtra(NotesDB.CONTENT));
        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(
                NotesDB.PATH));
        imageView.setImageBitmap(bitmap);
        videoView.setVideoURI(Uri
                .parse(getIntent().getStringExtra(NotesDB.VIDEO)));
        videoView.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_delete:
                deleteDate();
                finish();
                break;

            case R.id.s_back:
                finish();
                break;
        }
    }

    public void deleteDate() {
        dbWriter.delete(NotesDB.TABLE_NAME,
                "_id=" + getIntent().getIntExtra(NotesDB.ID, 0), null);
    }
}
