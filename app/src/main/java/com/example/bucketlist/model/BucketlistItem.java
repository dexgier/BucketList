package com.example.bucketlist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bucketlist_table")
public class BucketlistItem {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String title;
    private String description;

    public BucketlistItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {return title;}
    public String getDescription(){return description;}
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}


}
