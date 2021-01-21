package com.example.easyissue.data

import com.example.easyissue.R

data class ProjectTheme(val language: String?) {

    var languageIcon: Int = -1
    var backgroundColor: Int = -1
    var topGradient: Int = -1

    init {
        when (language) {
            "JavaScript" -> {
                languageIcon = R.drawable.ic_javascript
                backgroundColor = R.drawable.gradient_javascript
                topGradient = R.drawable.gradient_top_javascript
            }
            "Kotlin" -> {
                languageIcon = R.drawable.ic_kotlin
                backgroundColor = R.drawable.gradient_kotlin
                topGradient = R.drawable.gradient_top_kotlin
            }
            "C#" -> {
                languageIcon = R.drawable.ic_csharp
                backgroundColor = R.drawable.gradient_csharp
                topGradient = R.drawable.gradient_top_csharp
            }
            "Java" -> {
                languageIcon = R.drawable.ic_java
                backgroundColor = R.drawable.gradient_java
                topGradient = R.drawable.gradient_top_java
            }
            "HTML" -> {
                languageIcon = R.drawable.ic_html
                backgroundColor = R.drawable.gradient_html
                topGradient = R.drawable.gradient_top_html
            }
            "Swift" -> {
                languageIcon = R.drawable.ic_swift
                backgroundColor = R.drawable.gradient_swift
                topGradient = R.drawable.gradient_top_swift
            }
            "Python" -> {
                languageIcon = R.drawable.ic_python
                backgroundColor = R.drawable.gradient_python
                topGradient = R.drawable.gradient_top_python
            }
            "React" -> {
                languageIcon = R.drawable.ic_react
                backgroundColor = R.drawable.gradient_react
                topGradient = R.drawable.gradient_top_react
            }
            null -> {
                languageIcon = R.drawable.ic_questionmark
                backgroundColor = R.drawable.gradient_unknown
                topGradient = R.drawable.gradient_top_unknown
            }
            else -> {
                languageIcon = R.drawable.ic_questionmark
                backgroundColor = R.drawable.gradient_unknown
                topGradient = R.drawable.gradient_top_unknown
            }
        }
    }
}

