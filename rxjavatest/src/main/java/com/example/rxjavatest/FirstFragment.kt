package com.example.rxjavatest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    var observableTest = ObservableTest()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_observer_create).setOnClickListener {
            observableTest.observableCreate()
        }
        view.findViewById<Button>(R.id.button_observer_create_complete).setOnClickListener {
            observableTest.observableCreateComplete()
        }
        view.findViewById<Button>(R.id.button_observer_create_error).setOnClickListener {
            observableTest.observableCreateError()
        }
        view.findViewById<Button>(R.id.button_observer_just).setOnClickListener {
            observableTest.observableJust()
        }
        view.findViewById<Button>(R.id.button_observer_from_array).setOnClickListener {
            observableTest.fromArray()
        }
        view.findViewById<Button>(R.id.button_observer_from_iterable).setOnClickListener {
            observableTest.fromIterable()
        }
        view.findViewById<Button>(R.id.button_observer_from_array4).setOnClickListener {
        }
        view.findViewById<Button>(R.id.button_observer_from_future).setOnClickListener {
            observableTest.fromFuture()
        }
        view.findViewById<Button>(R.id.button_observer_from_publisher).setOnClickListener {
            observableTest.fromPublisher()
        }
        view.findViewById<Button>(R.id.button_observer_from_callable).setOnClickListener {
            observableTest.fromCallable()
        }
        view.findViewById<Button>(R.id.button_single).setOnClickListener {
            observableTest.single()
        }
        view.findViewById<Button>(R.id.button_completable).setOnClickListener {
            observableTest.completable()
        }
    }
}