package com.dadadadev.common

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.launchCustomTabs(url: String) {
    CustomTabsIntent
        .Builder()
        .build()
        .launchUrl(this, Uri.parse(url))
}