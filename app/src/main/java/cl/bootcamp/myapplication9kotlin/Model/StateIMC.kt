package cl.bootcamp.myapplication9kotlin.Model

class StateIMC(
    val weight: String = "",
    val height: String = "",
    val age: String = "",
    val imcResult: Float = 0f
) {
    fun copy(
        weight: String = this.weight,
        height: String = this.height,
        age: String = this.age,
        imcResult: Float = this.imcResult
    ): StateIMC {
        return StateIMC(weight, height, age, imcResult)
    }
}