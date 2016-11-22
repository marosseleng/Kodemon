package com.kodemon.persistence.enums;

/**
 * Available types of Pokemon
 *
 * @author Matej Poklemba
 */
public enum PokemonType {
    WATER {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{FIRE, GROUND, ROCK};
        }
    },
    FIRE {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{GRASS, ICE, BUG};
        }
    },
    GRASS {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{WATER, GROUND, ROCK};
        }
    },
    DRAGON {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{DRAGON};
        }
    },
    PSYCHIC {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{FIGHTING, POISON};
        }
    },
    BUG {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{GRASS, POISON, PSYCHIC};
        }
    },
    NORMAL {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{};
        }
    },
    ELECTRIC {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{WATER, FLYING};
        }
    },
    FIGHTING {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{NORMAL, ICE, ROCK};
        }
    },
    POISON {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{GRASS, BUG};
        }
    },
    GROUND {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{FIRE, ELECTRIC, POISON, ROCK};
        }
    },
    FLYING {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{GRASS, FIGHTING, BUG};
        }
    },
    ROCK {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{FIRE, ICE, FLYING, BUG};
        }
    },
    GHOST {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{GHOST};
        }
    },
    ICE {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{GRASS, GROUND, FLYING, DRAGON};
        }
    };

    public abstract PokemonType[] weakerTypes();
}
