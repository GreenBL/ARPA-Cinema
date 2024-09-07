package pwm.ar.arpacinema.dev

data class Selection(
    val movieTitle : String,
    val showDate : String,
    val showTime : String,
    val seatString: String,
    val price : String = ""
){
    val seatCustomString: String
        get() {
            require(seatString.length == 2) { "Posto non valido: $seatString" }

            val row = seatString[0]
            val column = seatString[1]

            return "Fila $row, Posto $column"
        }
}

