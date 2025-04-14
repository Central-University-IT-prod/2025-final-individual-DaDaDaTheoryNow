package com.dadadadev.common.asset_manager

import android.content.Context
import java.io.InputStream

object AssetManager {
    fun open(context: Context, fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}