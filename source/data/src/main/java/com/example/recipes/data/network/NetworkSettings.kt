package com.example.recipes.data.network

class NetworkSettings {
    companion object {
        const val READ_TIME_OUT: Long = 3
        const val CONNECT_TIME_OUT: Long = 3
        const val RETRY_MAX_COUNT = 3

        const val DEFAULT_SERVER_ERROR_MESSAGE =
            "Lo siento, actualmente estamos experimentando dificultades técnicas. Por favor, inténtelo de nuevo más tarde. Si el problema persiste, por favor póngase en contacto con nuestro equipo de soporte técnico para obtener ayuda."
        const val DEFAULT_NETWORK_ERROR_MESSAGE =
            "Lo siento, no hemos podido establecer una conexión a Internet. Por favor, compruebe su conexión a Internet e inténtelo de nuevo."
    }
}