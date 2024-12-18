package org.projet.projetspring.controllers;

import org.projet.projetspring.dtos.PersonDto;
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

import java.util.ArrayList;

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
        model.addAttribute("requestDto", new RequestDto(null, null, ""));
        return "relationship/create-relation";
    }

    @PostMapping("/create")
    public String createFriendship(RequestDto requestDto, Model model, RedirectAttributes redirectAttributes) {
        if (requestDto.fromUser() == null || requestDto.toUser() == null || requestDto.requestStr() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill all the required fields");
            return "redirect:/relationships/new";
        }
        try {
            relationshipService.createFriendship(requestDto);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while creating the friendship: " + e.getMessage());

            return "redirect:/relationships/new";
        }
        return "redirect:/relationships/" + requestDto.fromUser();
    }

    @GetMapping("/{personId}")
    public String getFriendships(@PathVariable Long personId, Model model) {
        Person person = personService.findById(personId);
        if (person == null) {
            return "redirect:/persons";
        }
        model.addAttribute("person", person);
        if (!person.getAllFriendships().isEmpty()) {
            model.addAttribute("relations", person.getAllFriendships());
        }
        return "relationship/friendships";
    }


    @GetMapping("/clusters")
    public String getClusters(Model model) {
        ArrayList<ArrayList<PersonDto>> clusters = this.personService.findAllRelatedComponents();
        model.addAttribute("clusters", clusters);
        return "relationship/clusters";
    }
}