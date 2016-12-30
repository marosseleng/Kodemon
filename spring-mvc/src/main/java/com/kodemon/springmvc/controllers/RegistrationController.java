package com.kodemon.springmvc.controllers;

import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.dto.UserRegisterDTO;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.springmvc.validator.UserRegisterDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mseleng on 12/29/16.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

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
        LOG.debug("Performing registration of user with username: {}.", userRegister.getUserName());
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                LOG.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                LOG.trace("FieldError: {}", fe);
            }
            return "register";
        }
        try {
            if (userFacade.register(userRegister) != null) {
                redirectAttributes.addFlashAttribute("alert_success", "You have been successfully registered.");
                return "redirect:/login";
            } else {
                model.addAttribute("alert_error", "Registration unsuccessful, please try again later.");
                LOG.error("Error while registering user with username: {}", userRegister.getUserName());
                return "register";
            }
        } catch (DataAccessException ex) {
            model.addAttribute("alert_error", "Registration unsuccessful, please try again later.");
            LOG.error("Error while registering user with username: {}", userRegister.getUserName(), ex);
            return "register";
        }
    }

    /**
     * Spring Validator added to JSR-303 Validator for this @Controller only.
     * It is useful  for custom validations that are not defined on the form bean by annotations.
     * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-mvc-configuring
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() instanceof UserRegisterDTO) {
            binder.addValidators(new UserRegisterDTOValidator(userFacade));
            // awesome time handling...
            SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }
    }
}
