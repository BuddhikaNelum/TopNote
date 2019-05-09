package com.example.topnote;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topnote.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.DatabaseMetaData;
import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton fab_btn;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();

        String uid = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("AllData").child(uid);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("TopNote");
        setSupportActionBar(toolbar);

        fab_btn = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }

    public void addData() {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View mview = inflater.inflate(R.layout.add_data,null);
        mydialog.setView(mview);

        final AlertDialog dialog = mydialog.create();

        final EditText title = mview.findViewById(R.id.title);
        final EditText budget = mview.findViewById(R.id.budget);
        final EditText note = mview.findViewById(R.id.note);

        Button btnSave = mview.findViewById(R.id.save_btn);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mTitle = title.getText().toString().trim();
                String mBudget = budget.getText().toString().trim();
                String mNote = note.getText().toString().trim();

                if (TextUtils.isEmpty(mTitle)) {
                    title.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(mBudget)) {
                    title.setError("Required Field");
                }
                if (TextUtils.isEmpty(mNote)) {
                    title.setError("Required Field");
                }

                String id = mDatabase.push().getKey();

                String date = DateFormat.getDateInstance().format(new Date());

                Data mData = new Data(id, mTitle, mBudget, mNote, date);

                mDatabase.child(id).setValue(mData);

                dialog.dismiss();
            }
        });



        dialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data, MyviewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyviewHolder>(
                Data.class,
                R.layout.showdata,
                MyviewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(MyviewHolder viewHolder, Data model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setNote(model.getNote());
                viewHolder.setBudget(model.getBudget());
                viewHolder.setDate(model.getDate());

            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder {

        View myView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
        }

        public void setTitle(String title) {
            TextView mTitle = myView.findViewById(R.id.sh_title);
            mTitle.setText(title);
        }

        public void setNote(String note) {
            TextView mNote = myView.findViewById(R.id.sh_note);
            mNote.setText(note);
        }

        public void setBudget(String budget) {
            TextView mBudget = myView.findViewById(R.id.sh_budget);
            mBudget.setText("$ "+budget);
        }

        public void setDate(String date) {
            TextView mDate = myView.findViewById(R.id.sh_date);
            mDate.setText(date);
        }
    }
}
