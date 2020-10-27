package com.example.easyissue.projectScreen

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easyissue.R
import com.example.easyissue.ResourceContext
import com.example.easyissue.data.Project
import kotlinx.android.synthetic.main.card_project.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

//https://medium.com/swlh/android-recyclerview-with-data-binding-and-coroutine-3192097a0496
class ProjectAdapter(private val listener: OnItemClickListener) : ListAdapter<Project, ProjectAdapter.ProjectViewHolder>(Companion),
    KoinComponent {

    private val resourceHandler: ResourceContext by inject()

    companion object : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_project, parent, false)
        return ProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentItem = getItem(position)

        val projectTheme = ProjectPair(currentItem.language, resourceHandler.context)

        holder.apply {
            projectName.text = currentItem.name
            languageIcon.setBackgroundResource(projectTheme.languageIcon)
            background.background = projectTheme.backgroundColor
        }
    }

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val projectName: TextView = itemView.projectName
        val languageIcon: ImageView = itemView.languageIcon
        val background: ConstraintLayout = itemView.backgroundGradient

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val item: Project = getItem(adapterPosition)
            listener.onItemClick(item)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Project)
    }

    data class ProjectPair(val language: String?, private val context: Context) {

        var languageIcon: Int = -1
        var backgroundColor: Drawable?

        init {
            when (language) {
                "JavaScript" -> {
                    languageIcon = R.drawable.ic_javascript
                    backgroundColor =
                        ContextCompat.getDrawable(context, R.drawable.gradient_javascript)
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
                    backgroundColor =
                        ContextCompat.getDrawable(context, R.drawable.gradient_javascript)
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



