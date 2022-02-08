package pe.pcs.mvvm_room.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pe.pcs.mvvm_room.R
import java.lang.IllegalStateException

class BorrarDialogo(val borrarListener: BorrarListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)
            builder.setMessage(R.string.borrar_persona)
                .setPositiveButton("SI", DialogInterface.OnClickListener{ dialogInterface, i ->
                    borrarListener.borrarPersonal()
                })
                .setNegativeButton("NO", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })
            builder.create()
        } ?: throw IllegalStateException("La actividad no nuede estar nula")
    }

    interface BorrarListener {
        fun borrarPersonal()
    }
}