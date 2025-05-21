package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
 
    }
    @GetMapping
    public String index(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("categoryObj", new Category());
        return "/category/index";
    }


  @PostMapping("/create")
   public String store(@Valid @ModelAttribute("categoryObj") Category category,
                    BindingResult bindingResult,
                    Model model) {

    if (categoryRepository.findByCatNomeIgnoreCase(category.getCatNome()).isPresent()) {
        bindingResult.rejectValue("catNome", "error.category", "Categoria esistente");
    }

    
    if (bindingResult.hasErrors()) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "/category/index";  // torna alla view con errori
    }

    categoryRepository.save(category);
    return "redirect:/category";
}

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        categoryRepository.deleteById(id);
        return "redirect:/category";
    }
}

