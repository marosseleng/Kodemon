package com.kodemon.springmvc.controllers;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.UserFacade;
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
 *  @author Oliver Roch
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Inject
    private UserFacade userFacade;

    /**
     * Show list of all trainers.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userFacade.findAllUsers());
        return "user/list";
    }

    /**
     * Show detail of trainer specified by his username
     *
     * @param username which trainer is to be shown
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/detail/{username}", method = RequestMethod.GET)
    public String detail(@PathVariable String username, Model model) {
        model.addAttribute("trainer", userFacade.findUserByUserName(username).iterator().next());
        return "user/detail";
    }

    /**
     * Find trainer by his username and show his detail page if exists or alert if not.
     *
     * @param username user with this username is to be found
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(@RequestParam String username, Model model) {
        Collection<UserDTO> user = userFacade.findUserByUserName(username);
        if(user.isEmpty()) {
            model.addAttribute("alert_warning", "No trainer with such username found");
            model.addAttribute("users", userFacade.findAllUsers());
            return "/user/list";
        }
        model.addAttribute("trainer", userFacade.findUserByUserName(username).iterator().next());
        return "/user/detail";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, ServletRequest r, Model model) {
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUserName(username);
        userAuthDTO.setPwdHash(password);

        HttpServletRequest request = (HttpServletRequest) r;
        if(userFacade.login(userAuthDTO)) {
            UserDTO authenticated = userFacade.findUserByUserName(username).iterator().next();
            HttpSession session = request.getSession();
            session.setAttribute("authenticatedUser", authenticated);
            model.addAttribute("alert_success", "Welcome "+username);
            model.addAttribute("trainer", authenticated);
            return "/user/detail";
        } else {
            model.addAttribute("alert_danger", "Incorrect username or password ");
            return "/login";
        }
    }
}
