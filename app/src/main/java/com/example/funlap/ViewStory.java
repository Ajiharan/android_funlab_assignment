package com.example.funlap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.funlap.adaptors.StoryListAdapter;
import com.example.funlap.model.Story;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ViewStory extends AppCompatActivity {
    private Button backButton;
    private SwipeMenuListView listViews;
    private ArrayList<Story> dataArrayList;
    private StoryListAdapter listAdapter;
    private FirebaseFirestore db ;
    private EditText txtStory_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_story);
        txtStory_search=findViewById(R.id.txtStory_search);
        db = FirebaseFirestore.getInstance();
        backButton=findViewById(R.id.btn_back);
        listViews = (SwipeMenuListView) findViewById(R.id.view_story_details);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewStory.this,UserHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        txtStory_search.addTextChangedListener(new TextWatcher() {
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

    private void storyDetails() {

        getAllData();
    }

    private void getSearchData(String res){

        db.collection("stories").whereEqualTo("storyTitle",res)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Story story=new Story(document.getId(),document.get("storyTitle").toString(),document.get("storyAuthor").toString(),document.get("storyDesc").toString());

                                dataArrayList.add(story);
                            }
                            listAdapter = new StoryListAdapter(ViewStory.this, dataArrayList);
                            listViews.setAdapter(listAdapter);
                        } else {
                            Toast.makeText(ViewStory.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getAllData(){

        db.collection("stories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Story story=new Story(document.getId(),document.get("storyTitle").toString(),document.get("storyAuthor").toString(),document.get("storyDesc").toString());

                                dataArrayList.add(story);
                            }
                            listAdapter = new StoryListAdapter(ViewStory.this, dataArrayList);
                            listViews.setAdapter(listAdapter);
                        } else {
                            Toast.makeText(ViewStory.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}