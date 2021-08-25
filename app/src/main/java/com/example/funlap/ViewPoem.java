package com.example.funlap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.funlap.adaptors.PoemListAdapter;
import com.example.funlap.adaptors.StoryListAdapter;
import com.example.funlap.model.Poem;
import com.example.funlap.model.Story;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ViewPoem extends AppCompatActivity {
    private Button backButton;
    private SwipeMenuListView listViews;
    private ArrayList<Poem> dataArrayList;
    private PoemListAdapter listAdapter;
    private FirebaseFirestore db ;
    private EditText txtPoem_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poem);
        txtPoem_search=findViewById(R.id.txtPoem_search);
        db = FirebaseFirestore.getInstance();
        backButton=findViewById(R.id.btn_back);
        listViews = (SwipeMenuListView) findViewById(R.id.view_poem_details);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewPoem.this,UserHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        txtPoem_search.addTextChangedListener(new TextWatcher() {
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

        db.collection("poems").whereEqualTo("poemTitle",res)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataArrayList = new ArrayList<Poem>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Poem poem=new Poem(document.getId(),document.get("poemTitle").toString(),document.get("poem").toString(),document.get("poemDescription").toString());

                                dataArrayList.add(poem);
                            }
                            listAdapter = new PoemListAdapter(ViewPoem.this, dataArrayList);
                            listViews.setAdapter(listAdapter);
                        } else {
                            Toast.makeText(ViewPoem.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getAllData(){

        db.collection("poems")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataArrayList = new ArrayList<Poem>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Poem poem=new Poem(document.getId(),document.get("poemTitle").toString(),document.get("poem").toString(),document.get("poemDescription").toString());

                                dataArrayList.add(poem);
                            }
                            listAdapter = new PoemListAdapter(ViewPoem.this, dataArrayList);
                            listViews.setAdapter(listAdapter);
                        } else {
                            Toast.makeText(ViewPoem.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}