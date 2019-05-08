package com.example.topnote;

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

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton fab_btn;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        final EditText title = mview.findViewById(R.id.title);
        EditText budget = mview.findViewById(R.id.budget);
        EditText note = mview.findViewById(R.id.note);

        Button btnSave = mview.findViewById(R.id.save_btn);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mTitle = title.getText().toString().trim();
                String mBudget = title.getText().toString().trim();
                String mNote = title.getText().toString().trim();

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
            }
        });

        AlertDialog dialog = mydialog.create();

        dialog.show();

    }
}
