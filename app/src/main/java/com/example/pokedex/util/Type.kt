package com.example.pokedex.util

import com.example.pokedex.R

enum class Type {
    WATER {
        override fun getColor(): Int {
            return R.color.water
        }

        override fun getImagem(): Int {
            return R.drawable.water
        }
    },

    FIRE {
        override fun getColor(): Int {
            return R.color.fire
        }

        override fun getImagem(): Int {
            return R.drawable.fire
        }
    },

    GRASS {
        override fun getColor(): Int {
            return R.color.grass
        }

        override fun getImagem(): Int {
            return R.drawable.grass
        }
    },

    GROUND {
        override fun getColor(): Int {
            return R.color.ground
        }

        override fun getImagem(): Int {
            return R.drawable.ground
        }
    },

    ROCK {
        override fun getColor(): Int {
            return R.color.rock
        }

        override fun getImagem(): Int {
            return R.drawable.rock
        }
    },

    STEEL {
        override fun getColor(): Int {
            return R.color.steel
        }

        override fun getImagem(): Int {
            return R.drawable.steel
        }
    },

    ICE {
        override fun getColor(): Int {
            return R.color.ice
        }

        override fun getImagem(): Int {
            return R.drawable.ice
        }
    },

    ELECTRIC {
        override fun getColor(): Int {
            return R.color.electric
        }

        override fun getImagem(): Int {
            return R.drawable.electric
        }
    },

    DRAGON {
        override fun getColor(): Int {
            return R.color.dragon
        }

        override fun getImagem(): Int {
            return R.drawable.dragon
        }
    },

    GHOST {
        override fun getColor(): Int {
            return R.color.ghost
        }

        override fun getImagem(): Int {
            return R.drawable.ghost
        }
    },

    PSYCHIC {
        override fun getColor(): Int {
            return R.color.psychic
        }

        override fun getImagem(): Int {
            return R.drawable.psychic
        }
    },

    NORMAL {
        override fun getColor(): Int {
            return R.color.normal
        }

        override fun getImagem(): Int {
            return R.drawable.normal
        }
    },

    FIGHTING {
        override fun getColor(): Int {
            return R.color.fighting
        }

        override fun getImagem(): Int {
            return R.drawable.fighting
        }
    },

    POISON {
        override fun getColor(): Int {
            return R.color.poison
        }

        override fun getImagem(): Int {
            return R.drawable.poison
        }
    },

    BUG {
        override fun getColor(): Int {
            return R.color.bug
        }

        override fun getImagem(): Int {
            return R.drawable.bug
        }
    },

    FLYING {
        override fun getColor(): Int {
            return R.color.flying
        }

        override fun getImagem(): Int {
            return R.drawable.flying
        }
    },

    DARK {
        override fun getColor(): Int {
            return R.color.dark
        }

        override fun getImagem(): Int {
            return R.drawable.dark
        }
    },

    FAIRY {
        override fun getColor(): Int {
            return R.color.fairy
        }

        override fun getImagem(): Int {
            return R.drawable.fairy
        }

    };

    abstract fun getImagem(): Int

    abstract fun getColor(): Int
}