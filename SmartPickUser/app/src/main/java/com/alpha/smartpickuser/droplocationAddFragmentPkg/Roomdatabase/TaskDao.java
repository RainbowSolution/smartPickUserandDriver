package com.alpha.smartpickuser.droplocationAddFragmentPkg.Roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alpha.smartpickuser.droplocationAddFragmentPkg.PlaceModel;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM placemodel")
    List<PlaceModel> getAll();

    @Insert
    void insert(PlaceModel task);

    @Delete
    void delete(PlaceModel task);

    @Update
    void update(PlaceModel task);

    @Query("SELECT COUNT(*) FROM placemodel")
    String getCount();

    @Query("DELETE FROM placemodel")
    void delete();

    @Query("UPDATE placemodel SET receivername = :receivername, receivernumber = :receivernumber WHERE product_id =:id")
    void updatea(String receivername, String receivernumber, String id);
}