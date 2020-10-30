package com.example.easyissue.projectScreen

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easyissue.R
import com.example.easyissue.data.Project
import kotlinx.android.synthetic.main.card_project_compressed.view.*
import kotlinx.android.synthetic.main.card_project_full.view.*
import org.koin.core.KoinComponent

//https://medium.com/swlh/android-recyclerview-with-data-binding-and-coroutine-3192097a0496
class ProjectAdapter(private val listener: OnItemClickListener, private val isSingleSpan: Boolean, val resources: Resources) : ListAdapter<Project, ProjectAdapter.BaseViewHolder<*>>(Companion),
    KoinComponent {

    companion object : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        return if (isSingleSpan){
            ProjectViewHolderFull(LayoutInflater.from(parent.context).inflate(R.layout.card_project_full, parent, false))
        }else{
            ProjectViewHolderCompressed(LayoutInflater.from(parent.context).inflate(R.layout.card_project_compressed, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val currentItem = getItem(position)
        when(holder){
            is ProjectViewHolderFull -> {holder.bind(currentItem as Project)}
            is ProjectViewHolderCompressed -> {holder.bind(currentItem as Project)}
        }
    }

    inner class ProjectViewHolderFull(itemView: View) : BaseViewHolder<Project>(itemView), View.OnClickListener {

        override fun bind(item: Project) {
            val projectTheme = ProjectPair(item.language)
            itemView.apply {
                fullName.text = item.name
                fullLanguage.text = resources.getString(R.string.card_language, item.language)
                fullOwner.text = resources.getString(R.string.card_owner, item.owner.login)
                languageIcon.setBackgroundResource(projectTheme.languageIcon)
                fullBackground.background = projectTheme.backgroundColor
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val item: Project = getItem(adapterPosition)
            listener.onItemClick(item)
        }
    }

    inner class ProjectViewHolderCompressed(itemView: View) : BaseViewHolder<Project>(itemView), View.OnClickListener {

        override fun bind(item: Project) {
            val projectTheme = ProjectPair(item.language)
            itemView.apply {
                compName.text = item.name
                compInfo.text = resources.getString(R.string.card_language, item.language)
                compBackground.background = projectTheme.backgroundColor
            }
        }

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

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    inner class ProjectPair(language: String?) {

        var languageIcon: Int = -1
        var backgroundColor: Drawable?

        init {
            when (language) {
                "JavaScript" -> {
                    languageIcon = R.drawable.ic_javascript
                    backgroundColor = ResourcesCompat.getDrawable(resources, R.drawable.gradient_javascript, null)
                }
                "Kotlin" -> {
                    languageIcon = R.drawable.ic_kotlin
                    backgroundColor = ResourcesCompat.getDrawable(resources,R.drawable.gradient_kotlin, null)
                }
                "C#" -> {
                    languageIcon = R.drawable.ic_csharp
                    backgroundColor = ResourcesCompat.getDrawable(resources, R.drawable.gradient_csharp, null)
                }
                "Java" -> {
                    languageIcon = R.drawable.ic_java
                    backgroundColor = ResourcesCompat.getDrawable(resources, R.drawable.gradient_java, null)
                }
                "HTML" -> {
                    languageIcon = R.drawable.ic_html
                    backgroundColor = ResourcesCompat.getDrawable(resources,  R.drawable.gradient_html, null)
                }
                null -> {
                    languageIcon = R.drawable.ic_questionmark
                    backgroundColor = ResourcesCompat.getDrawable(resources, R.drawable.gradient_unknown, null)
                }
                else -> {
                    languageIcon = R.drawable.ic_questionmark
                    backgroundColor = ResourcesCompat.getDrawable(resources, R.drawable.gradient_unknown, null)
                }
            }
        }
    }
}
