package cz.muni.fi.pa165.kodemon.dao;

import cz.muni.fi.pa165.kodemon.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object for the Pokemon entity.
 *
 * @author Matej Poklemba
 */
public interface PokemonDao extends JpaRepository<Pokemon, Long> {

}
