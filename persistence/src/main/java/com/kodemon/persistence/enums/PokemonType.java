package com.kodemon.persistence.enums;

/**
 * Available types of Pokemon
 *
 * @author Matej Poklemba
 */
public enum PokemonType {
    // TODO fill correct weaker types for each type
    WATER {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[]{FIRE, DRAGON};
        }
    },
    FIRE {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    GRASS {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    DRAGON {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    PSYCHIC {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    BUG {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    NORMAL {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    ELECTRIC {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    FIGHTING {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    POISON {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    GROUND {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    FLYING {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    ROCK {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    GHOST {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    },
    ICE {
        @Override
        public PokemonType[] weakerTypes() {
            return new PokemonType[0];
        }
    };

    public abstract PokemonType[] weakerTypes();


}
