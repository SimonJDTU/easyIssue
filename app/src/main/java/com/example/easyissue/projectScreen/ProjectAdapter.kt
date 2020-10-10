package com.example.easyissue.projectScreen

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.easyissue.R
import com.example.easyissue.data.Project

class ProjectAdapter(private val data: List<Project>?, val context: Context) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_project, parent, false)
        return ProjectViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) {
            return 0
        } else {
            data.size
        }
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        data?.get(position)?.let {

            holder.projectReference = it

            holder.name.text = it.name

            val projectTheme = ProjectPair(it.language, context)
            holder.languageIcon.setBackgroundResource(projectTheme.languageIcon)
            holder.background.background = projectTheme.backgroundColor
        }
    }

    class ProjectViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        lateinit var projectReference: Project
        val languageIcon: ImageView = v.findViewById(R.id.languageIcon)
        val name: TextView = v.findViewById(R.id.projectName)
        val background: View = v.findViewById(R.id.background)

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            //fetch navController and pass object
            Log.d("Recyclerview", "Clicked")
        }
    }

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



