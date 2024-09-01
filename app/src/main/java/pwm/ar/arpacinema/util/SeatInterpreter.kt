package pwm.ar.arpacinema.util

object SeatInterpreter {
    private val seatsMapping = mapOf(
        "A1" to 1,
        "A2" to 2,
        "A3" to 3,
        "A4" to 4,
        "A5" to 5,
        "A6" to 6,
        "B1" to 7,
        "B2" to 8,
        "B3" to 9,
        "B4" to 10,
        "B5" to 11,
        "B6" to 12,
        "B7" to 13,
        "B8" to 14,
        "C1" to 15,
        "C2" to 16,
        "C3" to 17,
        "C4" to 18,
        "C5" to 19,
        "C6" to 20,
        "C7" to 21,
        "C8" to 22,
        "D1" to 23,
        "D2" to 24,
        "D3" to 25,
        "D4" to 26,
        "D5" to 27,
        "D6" to 28,
        "D7" to 29,
        "D8" to 30,
        "E1" to 31, // e1m1?
        "E2" to 32,
        "E3" to 33,
        "E4" to 34,
        "E5" to 35,
        "E6" to 36,
        "E7" to 37,
        "E8" to 38,
        "F1" to 39,
        "F2" to 40,
        "F3" to 41,
        "F4" to 42,
        "F5" to 43,
        "F6" to 44,
        "F7" to 45,
        "F8" to 46,
        "G1" to 47,
        "G2" to 48,
        "G3" to 49,
        "G4" to 50,
        "G5" to 51,
        "G6" to 52)

    fun getSeatId(seatName: String): Int {
        return seatsMapping[seatName] ?: -1 // if its -1 its broken so... hi elvis
    }

    fun getSeatName(seatId: Int): String {
        for ((name, id) in seatsMapping) {
            if (id == seatId) {
                return name
            }
        }
        return ""
    }

    fun convertListToInteger(list: List<String>): List<Int> {
        val integerList = mutableListOf<Int>()
        for (seat in list) {
            val seatId = getSeatId(seat)
            if (seatId != -1) {
                integerList.add(seatId)
            }
        }
        return integerList
    }

    fun unavailableToAvailable(list: List<Int>): List<Int> {
        val availableList = mutableListOf<Int>()
        for (i in 1..52) {
            if (!list.contains(i)) {
                availableList.add(i)
            }
        }
        return availableList

    }


}