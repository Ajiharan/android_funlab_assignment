package com.example.funlap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.funlap.adaptors.StoryListAdapter;
import com.example.funlap.adaptors.VideoListAdapter;
import com.example.funlap.model.Story;
import com.example.funlap.model.VideoFile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ViewVideo extends AppCompatActivity {
    private Button backButton;
    private SwipeMenuListView listViews;
    private ArrayList<VideoFile> dataArrayList;
    private VideoListAdapter listAdapter;
    private FirebaseFirestore db ;
    private EditText txtvideo_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
        txtvideo_search=findViewById(R.id.txtvideo_search);
        db = FirebaseFirestore.getInstance();
        backButton=findViewById(R.id.btn_back);
        listViews = (SwipeMenuListView) findViewById(R.id.view_video_details);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewVideo.this,UserHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        txtvideo_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{

                    if(s.toString().isEmpty()){
                        getAllData();
                    }else{
                        getSearchData(s.toString());
                    }
                }catch (ConcurrentModificationException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
        storyDetails();
    }
    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.parseColor("#F45557")));
            // set item width
            deleteItem.setWidth(150);
            deleteItem.setIcon(R.drawable.delete_icon);
            deleteItem.setTitleColor(Color.WHITE);

            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
    private void storyDetails() {

        getAllData();
        dataArrayList = new ArrayList<>();
        listAdapter = new VideoListAdapter(ViewVideo.this, dataArrayList);
        listViews.setAdapter(listAdapter);

        listViews.setMenuCreator(creator);
        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                final VideoFile videoFile=dataArrayList.get(i);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoFile.getUri()));
                startActivity(browserIntent);

            }
        });

    }
    private void getSearchData(String res){

        db.collection("videos").whereEqualTo("videoTitle",res)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                VideoFile videoFile=new VideoFile(document.getId(),document.get("videoTitle").toString(),document.get("videoDesc").toString(),document.get("videoUrl").toString());

                                dataArrayList.add(videoFile);
                            }
                            listAdapter = new VideoListAdapter(ViewVideo.this, dataArrayList);
                            listViews.setAdapter(listAdapter);
                        } else {
                            Toast.makeText(ViewVideo.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getAllData(){

        db.collection("videos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                VideoFile videoFile=new VideoFile(document.getId(),document.get("videoTitle").toString(),document.get("videoDesc").toString(),document.get("videoUrl").toString());

                                dataArrayList.add(videoFile);
                            }
                            listAdapter = new VideoListAdapter(ViewVideo.this, dataArrayList);
                            listViews.setAdapter(listAdapter);
                        } else {
                            Toast.makeText(ViewVideo.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}