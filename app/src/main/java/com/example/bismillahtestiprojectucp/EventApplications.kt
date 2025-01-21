package com.example.bismillahtestiprojectucp

import android.app.Application
import com.example.bismillahtestiprojectucp.dependeciesinjection.AppContainer
import com.example.bismillahtestiprojectucp.dependeciesinjection.EventContainer

class EventApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = EventContainer()
    }
}