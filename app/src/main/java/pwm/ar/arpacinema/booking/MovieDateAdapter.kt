package pwm.ar.arpacinema.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import pwm.ar.arpacinema.databinding.DateItemBinding
import pwm.ar.arpacinema.databinding.ProfileImageItemBinding
import pwm.ar.arpacinema.model.ProfileImage
import pwm.ar.arpacinema.model.ScreeningDate
import pwm.ar.arpacinema.profile.image.ProfileImageAdapter

class MovieDateAdapter(
    private val dates: List<ScreeningDate>,
    private val onDayClick: (ScreeningDate) -> Unit
) : RecyclerView.Adapter<MovieDateAdapter.MovieDateViewHolder>() {

    // Variabile per tenere traccia della posizione selezionata
    private var selectedPosition = RecyclerView.NO_POSITION

    inner class MovieDateViewHolder(val binding: DateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Imposta il listener sul click
            binding.root.setOnClickListener {
                val clickedPosition = adapterPosition

                // Ignora se l'elemento cliccato è già selezionato
                if (selectedPosition == clickedPosition) return@setOnClickListener

                // Passa la data selezionata al listener
                onDayClick(dates[clickedPosition])

                // Salva la posizione precedentemente selezionata
                val previousSelectedPosition = selectedPosition

                // Aggiorna la nuova posizione selezionata
                selectedPosition = clickedPosition

                // Notifica il cambiamento della posizione precedente e quella attuale
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
        }

        // Imposta lo stato visivo in base alla selezione
        fun bind(date: ScreeningDate, isSelected: Boolean) {
            // Imposta i valori di testo delle viste
            binding.dayName.text = date.dayOfWeek
            binding.dayNum.text = date.dayOfMonth

            // Imposta lo stato del chip in base a se è selezionato o meno
            binding.root.isChecked = isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDateViewHolder {
        val binding = DateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieDateViewHolder(binding)
    }

    override fun getItemCount(): Int = dates.size

    override fun onBindViewHolder(holder: MovieDateViewHolder, position: Int) {
        // Verifica se la posizione corrente è quella selezionata
        val isSelected = position == selectedPosition

        // Chiama il metodo bind per aggiornare la UI in base allo stato di selezione
        holder.bind(dates[position], isSelected)
    }
}