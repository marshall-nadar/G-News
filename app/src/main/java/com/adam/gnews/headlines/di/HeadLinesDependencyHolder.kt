package com.adam.gnews.headlines.di

import com.adam.gnews.core.application.App

object HeadLinesDependencyHolder {

    private var headLinesComponent: HeadLinesComponent? = null

    fun initHeadLinesComponent(): HeadLinesComponent {
        if (headLinesComponent == null) {
            headLinesComponent = DaggerHeadLinesComponent.builder()
                .coreComponent(App.coreComponent)
                .build()
        }
        return headLinesComponent as HeadLinesComponent
    }

    fun destroyHeadLinesComponent() {
        headLinesComponent = null
    }
}