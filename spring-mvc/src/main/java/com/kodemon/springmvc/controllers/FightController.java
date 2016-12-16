package com.kodemon.springmvc.controllers;

import com.kodemon.api.dto.*;
import com.kodemon.api.enums.WildPokemonFightMode;
import com.kodemon.api.facade.FightFacade;
import com.kodemon.api.facade.GymFacade;
import com.kodemon.api.facade.PokemonFacade;
import com.kodemon.api.facade.UserFacade;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Pokemon;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

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

    @Inject
    public FightController(
            FightFacade fightFacade,
            UserFacade userFacade,
            PokemonFacade pokemonFacade,
            GymFacade gymFacade) {
        this.fightFacade = fightFacade;
        this.userFacade = userFacade;
        this.pokemonFacade = pokemonFacade;
        this.gymFacade = gymFacade;
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
        LOG.debug("Viewing fights of " + period);
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
    public String listFightsOfGym(@RequestParam Long id, Model model) {
        GymDTO gym = gymFacade.findGymById(id);
        Collection<FightDTO> fights = fightFacade.listFightsOfGym(gym);
        if (fights.isEmpty()) {
            model.addAttribute("alert_warning", "No fights of this gym found");
            model.addAttribute("gym", gym);
            LOG.debug("Tried to look up fights of '" + gym.getCity() + "' Gym - the gym doesn't have any fight history.");
            return "/gym/detail";
        }
        model.addAttribute("fights", fights);
        LOG.debug("Viewing fights of '" + gym.getCity() + "' Gym");
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
    public String listFightsOfUser(@RequestParam String username, Model model) {

        Collection<UserDTO> user = userFacade.findUserByUserName(username);
        if (user.isEmpty()) {
            model.addAttribute("alert_warning", "No trainer with such username found");
            LOG.error("Tried to look up fights of '" + username + "' - user doesn't exists.");
            return "home";
        }
        Collection<FightDTO> fights = fightFacade.listFightsOfTrainer(userFacade.findUserByUserName(username).iterator().next());
        if (fights.isEmpty()) {
            model.addAttribute("alert_warning", "No fights of this user found");
            model.addAttribute("trainer", userFacade.findUserByUserName(username).iterator().next());
            LOG.debug("Tried to look up fights of '" + username + "' - user doesn't have any fight history.");
            return "/user/detail";
        }
        model.addAttribute("fights", fights);
        LOG.debug("Viewing fights of user '" + username + "'");
        return "fight/list";
    }

    @RequestMapping(value = "/grass", method = RequestMethod.GET)
    public String grass(ServletRequest r, Model model) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        if ((UserDTO) session.getAttribute("authenticatedUser") == null) {
            model.addAttribute("alert_warning", "You are not logged in!");
            LOG.error("User not logged in.");
            return "home";
        }
        Collection<UserDTO> users = userFacade.findUserByUserName(((UserDTO) session.getAttribute("authenticatedUser")).getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", "Error: User not found in database!");
            LOG.error("Error: User not found in database!");
            return "home";
        }
        UserDTO user = users.iterator().next();
        PokemonDTO wildPokemon = pokemonFacade.generateWildPokemon(user);
        session.setAttribute("wildPokemon", wildPokemon);
        model.addAttribute("wildPokemon", wildPokemon);
        model.addAttribute("trainersPokemon", user.getPokemons().get(0));
        LOG.debug("A wild " + wildPokemon.getName().getName() + " level " + wildPokemon.getLevel() + " appeared for " + user.getUserName());
        return "fight/grass";
    }

    @RequestMapping(value = "/fightWild", method = RequestMethod.GET)
    public String fightWild(@RequestParam String mode, ServletRequest r, Model model) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        WildPokemonFightMode mode_ = (mode.equals("train")) ? WildPokemonFightMode.TRAIN : WildPokemonFightMode.CATCH;
        PokemonDTO wildPokemon = (PokemonDTO) session.getAttribute("wildPokemon");
        if (wildPokemon == null) {
            return "home";
        }
        session.removeAttribute("wildPokemon");
        if ((UserDTO) session.getAttribute("authenticatedUser") == null) {
            model.addAttribute("alert_warning", "You are not logged in!");
            LOG.error("User not logged in.");
            return "home";
        }

        Collection<UserDTO> users = userFacade.findUserByUserName(((UserDTO) session.getAttribute("authenticatedUser")).getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", "Error: User not found in database!");
            LOG.error("Error: User not found in database!");
            return "home";
        }
        UserDTO user = users.iterator().next();

        boolean fightResult = fightFacade.fightWildPokemon(user, wildPokemon, mode_);
        if (fightResult)
            model.addAttribute("alert_success", (mode_ == WildPokemonFightMode.CATCH ? "Gotcha! " + wildPokemon.getName().getName() + " was caught!" : wildPokemon.getName().getName() + " fainted. " + user.getPokemons().get(0).getName().getName() + " leveled up to " + (user.getPokemons().get(0).getLevel() + 1)));
        else
            model.addAttribute("alert_warning", (mode_ == WildPokemonFightMode.CATCH ? wildPokemon.getName().getName() + " flew away!" : user.getPokemons().get(0).getName().getName() + " fainted! You ran to the nearest PokeCenter and healed your Pokemon."));

        users = userFacade.findUserByUserName(((UserDTO) session.getAttribute("authenticatedUser")).getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", "Error: User not found in database!");
            LOG.error("Error: User not found in database!");
            return "home";
        }
        user = userFacade.findUserByUserName(user.getUserName()).iterator().next();
        session.setAttribute("authenticatedUser", user);

        LOG.debug(user.getUserName() + (mode_ == WildPokemonFightMode.CATCH ? " catching" : " fighting") + " wild " + wildPokemon.getName().getName() + " level " + wildPokemon.getLevel() + " -> " + (fightResult ? "Success!" : "Failed"));
        return "home";
    }

    @RequestMapping(value = "/fightGym", method = RequestMethod.GET)
    public String fightGym(@RequestParam Long id, ServletRequest r, Model model) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            model.addAttribute("alert_warning", "You are not logged in!");
            LOG.error("User not logged in.");
            return "home";
        }
        Collection<UserDTO> users = userFacade.findUserByUserName(userDTO.getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", "Error: User not found in database!");
            LOG.error("Error: User not found in database!");
            return "home";
        }
        userDTO = users.iterator().next();
        GymDTO gym = gymFacade.findGymById(id);
        if (gym == null) {
            model.addAttribute("alert_warning", "Error: Gym not found in database!");
            LOG.error("Error: Gym ID " + id + " not found in database!");
            return "home";
        }
        for (BadgeDTO b : userDTO.getBadges()) {
            if (b.getName().equals(gym.getBadgeName())) {
                model.addAttribute("alert_warning", "You have already beaten this gym!");
                LOG.error("User " + userDTO.getUserName() + " has already beaten " + gym.getCity() + " Gym.");
                return "home";
            }
        }
        if (gym.getTrainer().getUserName().equals(userDTO.getUserName())) {
            model.addAttribute("alert_warning", "You cannot fight your own gym!");
            LOG.error("User " + userDTO.getUserName() + " owns " + gym.getCity() + " Gym.");
            return "home";
        }
        boolean fightResult = fightFacade.fightForBadge(userDTO, gym);
        model.addAttribute("fightResult", fightResult);
        if (fightResult)
            model.addAttribute("alert_success", "You beat " + gym.getTrainer().getUserName() + "! You received " + gym.getBadgeName());
        else
            model.addAttribute("alert_warning", "You lost!");
        users = userFacade.findUserByUserName(userDTO.getUserName());
        if (users.isEmpty()) {
            model.addAttribute("alert_warning", "Error: User not found in database!");
            LOG.error("Error: User not found in database!");
            return "home";
        }
        userDTO = users.iterator().next();
        session.setAttribute("authenticatedUser", userDTO);
        LOG.debug(userDTO.getUserName() + " is fighting " + gym.getCity() + " Gym -> " + (fightResult ? "Success!" : "Failed"));
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
    public String detail(@PathVariable Long id, Model model) {
        FightDTO fight = fightFacade.findFightById(id);
        if (fight == null) {
            model.addAttribute("alert_warning", "Error: Fight not found in database!");
            LOG.error("Error: Fight ID " + id + " not found in database!");
            return "home";
        }
        model.addAttribute("fight", fight);
        LOG.debug("Viewing detail of fight " + id);
        return "fight/detail";
    }
}
