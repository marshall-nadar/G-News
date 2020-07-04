package com.adam.gnews.headlines.di

import com.adam.gnews.core.application.di.CoreComponent
import com.adam.gnews.headlines.view.HeadLinesActivity
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [HeadLinesModule::class]
)
@HeadLinesScope
interface HeadLinesComponent {
    fun inject(activity: HeadLinesActivity)
}