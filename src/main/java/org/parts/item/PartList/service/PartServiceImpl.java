package org.parts.item.PartList.service;

import org.parts.item.PartList.model.Part;
import org.parts.item.PartList.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl implements PartService {

    private PartRepository repository;

    @Autowired
    public void setProductRepository(PartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Part getNoteById(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public void saveNote(Part part) {
        repository.save(part);
    }

    @Override
    public void updateNote(Integer id, String title, Integer quantity, boolean surely) {
        Part updated = repository.findOne(id);
        updated.setI_requared(surely);
        updated.setTitle(title);
        updated.setQuantity(quantity);
        repository.save(updated);
    }

    @Override
    public void deleteNote(Integer id) {
        repository.delete(id);
    }

}
