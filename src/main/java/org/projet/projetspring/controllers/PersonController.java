package org.projet.projetspring.controllers;

import org.projet.projetspring.dtos.PersonDto;
import org.projet.projetspring.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public String listPersons(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "persons/list";
    }

    @GetMapping("/create")
    public String createPersonForm(Model model) {
        model.addAttribute("person", new PersonDto());
        return "persons/create";
    }

    @PostMapping("/create")
    public String createPerson(PersonDto personDto, BindingResult result) {
        try {
            personService.createUser(personDto);
        } catch (Exception e) {
            result.addError(new ObjectError("user", "Cette personne n'est pas disponible"));
        }
        return "redirect:/persons";
    }
}
