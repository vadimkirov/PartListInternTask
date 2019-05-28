package org.parts.item.PartList.service;

import org.parts.item.PartList.model.Part;

public interface PartService {

    Part getNoteById(Integer id);

    void saveNote(Part part);

    void updateNote(Integer id, String title, Integer quantity, boolean surely);

    void deleteNote(Integer id);
}
