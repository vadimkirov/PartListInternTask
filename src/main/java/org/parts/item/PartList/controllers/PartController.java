package org.parts.item.PartList.controllers;

import org.parts.item.PartList.model.Part;
import org.parts.item.PartList.repository.PartRepository;
import org.parts.item.PartList.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PartController {

    private final PartRepository repository;

    private PartService service;


    @Autowired
    public PartController(PartRepository repository) {
        this.repository = repository;

    }

    @Autowired
    public void setNoteService(PartService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String list(Model model,
                       @PageableDefault(sort = {"title"}, direction = Sort.Direction.ASC)
                               Pageable pageable) {
        Page<Part> notebook = repository.findAllByOrderByTitleAsc(pageable);


        model.addAttribute("parts", notebook);


        model.addAttribute("comps", computersForAssembly());

        return "index";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Part part = repository.getOne(id);
        model.addAttribute("part", part);
        return "operations/edit";
    }

    @PostMapping("/update")
    public String updateNote(@RequestParam Integer id,
                             @RequestParam(value = "title", required = false, defaultValue = "???") String title,
                             @RequestParam(value = "quantity", required = false, defaultValue = "0") Integer quantity,
                             @RequestParam(value = "i_requared", required = false) boolean i_requared) {
        service.updateNote(id, title, quantity, i_requared);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newNote() {
        return "operations/new";
    }

    @PostMapping("/save")
    public String saveNote(@RequestParam(value = "title", required = false, defaultValue = "???") String title,
                           @RequestParam(value = "quantity", required = false, defaultValue = "0") Integer quantity,
                           @RequestParam(value = "i_requared", required = false) boolean i_requared) {
        service.saveNote(new Part(title, quantity, i_requared));
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteNote(id);
        return "redirect:/";
    }


    private int computersForAssembly() {
        int quantity = -1;

        List<Part> onlyNecessaryParts = repository.findAll();
        for (Part item : onlyNecessaryParts) {
            if (item.isI_requared() && quantity == -1) {
                quantity = item.getQuantity();
            }
            if (quantity > -1 && item.isI_requared()) {
                if (quantity > item.getQuantity()) {
                    quantity = item.getQuantity();
                }
            }
        }

        if (quantity == -1) return 0;
        return quantity;
    }
}
