package com.example.easyissue.projectScreen

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easyissue.R
import com.example.easyissue.ResourceContext
import com.example.easyissue.data.Project
import com.example.easyissue.databinding.CardProjectBinding
import org.koin.core.KoinComponent
import org.koin.core.inject

//https://medium.com/swlh/android-recyclerview-with-data-binding-and-coroutine-3192097a0496
class ProjectAdapter : ListAdapter<Project,
        ProjectAdapter.ProjectViewHolder>(Companion),
    KoinComponent {

    private val resourceHandler: ResourceContext by inject()

    companion object : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardProjectBinding.inflate(layoutInflater)

        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        getItem(position).let {
            val projectTheme = ProjectPair(it.language, resourceHandler.context)

            holder.binding.apply {
                projectName.text = it.name
                languageIcon.setBackgroundResource(projectTheme.languageIcon)
                background.background = projectTheme.backgroundColor
            }
        }
    }

    class ProjectViewHolder(val binding: CardProjectBinding) : RecyclerView.ViewHolder(binding.root)

    data class ProjectPair(val language: String?, private val context: Context) {

        var languageIcon: Int = -1
        var backgroundColor: Drawable?

        init {
            when (language) {
                "JavaScript" -> {
                    languageIcon = R.drawable.ic_javascript
                    backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_javascript)
                }
                "Kotlin" -> {
                    languageIcon = R.drawable.ic_kotlin
                    backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_kotlin)
                }
                "C#" -> {
                    languageIcon = R.drawable.ic_csharp
                    backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_csharp)
                }
                "Java" -> {
                    languageIcon = R.drawable.ic_java
                    backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_java)
                }
                "HTML" -> {
                    languageIcon = R.drawable.ic_html
                    //TODO: Implement gradient for case
                    backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_javascript)
                }
                null -> {
                    languageIcon = R.drawable.ic_questionmark
                    //TODO: Implement gradient for case
                    backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_kotlin)
                }
                else -> {
                    languageIcon = R.drawable.ic_questionmark
                    //TODO: Implement gradient for case
                    backgroundColor = ContextCompat.getDrawable(context, R.drawable.gradient_kotlin)
                }
            }
        }
    }
}



