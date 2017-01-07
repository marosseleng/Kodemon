package com.kodemon.springmvc.controllers;

import com.kodemon.api.dto.*;
import com.kodemon.api.enums.WildPokemonFightMode;
import com.kodemon.api.facade.FightFacade;
import com.kodemon.api.facade.GymFacade;
import com.kodemon.api.facade.PokemonFacade;
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

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Matej Poklemba
 */

@Controller
@RequestMapping("/fight")
public class FightController {

    final static Logger LOG = LoggerFactory.getLogger(FightController.class);

    private FightFacade fightFacade;
    private UserFacade userFacade;
    private PokemonFacade pokemonFacade;
    private GymFacade gymFacade;
    private MessageSource messageSource;

    @Inject
    public FightController(
            FightFacade fightFacade,
            UserFacade userFacade,
            PokemonFacade pokemonFacade,
            GymFacade gymFacade,
            MessageSource messageSource) {
        this.fightFacade = fightFacade;
        this.userFacade = userFacade;
        this.pokemonFacade = pokemonFacade;
        this.gymFacade = gymFacade;
        this.messageSource = messageSource;
    }

    /**
     * Show list of all fights.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam String period, Model model) {
        Collection<FightDTO> fights;
        switch (period) {
            case "year":
                fights = fightFacade.listThisYearsFights();
                break;
            case "month":
                fights = fightFacade.listThisMonthsFights();
                break;
            case "today":
                fights = fightFacade.listTodaysFights();
                break;
            default:
                fights = fightFacade.listAllFights();
                break;
        }
        model.addAttribute("fights", fights);
        LOG.debug("Viewing fights of {}", period);
        return "fight/list";
    }

    /**
     * Show list of given gym's fights.
     *
     * @param id    id of gym to display fights of
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/listFightsOfGym", method = RequestMethod.GET)
    public String listFightsOfGym(@RequestParam Long id, Model model, Locale locale) {
        GymDTO gym = gymFacade.findGymById(id);
        Collection<FightDTO> fights = fightFacade.listFightsOfGym(gym);
        if (fights.isEmpty()) {
            model.addAttribute("alert_warning", getMessage("warning.fight.noFightsOfGym", locale));
            model.addAttribute("gym", gym);
            LOG.debug("Tried to look up fights of '{}' Gym - the gym doesn't have any fight history.", gym.getCity());
            return "gym/detail";
        }
        model.addAttribute("fights", fights);
        LOG.debug("Viewing fights of '{}' Gym", gym.getCity());
        return "fight/list";
    }

    /**
     * Show list of given user's fights.
     *
     * @param username which user's fights do display
     * @param model    data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/listFightsOfUser", method = RequestMethod.GET)
    public String listFightsOfUser(@RequestParam String username, Model model, Locale locale) {

        Collection<UserDTO> user = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(username);
        if (user.isEmpty()) {
            model.addAttribute("alert_warning", getMessage("warning.fight.noTrainerWithSuchUsername", locale));
            LOG.error("Tried to look up fights of '{}' - user doesn't exists.", username);
            return "home";
        }
        Collection<FightDTO> fights = fightFacade.listFightsOfTrainer(userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(username).iterator().next());
        if (fights.isEmpty()) {
            model.addAttribute("alert_warning", getMessage("warning.fight.noFightsOfUser", locale));
            model.addAttribute("trainer", userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(username).iterator().next());
            LOG.debug("Tried to look up fights of '{}' - user doesn't have any fight history.", username);
            return "user/detail";
        }
        model.addAttribute("fights", fights);
        LOG.debug("Viewing fights of user '{}'", username);
        return "fight/list";
    }

    @RequestMapping(value = "/grass", method = RequestMethod.GET)
    public String grass(ServletRequest r, Model model, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticatedUser") == null) {
            model.addAttribute("alert_warning", getMessage("warning.fight.notLoggedIn", locale));
            LOG.error("User not logged in.");
            return "home";
        }
        Collection<UserDTO> users = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(((UserDTO) session.getAttribute("authenticatedUser")).getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", getMessage("warning.fight.userNotFound", locale));
            LOG.error("Error: User not found in database!");
            return "home";
        }
        UserDTO user = users.iterator().next();
        PokemonDTO wildPokemon = pokemonFacade.generateWildPokemon(user);
        session.setAttribute("wildPokemon", wildPokemon);
        model.addAttribute("wildPokemon", wildPokemon);
        model.addAttribute("trainersPokemon", user.getActivePokemons().get(0));
        LOG.debug("A wild {} level {} appeared for {}", wildPokemon.getName().getName(), wildPokemon.getLevel(), user.getUserName());
        return "fight/grass";
    }


    @RequestMapping(value = "/fightWild", method = RequestMethod.GET)
    public String fightWild(@RequestParam String mode, ServletRequest r, Model model, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        if (mode.equals("run")) {
            model.addAttribute("alert_success", getMessage("success.fight.ranAway", locale));
            return "home";
        }
        WildPokemonFightMode mode_;
        if (mode.equals("train")) {
            mode_ = WildPokemonFightMode.TRAIN;
        }
        else if (mode.equals("catch")) {
            mode_ = WildPokemonFightMode.CATCH;
        }
        else {
            model.addAttribute("alert_warning", getMessage("warning.fight.incorrectMode", locale));
            return "home";
        }
        PokemonDTO wildPokemon = (PokemonDTO) session.getAttribute("wildPokemon");
        if (wildPokemon == null) {
            return "home";
        }
        session.removeAttribute("wildPokemon");
        if ((UserDTO) session.getAttribute("authenticatedUser") == null) {
            model.addAttribute("alert_warning", getMessage("warning.fight.notLoggedIn", locale));
            LOG.error("User not logged in.");
            return "home";
        }

        Collection<UserDTO> users = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(((UserDTO) session.getAttribute("authenticatedUser")).getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", getMessage("warning.fight.userNotFound", locale));
            LOG.error("Error: User not found in database!");
            return "home";
        }
        UserDTO user = users.iterator().next();

        boolean fightResult = fightFacade.fightWildPokemon(user, wildPokemon, mode_);
        String wildPokemonName = wildPokemon.getName().getName();
        PokemonDTO userPokemon = user.getActivePokemons().get(0);
        String userPokemonName = userPokemon.getName().getName();
        if (fightResult) {
            int userPokemonOldLevel = userPokemon.getLevel();
            model.addAttribute("alert_success", (mode_ == WildPokemonFightMode.CATCH ?
                    getMessage("success.fight.pokemonCaught", locale, wildPokemonName) :
                    getMessage(
                            "success.fight.wildPokemonFainted",
                            locale,
                            wildPokemonName, userPokemonName, userPokemonOldLevel + 1)));
        } else {
            model.addAttribute("alert_warning", (mode_ == WildPokemonFightMode.CATCH ?
                    getMessage("success.fight.pokemonNotCaught", locale, wildPokemonName) :
                    getMessage("success.fight.ownPokemonFainted", locale, userPokemonName)));
        }
        users = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(((UserDTO) session.getAttribute("authenticatedUser")).getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", getMessage("warning.fight.userNotFound", locale));
            LOG.error("Error: User not found in database!");
            return "home";
        }
        user = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(user.getUserName()).iterator().next();
        session.setAttribute("authenticatedUser", user);

        LOG.debug("{} {} wild {} level {} -> {}", user.getUserName(), (mode_ == WildPokemonFightMode.CATCH ? " catching" : " fighting"), wildPokemon.getName().getName(), wildPokemon.getLevel(), (fightResult ? "Success!" : "Failed"));
        wildPokemon = pokemonFacade.generateWildPokemon(user);
        session.setAttribute("wildPokemon", wildPokemon);
        model.addAttribute("wildPokemon", wildPokemon);
        model.addAttribute("trainersPokemon", user.getActivePokemons().get(0));
        LOG.debug("A wild {} level {} appeared for {}", wildPokemon.getName().getName(), wildPokemon.getLevel(), user.getUserName());
        return "fight/grass";
    }

    @RequestMapping(value = "/fightGym", method = RequestMethod.GET)
    public String fightGym(@RequestParam Long id, ServletRequest r, Model model, Locale locale) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            model.addAttribute("alert_warning", getMessage("warning.fight.notLoggedIn", locale));
            LOG.error("User not logged in.");
            return "home";
        }
        Collection<UserDTO> users = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(userDTO.getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", getMessage("warning.fight.userNotFound", locale));
            LOG.error("Error: User not found in database!");
            return "home";
        }
        userDTO = users.iterator().next();
        GymDTO gym = gymFacade.findGymById(id);
        if (gym == null) {
            model.addAttribute("alert_warning", getMessage("warning.fight.gymNotFound", locale));
            LOG.error("Error: Gym ID {} not found in database!", id);
            return "home";
        }
        for (BadgeDTO b : userDTO.getBadges()) {
            if (b.getName().equals(gym.getBadgeName())) {
                model.addAttribute("alert_warning", getMessage("warning.fight.youHaveBeatenGym", locale));
                LOG.error("User {} has already beaten {} Gym.", userDTO.getUserName(), gym.getCity());
                return "home";
            }
        }
        if (gym.getTrainer().getUserName().equals(userDTO.getUserName())) {
            model.addAttribute("alert_warning", getMessage("warning.fight.cannotFightOwnGym", locale));
            LOG.error("User {} owns {} Gym.", userDTO.getUserName(), gym.getCity());
            return "home";
        }
        boolean fightResult = fightFacade.fightForBadge(userDTO, gym);
        model.addAttribute("fightResult", fightResult);
        if (fightResult)
            model.addAttribute("alert_success", getMessage(
                    "success.fight.youBeatTrainerYouReceiveBadge",
                    locale,
                    gym.getTrainer().getUserName(), gym.getBadgeName()));
        else
            model.addAttribute("alert_warning", getMessage("warning.fight.youLost", locale));
        users = userFacade.findUserByUserNameIgnoringCaseIncludeSubstrings(userDTO.getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", getMessage("warning.fight.userNotFound", locale));
            LOG.error("Error: User not found in database!");
            return "home";
        }
        userDTO = users.iterator().next();
        session.setAttribute("authenticatedUser", userDTO);
        LOG.debug("{} is fighting [} Gym -> {}", userDTO.getUserName(), gym.getCity(), (fightResult ? "Success!" : "Failed"));
        return "home";
    }

    /**
     * Show detail of fight specified by its id
     *
     * @param id    which id is to be shown
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, Locale locale) {
        FightDTO fight = fightFacade.findFightById(id);
        if (fight == null) {
            model.addAttribute("alert_warning", getMessage("warning.fight.fightNotFound", locale));
            LOG.error("Error: Fight ID {} not found in database!", id);
            return "home";
        }
        model.addAttribute("fight", fight);
        LOG.debug("Viewing detail of fight {}", id);
        return "fight/detail";
    }

    private String getMessage(String code, Locale locale, Object... args) {
        return messageSource.getMessage(code, args, locale);
    }
}
