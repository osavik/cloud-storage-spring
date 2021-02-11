package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    // insert Note
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    // get Note by noteName & userId
    @Select("SELECT * FROM NOTES WHERE notename = #{noteName} AND userid = #{userId}")
    Note getNoteByNotenameAndUserId(Integer userId, String noteName);

    // get Note by noteId
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}")
    Note getNoteByNoteIdAndUserId(Integer userId, Integer noteId);

    // get all Notes by userId
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllNotesByUserId(Integer userId);

    // delete Note by noteId
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}")
    void deleteNoteByNoteIdAndUserId(Integer userId, Integer noteId);

    // update Note by noteId
    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription =#{noteDescription} WHERE" +
            " noteid = #{noteId} AND userid = #{userId}")
    Integer updateNoteByNoteIdAndUserId(Integer userId, Integer noteId, String noteTitle, String noteDescription);

}
