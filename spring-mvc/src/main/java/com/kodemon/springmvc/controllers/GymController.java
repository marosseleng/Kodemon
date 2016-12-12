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
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * The controller of Gym
 *
 *  @author Michal Romanek
 */

@Controller
@RequestMapping("/gym")
public class GymController {

    final static Logger LOG = LoggerFactory.getLogger(GymController.class);

    @Inject
    private GymFacade gymFacade;

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
     * @param id of the chosen gym
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model) {
        GymDTO gym = gymFacade.findGymById(id);
        if(gym == null){
            LOG.warn("No gym with this id found");
            model.addAttribute("alert_warning", "No gym with this id found");
            model.addAttribute("gyms", gymFacade.findAll());
            return "/gym/list";
        }
        model.addAttribute("gym");
        return "gym/detail";
    }

    /**
     * Find gym by its id and show his detail page if exists or alert if not.
     *
     * @param id of the chosen gym
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(@RequestParam Long id, Model model) {
        GymDTO gym = gymFacade.findGymById(id);
        if(gym == null) {
            LOG.warn("No gym with this id found");
            model.addAttribute("alert_warning", "No gym with this id found");
            model.addAttribute("gyms", gymFacade.findAll());
            return "/gym/list";
        }
        model.addAttribute("gym");
        return "/gym/detail";
    }
}
