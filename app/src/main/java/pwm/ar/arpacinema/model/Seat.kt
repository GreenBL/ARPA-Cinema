package pwm.ar.arpacinema.model

import pwm.ar.arpacinema.util.SeatInterpreter

data class Seat (
    val identifier: String
) {
    val row: String
        get() = identifier[0].toString()

    val column: String
        get() = identifier[1].toString()

    val index : Int
        get() = SeatInterpreter.getSeatId(identifier)

}

