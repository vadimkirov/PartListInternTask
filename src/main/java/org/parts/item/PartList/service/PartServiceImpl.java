package org.parts.item.PartList.service;

import org.parts.item.PartList.model.Part;
import org.parts.item.PartList.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository repository;

    @Autowired
    public PartServiceImpl(PartRepository repository) {
        this.repository = repository;
    }

    @Override
    public int computersForAssembly() {
        if(repository.findByRequiredIsTrue().size() == 0){
            return 0;
        }
        int quantity = repository.findByRequiredIsTrue().get(0).getQuantity();
        for  (Part item: repository.findByRequiredIsTrue()){
            if(item.getQuantity()< quantity){
                quantity = item.getQuantity();
            }
        }
        return quantity;
    }
}
