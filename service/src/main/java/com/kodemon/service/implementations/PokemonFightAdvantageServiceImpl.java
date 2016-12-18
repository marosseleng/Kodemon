package com.kodemon.service.implementations;

import com.kodemon.persistence.enums.PokemonName;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.interfaces.PokemonFightAdvantageService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Service which returns fight advantage of pokemon against another pokemon.
 * Fight advantage is calculated from pokemon types. For each weaker type there is +0.2 to score coefficient.
 * For each stronger type there is -0.2 to score coefficient.
 *
 * @author Oliver Roch
 */
@Service
public class PokemonFightAdvantageServiceImpl implements PokemonFightAdvantageService {

    @Override
    public double computePokemonFightAdvantage(PokemonName challenger, PokemonName target) {
        PokemonType[] challengerTypes = challenger.getTypes();
        PokemonType[] targetTypes = target.getTypes();

        double advantageCoefficient = 1;

        for (PokemonType challengerType : challengerTypes) {
            Set<PokemonType> weakerTypes = new HashSet<>(Arrays.asList(challengerType.weakerTypes()));
            for (PokemonType targetType : targetTypes) {
                Set<PokemonType> strongerTypes = new HashSet<>(Arrays.asList(targetType.weakerTypes()));
                if (weakerTypes.contains(targetType)) {
                    advantageCoefficient += 0.2;
                }
                if (strongerTypes.contains(challengerType)) {
                    advantageCoefficient -= 0.2;
                }
            }
        }

        return advantageCoefficient;
    }
}
