package com.example.bucketlist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bucketlist.R;
import com.example.bucketlist.database.BucketlistDatabase;
import com.example.bucketlist.model.BucketlistItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {
    ArrayList<BucketlistItem> bucketlistItemList = new ArrayList<>();
    BucketlistAdapter bucketListadapter;
    RecyclerView rvBucketlist;
    private BucketlistDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();
    GestureDetector gestureDetector;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateItem.class);
                startActivityForResult(intent, 1);
            }
        });

        fillRecyclerView();
    }

    private void fillRecyclerView() {
        db = BucketlistDatabase.getDatabase(this);
        rvBucketlist = findViewById(R.id.rvBucketlist);
        bucketListadapter = new BucketlistAdapter(this, bucketlistItemList);
        rvBucketlist.setAdapter(bucketListadapter);
        rvBucketlist.setLayoutManager(new LinearLayoutManager(this));
        rvBucketlist.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = rvBucketlist.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = rvBucketlist.getChildAdapterPosition(child);
                    deleteItem(bucketlistItemList.get(adapterPosition));
                }
            }
        });

        rvBucketlist.addOnItemTouchListener(this);
        getAllItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");


                BucketlistItem item = new BucketlistItem(title, description);
                insertItem(item);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private void insertItem(final BucketlistItem item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketlistDao().insert(item);
                getAllItems();
            }
        });
    }

    private void updateUI(List<BucketlistItem> items) {
        bucketlistItemList.clear();
        bucketlistItemList.addAll(items);
        bucketListadapter.notifyDataSetChanged();
    }

    private void getAllItems() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final List<BucketlistItem> items = db.bucketlistDao().getAllItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(items);
                    }
                });
            }
        });
    }

    private void deleteItem(final BucketlistItem item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketlistDao().delete(item);
                getAllItems();
            }
        });
    }

    private void deleteAllItems(final List<BucketlistItem> item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketlistDao().delete(item);
                getAllItems();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            deleteAllItems(bucketlistItemList);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
       gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
