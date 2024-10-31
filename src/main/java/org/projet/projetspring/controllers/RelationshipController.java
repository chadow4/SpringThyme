package org.projet.projetspring.controllers;

import org.projet.projetspring.models.Person;
import org.projet.projetspring.services.PersonService;
import org.projet.projetspring.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        return "relationship/create-friendship";
    }

    @PostMapping("/create")
    public String createFriendship(@RequestParam Long person1Id, @RequestParam Long person2Id, Model model, RedirectAttributes redirectAttributes) {
        try {
            relationshipService.createFriendship(person1Id, person2Id);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while creating the friendship: " + e.getMessage());

            return "redirect:/relationships/new";
        }
        return "redirect:/relationships/" + person1Id;
    }

    @GetMapping("/{personId}")
    public String getFriendships(@PathVariable Long personId, Model model) {
        Person person = personService.findById(personId);
        model.addAttribute("person", person);
        ;
//        List<Relationship> friendships = relationshipService.getFriendshipsByPersonId(personId);
//        model.addAttribute("friendships", friendships);
        return "relationship/friendships";
    }
}