package org.projet.projetspring.controllers;

import org.projet.projetspring.dtos.RequestDto;
import org.projet.projetspring.models.Person;
import org.projet.projetspring.services.PersonService;
import org.projet.projetspring.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/relationships")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private PersonService personService;

    @GetMapping("/new")
    public String showCreateFriendshipForm(Model model) {
        model.addAttribute("people", personService.findAll());
        model.addAttribute("requestDto", new RequestDto());
        return "relationship/create-relation";
    }

    @PostMapping("/create")
    public String createFriendship(RequestDto requestDto, Model model, RedirectAttributes redirectAttributes) {
        if (requestDto.getFromUser() == null || requestDto.getToUser() == null || requestDto.getRequestStr() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill all the required fields");
            return "redirect:/relationships/new";
        }
        try {
            relationshipService.createFriendship(requestDto);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while creating the friendship: " + e.getMessage());

            return "redirect:/relationships/new";
        }
        return "redirect:/relationships/" + requestDto.getFromUser();
    }

    @GetMapping("/{personId}")
    public String getFriendships(@PathVariable Long personId, Model model) {
        Person person = personService.findById(personId);
        if (person == null) {
            return "redirect:/persons";
        }
        model.addAttribute("person", person);
        if (!person.getAllFriendships().isEmpty()) {
            System.out.println("Liste des amis de " + person.getFirstName() + ": " + person.getAllFriendships());
            model.addAttribute("relations", person.getAllFriendships());
        }
        return "relationship/friendships";
    }
}