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
 *  @author Matej Poklemba
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
        switch (period)
        {
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
        LOG.debug("list" + period + "()");
        return "fight/list";
    }

    /**
     * Show list of given gym's fights.
     *
     * @param id id of gym to display fights of
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/listFightsOfGym", method = RequestMethod.GET)
    public String listFightsOfGym(@RequestParam Long id, Model model) {
        model.addAttribute("fights", fightFacade.listFightsOfGym(gymFacade.findGymById(id)));
        LOG.debug("listFightsOfGym()");
        return "fight/list";
    }

    /**
     * Show list of given user's fights.
     *
     * @param username which user's fights do display
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/listFightsOfUser", method = RequestMethod.GET)
    public String listFightsOfUser(@RequestParam String username, Model model) {

        Collection<UserDTO> user = userFacade.findUserByUserName(username);
        if(user.isEmpty()) {
            model.addAttribute("alert_warning", "No trainer with such username found");
            model.addAttribute("users", userFacade.findAllUsers());
            return "fight/list";
        }
        Collection<FightDTO> fights = fightFacade.listFightsOfTrainer(userFacade.findUserByUserName(username).iterator().next());
        if(fights.isEmpty()) {
            model.addAttribute("alert_warning", "No fights of this user found");
            model.addAttribute("trainer", userFacade.findUserByUserName(username).iterator().next());
            return "/user/detail";
        }
        model.addAttribute("fights", fights);
        LOG.debug("listFightsOfUser()");
        return "fight/list";
    }

    @RequestMapping(value = "/grass", method = RequestMethod.GET)
    public String grass(ServletRequest r, Model model) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        if ((UserDTO)session.getAttribute("authenticatedUser") == null)
        {
            model.addAttribute("alert_warning", "You are not logged in!");
            return "home";
        }
        Collection<UserDTO> users = userFacade.findUserByUserName(((UserDTO)session.getAttribute("authenticatedUser")).getUserName());
        UserDTO user = users.iterator().next();
        PokemonDTO wildPokemon = pokemonFacade.generateWildPokemon(user);
        session.setAttribute("wildPokemon", wildPokemon);
        model.addAttribute("wildPokemon", wildPokemon);
        model.addAttribute("trainersPokemon", user.getPokemons().get(0));
        LOG.debug("A wild " + wildPokemon.getName().getName() + " level " + wildPokemon.getLevel() + " appeared!");
        return "fight/grass";
    }

    @RequestMapping(value = "/fightWild", method = RequestMethod.GET)
    public String fightWild(@RequestParam String mode, ServletRequest r, Model model) {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpSession session = request.getSession();
        WildPokemonFightMode mode_ = (mode.equals("fight")) ? WildPokemonFightMode.TRAIN : WildPokemonFightMode.CATCH;
        PokemonDTO wildPokemon = (PokemonDTO)session.getAttribute("wildPokemon");
        if ((UserDTO)session.getAttribute("authenticatedUser") == null)
        {
            model.addAttribute("alert_warning", "You are not logged in!");
            return "home";
        }
        Collection<UserDTO> users = userFacade.findUserByUserName(((UserDTO)session.getAttribute("authenticatedUser")).getUserName());
        UserDTO user = users.iterator().next();
        boolean fightResult = fightFacade.fightWildPokemon(user, wildPokemon, mode_);
        model.addAttribute("fightResult", fightResult);
        if (fightResult)
            model.addAttribute("alert_success", (mode_ == WildPokemonFightMode.CATCH ? "Gotcha! " + wildPokemon.getName().getName() + " was caught!" : wildPokemon.getName().getName() + " fainted. " + user.getPokemons().get(0).getName().getName() + " leveled up to " + (user.getPokemons().get(0).getLevel() + 1)));
        else
            model.addAttribute("alert_warning", (mode_ == WildPokemonFightMode.CATCH ? wildPokemon.getName().getName() + " flew away!" : user.getPokemons().get(0).getName().getName() + " fainted! You ran to the nearest PokeCenter and healed your Pokemon."));
        LOG.debug((mode_ == WildPokemonFightMode.CATCH ? "Catching" : "Fighting") + " wild " + wildPokemon.getName().getName() + " level " + wildPokemon.getLevel() + " -> " + (fightResult ? "Success!" : "Failed"));
        return "home";
    }

    /**
     * Show detail of fight specified by its id
     *
     * @param id which id is to be shown
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("fight", fightFacade.findFightById(id));
        return "fight/detail";
    }
}
