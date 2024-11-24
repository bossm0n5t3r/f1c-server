package me.f1c.domain

object JolpicaF1API {
    // https://github.com/jolpica/jolpica-f1
    private const val JOLPICA_F1_API = "https://api.jolpi.ca/ergast/f1"

    fun getRaceApi(year: Int) = "$JOLPICA_F1_API/$year/races/?format=json"
}
