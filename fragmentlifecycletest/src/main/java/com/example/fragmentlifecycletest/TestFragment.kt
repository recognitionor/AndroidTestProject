package com.example.fragmentlifecycletest

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TestFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("jhlee", "onCreate1 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onCreate1 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onCreate1 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onCreate1 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onCreate1 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onCreate1 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onCreate1 isStateSaved : ${this.isStateSaved}")
        super.onCreate(savedInstanceState)
        Log.d("jhlee", "----------------onCreate-----------------")

        Log.d("jhlee", "onCreate2 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onCreate2 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onCreate2 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onCreate2 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onCreate2 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onCreate2 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onCreate2 isStateSaved : ${this.isStateSaved}")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("jhlee", "onCreateView isAdded : ${this.isAdded}")
        Log.d("jhlee", "onCreateView isResumed : ${this.isResumed}")
        Log.d("jhlee", "onCreateView isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onCreateView isHidden : ${this.isHidden}")
        Log.d("jhlee", "onCreateView isDetached : ${this.isDetached}")
        Log.d("jhlee", "onCreateView isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onCreateView isStateSaved : ${this.isStateSaved}")


        Log.d("jhlee", "----------------onCreateView-----------------")
        return inflater.inflate(R.layout.fragment_main, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("jhlee", "onViewCreated1 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onViewCreated1 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onViewCreated1 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onViewCreated1 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onViewCreated1 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onViewCreated1 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onViewCreated1 isStateSaved : ${this.isStateSaved}")
        super.onViewCreated(view, savedInstanceState)
        Log.d("jhlee", "----------------onViewCreated-----------------")

        Log.d("jhlee", "onViewCreated2 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onViewCreated2 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onViewCreated2 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onViewCreated2 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onViewCreated2 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onViewCreated2 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onViewCreated2 isStateSaved : ${this.isStateSaved}")
    }

    override fun onDestroyView() {
        Log.d("jhlee", "onDestroyView1 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onDestroyView1 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onDestroyView1 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onDestroyView1 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onDestroyView1 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onDestroyView1 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onDestroyView1 isStateSaved : ${this.isStateSaved}")
        super.onDestroyView()
        Log.d("jhlee", "----------------onDestroyView-----------------")


        Log.d("jhlee", "onDestroyView2 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onDestroyView2 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onDestroyView2 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onDestroyView2 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onDestroyView2 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onDestroyView2 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onDestroyView2 isStateSaved : ${this.isStateSaved}")

    }

    override fun onDetach() {
        Log.d("jhlee", "onDetach1 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onDetach1 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onDetach1 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onDetach1 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onDetach1 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onDetach1 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onDetach1 isStateSaved : ${this.isStateSaved}")

        super.onDetach()
        Log.d("jhlee", "----------------onDetach-----------------")

        Log.d("jhlee", "onDetach2 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onDetach2 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onDetach2 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onDetach2 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onDetach2 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onDetach2 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onDetach2 isStateSaved : ${this.isStateSaved}")
    }

    override fun onDestroy() {
        Log.d("jhlee", "onDestroy1 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onDestroy1 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onDestroy1 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onDestroy1 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onDestroy1 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onDestroy1 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onDestroy1 isStateSaved : ${this.isStateSaved}")
        super.onDestroy()
        Log.d("jhlee", "----------------onDestroy-----------------")

        Log.d("jhlee", "onDestroy2 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onDestroy2 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onDestroy2 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onDestroy2 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onDestroy2 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onDestroy2 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onDestroy2 isStateSaved : ${this.isStateSaved}")
    }

    override fun onAttach(activity: Activity?) {
        Log.d("jhlee", "onAttach1 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onAttach1 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onAttach1 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onAttach1 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onAttach1 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onAttach1 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onAttach1 isStateSaved : ${this.isStateSaved}")
        super.onAttach(activity)
        Log.d("jhlee", "----------------onAttach-----------------")

        Log.d("jhlee", "onAttach2 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onAttach2 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onAttach2 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onAttach2 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onAttach2 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onAttach2 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onAttach2 isStateSaved : ${this.isStateSaved}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("jhlee", "onActivityCreated1 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onActivityCreated1 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onActivityCreated1 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onActivityCreated1 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onActivityCreated1 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onActivityCreated1 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onActivityCreated1 isStateSaved : ${this.isStateSaved}")
        super.onActivityCreated(savedInstanceState)
        Log.d("jhlee", "----------------onActivityCreated-----------------")

        Log.d("jhlee", "onActivityCreated2 isAdded : ${this.isAdded}")
        Log.d("jhlee", "onActivityCreated2 isResumed : ${this.isResumed}")
        Log.d("jhlee", "onActivityCreated2 isInLayout : ${this.isInLayout}")
        Log.d("jhlee", "onActivityCreated2 isHidden : ${this.isHidden}")
        Log.d("jhlee", "onActivityCreated2 isDetached : ${this.isDetached}")
        Log.d("jhlee", "onActivityCreated2 isRemoving : ${this.isRemoving}")
        Log.d("jhlee", "onActivityCreated2 isStateSaved : ${this.isStateSaved}")
    }


}