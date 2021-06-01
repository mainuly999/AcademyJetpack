package com.ainul.academy.ui.reader.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ainul.academy.R
import com.ainul.academy.data.ContentEntity
import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.databinding.FragmentModuleContentBinding
import com.ainul.academy.ui.reader.CourseReaderViewModel
import com.ainul.academy.viewmodel.ViewModelFactory

class ModuleContentFragment : Fragment() {
    companion object{
        val TAG: String = ModuleContentFragment::class.java.simpleName
        fun newInstance(): ModuleContentFragment = ModuleContentFragment()
    }

    private lateinit var fragmentModuleContentBinding: FragmentModuleContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentModuleContentBinding = FragmentModuleContentBinding.inflate(inflater, container, false)
        return fragmentModuleContentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        if(activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            fragmentModuleContentBinding.progressBar.visibility = View.VISIBLE
            viewModel.getSelectedModule().observe(viewLifecycleOwner, Observer<ModuleEntity>{ module ->
                fragmentModuleContentBinding.progressBar.visibility = View.GONE
                if (module != null) {
                    populatedWebView(module)
                }
            })
        }
    }

    private fun populatedWebView(module: ModuleEntity) {
        fragmentModuleContentBinding.webView.loadData(module.content?.content ?: "", "text/html", "UTF-8")
    }
}