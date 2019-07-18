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

@Controller
public class PartController {

    private final PartRepository repository;

    private final PartService service;

    @Autowired
    public PartController(PartRepository repository, PartService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model,
                       @PageableDefault(sort = {"title"}, direction = Sort.Direction.ASC)
                               Pageable pageable){
        getMainPage(filter, model, pageable);
        return "index";
    }

    @GetMapping("/required")
    public String getRequared(@RequestParam(required = false, defaultValue = "") String filter,
                              Model model,
                              @PageableDefault(sort = {"title"}, direction = Sort.Direction.ASC)
                                      Pageable pageable){

        Page<Part> page = repository.findByRequired(true,pageable);
        model.addAttribute("sumComps",service.computersForAssembly());
        model.addAttribute("url","/required");
        model.addAttribute("parts", page);
        model.addAttribute("filter",filter);
        return "index";
    }


    @GetMapping("/norequired")
    public String getNoRequared(@RequestParam(required = false, defaultValue = "") String filter,
                                Model model,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC)
                                        Pageable pageable){

        Page<Part> page = repository.findByRequired(false,pageable);
        model.addAttribute("sumComps",service.computersForAssembly());
        model.addAttribute("url","/norequired");
        model.addAttribute("parts", page);
        model.addAttribute("filter",filter);
        return "index";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Part part = repository.getOne(id);
        model.addAttribute("part", part);
        return "operations/edit";
    }

    @PostMapping("/update")
    public String updateNote(@RequestParam ("id")Part updateItem,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "quantity", required = false, defaultValue = "0") Integer quantity,
                             @RequestParam(value = "required", required = false) boolean i_requared) {

        updateItem.setTitle(title);
        updateItem.setQuantity(quantity);
        updateItem.setRequired(i_requared);
        repository.save(updateItem);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newPart() {
        return "operations/new";
    }

    @PostMapping("/save")
    public String saveNote(@RequestParam(value = "title", required = false, defaultValue = "???") String title,
                           @RequestParam(value = "quantity", required = false, defaultValue = "0") Integer quantity,
                           @RequestParam(value = "required", required = false) boolean i_required) {
        repository.save(new Part(title, quantity, i_required));
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repository.delete(id);
        return "redirect:/";
    }


    private void getMainPage(@RequestParam(required = false, defaultValue = "") String filter,
                             Model model, @PageableDefault(sort = {"title"}, direction = Sort.Direction.ASC)
                                     Pageable pageable) {
        Page<Part> page = getPartList(filter, pageable) ;
        model.addAttribute("sumComps",service.computersForAssembly());
        model.addAttribute("url", "/");
        model.addAttribute("parts", page);
        model.addAttribute("filter",filter);
    }

    private Page<Part> getPartList(@RequestParam(required = false, defaultValue = "") String filter,
                                        @PageableDefault(sort = {"title"}, direction = Sort.Direction.ASC)
                                                Pageable pageable) {
        Page<Part> parts ;
        if(filter != null && !filter.isEmpty()) {
            parts = repository.findAllByTitleIsContainingIgnoreCase(filter, pageable);
        }else {
            parts = repository.findAll(pageable);
        }
        return parts;
    }

}
