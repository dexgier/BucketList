package com.example.bucketlist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.bucketlist.model.BucketlistItem;

import java.util.List;


@Dao
public interface BucketlistDao {

    @Insert
    void insert(BucketlistItem item);

    @Delete
    void delete(BucketlistItem item);

    @Delete
    void delete(List<BucketlistItem> items);

    @Query("Select * FROM bucketlist_table")
    List<BucketlistItem>getAllItems();
}
