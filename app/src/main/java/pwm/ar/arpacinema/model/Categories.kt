package pwm.ar.arpacinema.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Categories(val category: String) : Parcelable {
    ADVENTURE("Avventura"), // K
    ACTION("Azione"), // K
    ANIMATION("Animazione"), // K
    ANIME("Anime"), // K
    BIOGRAPH("Biografico"), // K
    COMEDY("Commedia"), // K
    DOC("Documentario"), // K
    DRAMA("Drammatico"), // K
    //FANTASY("Fantasy"),
    FAMILY("Per famiglie"), // K
    SCIFI("Sci-fi"), // k?
    WAR("Guerra"), // K
    HORROR("Horror"), // K
    MUSICAL("Musical"), // k
    ROMANCE("Romantico"), // k
    STORICAL("Storico"), // k
    THRILLER("Thriller"), // k
    //WESTERN("Western");
}