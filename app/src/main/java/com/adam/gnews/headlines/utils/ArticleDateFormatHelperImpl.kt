package com.adam.gnews.headlines.utils

import java.text.SimpleDateFormat
import java.util.*

class ArticleDateFormatHelperImpl(
    private val dateFormatter: SimpleDateFormat
) : ArticleDateFormatHelper {

    override fun formatDate(date: Date): String = dateFormatter.format(date)

}