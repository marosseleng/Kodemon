package com.kodemon.springmvc.controllers;

import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.facade.GymFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

/**
 * The controller of Gym
 *
 * @author Michal Romanek
 */

@Controller
@RequestMapping("/gym")
public class GymController {

    final static Logger LOG = LoggerFactory.getLogger(GymController.class);

    private GymFacade gymFacade;

    @Inject
    public GymController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    /**
     * Show list of all gyms.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("gyms", gymFacade.findAll());
        return "gym/list";
    }

    /**
     * Show detail of gym specified by its id
     *
     * @param id                 of the chosen gym
     * @param model              data to display
     * @param redirectAttributes attributes to use/display in case of redirect
     * @return JSP page name
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        GymDTO gym = gymFacade.findGymById(id);
        if (gym == null) {
            LOG.warn("No gym with this id found");
            redirectAttributes.addFlashAttribute("alert_warning", "No gym with this id found");
            redirectAttributes.addFlashAttribute("gyms", gymFacade.findAll());
            return "redirect:/gym/list";
        }
        model.addAttribute("gym", gym);
        return "gym/detail";
    }
}
