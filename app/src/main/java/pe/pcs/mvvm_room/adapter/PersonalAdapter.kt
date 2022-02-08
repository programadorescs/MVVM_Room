package pe.pcs.mvvm_room.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.mvvm_room.R
import pe.pcs.mvvm_room.config.Constantes
import pe.pcs.mvvm_room.databinding.ItemListBinding
import pe.pcs.mvvm_room.model.Personal
import pe.pcs.mvvm_room.view.FormularioActivity

class PersonalAdapter(private val lista: List<Personal>?): RecyclerView.Adapter<PersonalAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalAdapter.MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )

    }

    override fun onBindViewHolder(holder: PersonalAdapter.MyViewHolder, position: Int) {

        val item = lista?.get(position)
        holder.enlazarItem(item!!)

    }

    override fun getItemCount(): Int {
        return lista?.size?: 0
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        // crear el enlace con el binding
        val binding = ItemListBinding.bind(view)

        // Crear un contexto
        var contexto = view.context

        fun enlazarItem(personal: Personal) {
            binding.tvNombre.text = "${personal.nombre} ${personal.apellido}"
            binding.tvTelefono.text = personal.telefono
            binding.tvEmail.text = personal.email
            binding.tvEdad.text = personal.edad.toString()

            // Evento clic de la tarjeta cardview
            binding.root.setOnClickListener {

                val intent = Intent(contexto, FormularioActivity::class.java)
                intent.putExtra(Constantes.OPERACION_KEY, Constantes.OPERACION_EDITAR)
                intent.putExtra(Constantes.ID_PERSONAL_KEY, personal.idEmpleado)
                contexto.startActivity(intent)

            }
        }

    }
}