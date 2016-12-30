package com.kodemon.springmvc.controllers;

import com.kodemon.api.dto.UserRegisterDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.springmvc.validator.UserRegisterDTOValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by mseleng on 12/29/16.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserFacade userFacade;

    @Inject
    public RegistrationController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @ModelAttribute("pokemon")
    public List<PokemonName> availablePokemon() {
        return PokemonName.getInitialPokemon();
    }

    @GetMapping
    public String initRegistration(Model model) {
        model.addAttribute("userRegister", new UserRegisterDTO());
        return "register";
    }

    @PostMapping(path = "/finish")
    public String register(
            @Valid @ModelAttribute("userRegister") UserRegisterDTO userRegister,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
//                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
//                log.trace("FieldError: {}", fe);
            }
            return "";
        }
        userFacade.register(userRegister);
        redirectAttributes.addFlashAttribute("alert_success", "You have been successfully registered.");
        return "redirect:/login";
    }

    /**
     * Spring Validator added to JSR-303 Validator for this @Controller only.
     * It is useful  for custom validations that are not defined on the form bean by annotations.
     * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof UserRegisterDTO) {
            binder.addValidators(new UserRegisterDTOValidator());
        }
    }
}
