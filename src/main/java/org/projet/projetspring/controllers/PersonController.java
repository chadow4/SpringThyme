package org.projet.projetspring.controllers;

import org.projet.projetspring.dtos.FilterDto;
import org.projet.projetspring.dtos.PersonDto;
import org.projet.projetspring.services.PersonService;
import org.projet.projetspring.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private RelationshipService relationshipService;

    @GetMapping
    public String listPersons(Model model) {
        model.addAttribute("persons", personService.findAll());
        model.addAttribute("filterDto", new FilterDto("","", null, null));
        model.addAttribute("relationTypeList", relationshipService.getSortedRelationTypes());
        return "persons/list";
    }

    @GetMapping("/create")
    public String createPersonForm(Model model) {
        model.addAttribute("person", new PersonDto(null,"","",null, ""));
        return "persons/create";
    }
    @GetMapping("/{personId}")
    public String profil(@PathVariable Long personId, Model model) {
        model.addAttribute("person", personService.findById(personId));
        return "persons/profile";
    }

    @PostMapping("/create")
    public String createPerson(@ModelAttribute("person") PersonDto personDto, BindingResult result) {
        if (result.hasErrors()) {
            return "persons/create";
        }
        try {
            personService.createUser(personDto);
        } catch (Exception e) {
            result.addError(new ObjectError("user", "Cette personne n'est pas disponible"));
        }
        return "redirect:/persons";
    }


    @PostMapping("/filter")
    public String filterPerson(@ModelAttribute("filterDto") FilterDto filterDto, Model model, BindingResult result) {
        model.addAttribute("persons", personService.findSorted(filterDto));
        model.addAttribute("filterDto", new FilterDto("", (filterDto.condition() != null) ? filterDto.condition():"", null, null));
        model.addAttribute("relationTypeList", relationshipService.getSortedRelationTypes());
        return "persons/list";
    }

}
