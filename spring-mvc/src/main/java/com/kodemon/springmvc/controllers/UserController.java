package com.kodemon.springmvc.controllers;

import com.kodemon.api.dto.UserAuthDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static com.kodemon.persistence.util.Constants.MAX_ACTIVE_POKEMON;
import static com.kodemon.persistence.util.Constants.MIN_USERNAME_LENGTH;

/**
 * @author Oliver Roch
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

    private UserFacade userFacade;
    private MessageSource messageSource;

    @Inject
    public UserController(UserFacade userFacade, MessageSource messageSource) {
        this.userFacade = userFacade;
        this.messageSource = messageSource;
    }

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
     * @param username           which trainer is to be shown
     * @param model              data to display
     * @param redirectAttributes attributes to use/display in case of redirect
     * @return JSP page name
     */
    @RequestMapping(value = "/detail/{username}", method = RequestMethod.GET)
    public String detail(@PathVariable String username, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        Collection<UserDTO> user = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(username);
        if (user.isEmpty()) {
            LOG.warn("No trainer with such username found");
            redirectAttributes.addFlashAttribute("alert_warning", getMessage("warning.user.noTrainerFound", locale));
            redirectAttributes.addFlashAttribute("users", userFacade.findAllUsers());
            return "redirect:/user/list";
        }
        model.addAttribute("trainer", user.iterator().next());
        return "user/detail";
    }

    /**
     * Find trainer by his username and show his detail page if exists or alert if not.
     *
     * @param username user with this username is to be found
     * @param model    data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(@RequestParam String username, Model model, Locale locale) {
        Collection<UserDTO> result;
        if (username.length() < MIN_USERNAME_LENGTH) {
            model.addAttribute("alert_warning", getMessage("warning.user.searchQueryShort", locale, MIN_USERNAME_LENGTH));
            result = userFacade.findAllUsers();
        } else {
            Collection<UserDTO> found = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(username);
            if (found.isEmpty()) {
                LOG.warn("No trainer with such username found");
                model.addAttribute("alert_warning", getMessage("warning.user.noTrainerFound", locale));
                result = userFacade.findAllUsers();
            } else {
                result = found;
            }
        }
        model.addAttribute("users", result);
        return "/user/list";
    }

    /**
     * @param username of trainer to be logged in
     * @param password of trainer to be logged in
     * @param r        servlet request to create session
     * @param model    data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, ServletRequest r, Model model, Locale locale) {
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setUserName(username);
        userAuthDTO.setPassword(password);

        HttpServletRequest request = (HttpServletRequest) r;
        if (userFacade.login(userAuthDTO)) {
            UserDTO authenticated = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(username).iterator().next();
            if (authenticated.isBlocked()) {
                model.addAttribute("alert_danger", getMessage("danger.user.accountBlocked", locale));
                return "login";
            }
            HttpSession session = request.getSession();
            session.setAttribute("authenticatedUser", authenticated);
            model.addAttribute("alert_success", getMessage("success.user.welcomeUser", locale, username));
            model.addAttribute("trainer", authenticated);
            return "user/detail";
        } else {
            model.addAttribute("alert_danger", getMessage("danger.user.incorrectCredentials", locale));
            return "login";
        }
    }

    /**
     * Block specified user. Only administrator can block other user and even admin cannot block another admin.
     * User remain blocked until admin unblocks him.
     *
     * @param username Username of user to be blocked
     * @param r        servlet request to get logged user
     * @param model    Data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/blockUser", method = RequestMethod.POST)
    public String blockUser(@RequestParam String username, ServletRequest r, Model model, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        UserDTO toBeBlocked = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(username).iterator().next();
        if (toBeBlocked == null) {
            model.addAttribute("alert_warning", getMessage("warning.user.doesNotExist", locale));
            LOG.error("Trying to block non-existing user");
            return "user/list";
        }
        model.addAttribute("trainer", toBeBlocked);
        if (session.getAttribute("authenticatedUser") == null ||
                !(((UserDTO) session.getAttribute("authenticatedUser")).isAdmin())) {
            model.addAttribute("alert_warning", getMessage("warning.user.insufficientPermissions", locale));
            LOG.error("User is not admin.");
        } else if (toBeBlocked.isAdmin()) {
            model.addAttribute("alert_warning", getMessage("warning.user.adminCannotBlockOther", locale));
            LOG.error("Admin trying to block admin");
        } else {
            LOG.info("User blocked");
            userFacade.setBlocked(toBeBlocked.getId(), true);
            model.addAttribute("alert_success", getMessage("success.user.userBlocked", locale));
        }
        return "user/detail";
    }

    /**
     * Unblock specified user if he is blocked. Only administrator can unblock user.
     *
     * @param username Username of user to be unblocked
     * @param r        servlet request to get logged user
     * @param model    Data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/unblockUser", method = RequestMethod.POST)
    public String unblockUser(@RequestParam String username, ServletRequest r, Model model, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        UserDTO toBeUnblocked = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(username).iterator().next();
        if (toBeUnblocked == null) {
            model.addAttribute("alert_warning", getMessage("warning.user.doesNotExist", locale));
            LOG.error("Trying to unblock non-existing user");
            return "user/list";
        }
        model.addAttribute("trainer", toBeUnblocked);
        if (session.getAttribute("authenticatedUser") == null ||
                !(((UserDTO) session.getAttribute("authenticatedUser")).isAdmin())) {
            model.addAttribute("alert_warning", getMessage("warning.user.insufficientPermissions", locale));
            LOG.error("User is not admin.");
        } else {
            LOG.info("User unblocked");
            userFacade.setBlocked(toBeUnblocked.getId(), false);
            model.addAttribute("alert_success", getMessage("success.user.userUnblocked", locale));
        }
        return "user/detail";
    }

    /**
     * Function to logout user.
     *
     * @param r     servlet request to get logged user
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ServletRequest r, Model model, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticatedUser") == null) {
            model.addAttribute("alert_warning", getMessage("warning.user.notLoggedIn", locale));
            return "login";
        }
        LOG.info("User logged out");
        session.removeAttribute("authenticatedUser");
        model.addAttribute("alert_success", getMessage("success.user.userLoggedOut", locale));
        return "login";
    }

    private String getMessage(String code, Locale locale, Object... args) {
        return messageSource.getMessage(code, args, locale);
    }

    /**
     * Page for selecting first 6 pokemons to be used for fight
     *
     * @param r      servlet request to get logged user
     * @param model  data to display
     * @param locale locale settings
     * @return JSP page name
     */
    @RequestMapping(value = "/reorder", method = RequestMethod.GET)
    public String reorder(ServletRequest r, Model model, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticatedUser") == null) {
            model.addAttribute("alert_warning", getMessage("warning.user.notLoggedIn", locale));
            return "login";
        }
        UserDTO currentUser = (UserDTO) session.getAttribute("authenticatedUser");
        model.addAttribute("pokemons", currentUser.getPokemons());
        model.addAttribute("activePokemons", currentUser.getActivePokemons());
        model.addAttribute("numberOfPokemonsForFight", MAX_ACTIVE_POKEMON);
        return "user/reorder";
    }

    @RequestMapping(value = "setFirstSixPokemons", method = RequestMethod.POST)
    public String setFirstSixPokemons(ServletRequest r, Model model, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();

        if (session.getAttribute("authenticatedUser") == null) {
            model.addAttribute("alert_warning", getMessage("warning.user.notLoggedIn", locale));
            return "login";
        }

        List<Integer> pokemonIndices = new ArrayList<>();
        for(String i : request.getParameterValues("pokemon")) {
            pokemonIndices.add(Integer.valueOf(i));
        }

        UserDTO currentUser = (UserDTO) session.getAttribute("authenticatedUser");
        userFacade.chooseActivePokemons(currentUser.getId(), pokemonIndices);

        model.addAttribute("alert_success", getMessage("success.reorder.changed", locale));
        UserDTO user = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(currentUser.getUserName()).iterator().next();
        model.addAttribute("trainer", user);

        return "user/detail";
    }
}
