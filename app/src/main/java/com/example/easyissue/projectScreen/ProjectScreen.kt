package com.example.easyissue.projectScreen

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.easyissue.R
import com.example.easyissue.databinding.ProjectScreenBinding


class ProjectScreen : Fragment() {

    private lateinit var viewModel: ProjectScreenViewModel
    private lateinit var projectAdapter: ProjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ProjectScreenBinding>(
            inflater,
            R.layout.project_screen, container, false)

        viewModel = ViewModelProvider(this).get(ProjectScreenViewModel::class.java)

        binding.viewModel = viewModel

        binding.projectList.layoutManager = GridLayoutManager(context, 2)

        //TODO: Reference space value to dimens
        binding.projectList.addItemDecoration(SpacesItemDecoration(20))

        viewModel.fetchedProjects.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                projectAdapter = ProjectAdapter(viewModel.fetchedProjects.value, requireContext())
                binding.projectList.adapter = projectAdapter
            }else{
                Toast.makeText(context,"List Empty", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}

//Item decorator class for recyclerview
class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when {
            parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view)%2 == 0 -> {
                outRect.bottom = space
                outRect.right = space
            }
            parent.getChildLayoutPosition(view) == 1 || parent.getChildLayoutPosition(view)%2 != 0 -> {
                outRect.bottom = space
            }
        }
    }
}
