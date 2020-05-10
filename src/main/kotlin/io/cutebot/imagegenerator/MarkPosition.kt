package io.cutebot.imagegenerator

enum class MarkPosition(
        val positionId: Int
) {
    LB(1),
    RB(2),
    LT(3),
    RT(4);

    fun isLeftAlign(): Boolean {
        return this == LB || this == LT
    }

    fun isTopAlign(): Boolean {
        return this == LT || this == RT
    }

    companion object {
        private val posToId = mapOf(
                LB to LB.positionId,
                RB to RB.positionId,
                LT to LT.positionId,
                RT to RT.positionId
        )
        private val idToPos = posToId.entries.associate{ (k,v)-> v to k}

        fun typeById(id: Int): MarkPosition {
            return idToPos[id] ?: error("Unknown position $id")
        }

        fun idByType(type: MarkPosition): Int {
            return posToId[type] ?: error("Unknown position $type")
        }

    }
}
