package com.zero.schooglink

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//To notify compiler this uses Hilt and DI
@HiltAndroidApp
class BaseApplication : Application()