package com.dadadadev.superfinancer.domain.models

enum class RefillType {
    Increment,
    Decrement;

    companion object {
        fun fromInt(amount: Int): RefillType {
            return when {
                amount >= 0 -> Increment
                amount < 0 -> Decrement
                else -> Increment
            }
        }
    }
}

data class Refill(
    val id: Int = 0,
    var amount: Long,
    var type: RefillType,
    var targetGoalName: String,
    var targetGoalId: Int,
)