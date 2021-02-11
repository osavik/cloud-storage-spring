package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Form.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userId){
        return noteMapper.getAllNotesByUserId(userId);
    }

    public int saveNote(Integer userId, NoteForm noteForm){
        Note note = new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId);

        return noteMapper.insertNote(note);
    }

    public void deleteNoteByUserIdAndNoteId(Integer userId, Integer noteId){
        noteMapper.deleteNoteByNoteIdAndUserId(userId, noteId);
    }

    public int updateNoteByUserIdAndNoteId(Integer userId, NoteForm noteForm){
        return noteMapper.updateNoteByNoteIdAndUserId(userId, noteForm.getNoteId(),
                noteForm.getNoteTitle(), noteForm.getNoteDescription());
    }

}
