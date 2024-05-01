package com.example.applydigitalchallenge.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.applydigitalchallenge.data.model.PostEntity

/**
 * The PostDao interface defines methods for accessing and manipulating data in the post_table of the database.
 * It is annotated with @Dao, indicating that it is a Data Access Object (DAO) interface for Room.
 */
@Dao
interface PostDao {

    /**
     * Retrieves all posts from the post_table.
     *
     * @return A list of PostEntity objects representing all posts in the database.
     */
    @Query("SELECT * FROM post_table")
    fun getAll(): List<PostEntity>

    /**
     * Retrieves a specific post from the post_table based on the provided postId.
     *
     * @param postId The ID of the post to retrieve.
     * @return The PostEntity object representing the post with the specified ID.
     */
    @Query("SELECT * FROM post_table WHERE postId = :postId")
    fun getItem(postId: Long): PostEntity

    /**
     * Deletes all posts from the post_table.
     */
    @Query("DELETE FROM post_table")
    fun deleteAllPosts()

    /**
     * Inserts a new post into the post_table.
     *
     * @param post The PostEntity object representing the post to insert.
     */
    @Insert
    fun insert(post: PostEntity)

    /**
     * Deletes a specific post from the post_table.
     *
     * @param post The PostEntity object representing the post to delete.
     */
    @Delete
    fun delete(post: PostEntity)
}