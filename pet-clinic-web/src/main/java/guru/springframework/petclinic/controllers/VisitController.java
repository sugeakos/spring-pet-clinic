package guru.springframework.petclinic.controllers;

import guru.springframework.petclinic.model.Pet;
import guru.springframework.petclinic.model.Visit;
import guru.springframework.petclinic.services.PetService;
import guru.springframework.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequestMapping()
@Controller
public class VisitController {
    private static final String VIEW_VISIT_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Model model){
        Pet pet = petService.findById(petId);
        model.addAttribute("pet",pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;

    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initVisitForm(@PathVariable Long petId, Model model){
        return VIEW_VISIT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Validated Visit visit, BindingResult result){
        if (result.hasErrors()){
            return VIEW_VISIT_CREATE_OR_UPDATE_FORM;

        } else {
            visitService.save(visit);
            return "redirect:/owners/{ownerId}" ;
        }

    }

}
