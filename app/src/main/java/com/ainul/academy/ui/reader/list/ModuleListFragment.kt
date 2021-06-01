package com.ainul.academy.ui.reader.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ainul.academy.R
import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.databinding.FragmentModuleListBinding
import com.ainul.academy.ui.reader.CourseReaderActivity
import com.ainul.academy.ui.reader.CourseReaderCallback
import com.ainul.academy.ui.reader.CourseReaderViewModel
import com.ainul.academy.utils.DataDummy
import com.ainul.academy.viewmodel.ViewModelFactory


class ModuleListFragment : Fragment(), MyAdapterClickListener {

    companion object{
        val TAG: String = ModuleListFragment::class.java.simpleName
        fun newInstance(): ModuleListFragment = ModuleListFragment()
    }

    private lateinit var fragmentModuleListBinding: FragmentModuleListBinding
    private lateinit var adapter: ModuleListAdapter
    private lateinit var courseReaderCallback: CourseReaderCallback
    private lateinit var viewModel: CourseReaderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentModuleListBinding = FragmentModuleListBinding.inflate(inflater, container, false)
        return fragmentModuleListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]
        adapter = ModuleListAdapter(this)

        fragmentModuleListBinding.progressBar.visibility = View.VISIBLE
        viewModel.getModules().observe(viewLifecycleOwner, Observer<List<ModuleEntity>>{ modules ->
            fragmentModuleListBinding.progressBar.visibility = View.GONE
            populateRecyclerView(modules)
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        courseReaderCallback = context as CourseReaderActivity
        //kemduian buka CourseReaderActivity dan hubungkan dengan CourseReaderCallback
    }

    override fun onItemClicked(position: Int, moduleId: String){
        courseReaderCallback.moveTo(position, moduleId)
        viewModel.setSelectedModule(moduleId)
    }

    private fun populateRecyclerView(modules: List<ModuleEntity>) {
        with(fragmentModuleListBinding){
            adapter.setModules(modules)
            rvModule.layoutManager = LinearLayoutManager(context)
            rvModule.setHasFixedSize(true)
            rvModule.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            rvModule.addItemDecoration(dividerItemDecoration)
        }
    }
}