package com.example.easyissue.projectScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.easyissue.R
import com.example.easyissue.data.Project
import com.example.easyissue.data.ProjectTheme
import kotlinx.android.synthetic.main.card_project_compressed.view.*
import kotlinx.android.synthetic.main.card_project_full.view.*
import org.koin.core.KoinComponent

//https://medium.com/swlh/android-recyclerview-with-data-binding-and-coroutine-3192097a0496
class ProjectAdapter(
    private val listener: OnItemClickListener,
    private val isDoubleSpan: Boolean
) : ListAdapter<Project, ProjectAdapter.BaseViewHolder<*>>(Companion),
    KoinComponent {

    companion object : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return if (isDoubleSpan) {
            ProjectViewHolderCompressed(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_project_compressed, parent, false)

            )
        } else {
            ProjectViewHolderFull(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_project_full, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val currentItem = getItem(position)
        when (holder) {
            is ProjectViewHolderFull -> {
                holder.bind(currentItem as Project)
            }
            is ProjectViewHolderCompressed -> {
                holder.bind(currentItem as Project)
            }
        }
    }

    inner class ProjectViewHolderFull(itemView: View) : BaseViewHolder<Project>(itemView),
        View.OnClickListener {

        override fun bind(item: Project) {
            val projectTheme = ProjectTheme(item.language)
            itemView.apply {
                fullName.text = item.name
                fullLanguage.text = resources.getString(R.string.card_language, item.language)
                fullOwner.text = resources.getString(R.string.card_owner, item.owner.login)
                languageIcon.setBackgroundResource(projectTheme.languageIcon)
                fullBackground.background =
                    ResourcesCompat.getDrawable(resources, projectTheme.backgroundColor, null)
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

    inner class ProjectViewHolderCompressed(itemView: View) : BaseViewHolder<Project>(itemView),
        View.OnClickListener {

        override fun bind(item: Project) {
            val projectTheme = ProjectTheme(item.language)
            itemView.apply {
                compName.text = item.name
                compInfo.text = resources.getString(R.string.card_language, item.language)
                compBackground.background =
                    ResourcesCompat.getDrawable(resources, projectTheme.backgroundColor, null)
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
}
