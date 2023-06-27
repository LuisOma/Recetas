package com.example.recipes.base

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.recipes.R
import com.example.recipes.data.domain.core.ErrorState
import com.example.recipes.data.network.NetworkSettings

/*--------------------------------------ACTIVITY EXTENSIONS---------------------------------------*/

fun Activity.addBackButton(): Toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
    setNavigationIcon(R.drawable.ic_arrow_back)
    setNavigationOnClickListener {
        finish()
    }
}


/*--------------------------------------FRAGMENT EXTENSIONS---------------------------------------*/
fun ErrorState.showMessageError(fragment: Fragment) {
    when (this) {
        is ErrorState.TransactionError -> {
            Toast.makeText(
                fragment.requireContext(),
                this.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        is ErrorState.Network -> {
            Toast.makeText(
                fragment.requireContext(),
                NetworkSettings.DEFAULT_NETWORK_ERROR_MESSAGE,
                Toast.LENGTH_SHORT
            ).show()
        }
        is ErrorState.LocalError -> {}

        else -> {}
    }
}




