package com.amilien.prometheus.utils

import androidx.navigation.NavOptions
import com.amilien.prometheus.R

val navAnimOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.enter_from_right)
    .setExitAnim(R.anim.exit_to_left)
    .setPopEnterAnim(R.anim.enter_from_left)
    .setPopExitAnim(R.anim.exit_to_right)
    .build()
