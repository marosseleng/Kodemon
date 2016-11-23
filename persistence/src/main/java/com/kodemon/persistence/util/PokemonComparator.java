package com.kodemon.persistence.util;

import com.kodemon.persistence.entity.Pokemon;

import java.util.Comparator;

/**
 * Comparator for Pokemon for sorting Pokemons by their level
 *
 * @author Matej Poklemba
 */
public class PokemonComparator implements Comparator<Pokemon> {
    @Override
    public int compare(Pokemon p1, Pokemon p2) {
        return Integer.compare(p1.getLevel(), p2.getLevel());
    }
}
