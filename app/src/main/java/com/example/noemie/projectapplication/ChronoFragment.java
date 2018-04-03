package com.example.noemie.projectapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChronoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChronoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChronoFragment extends Fragment {

    Chronometer chrono;
    Button start, stop, reset;
    TextView title;
    long timeWhenStopped = 0;
    boolean currentlyTiming = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChronoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChronoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChronoFragment newInstance(String param1, String param2) {
        ChronoFragment fragment = new ChronoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chrono, container, false);
        chrono = view.findViewById(R.id.chronometer2);
        title = view.findViewById(R.id.textView2);
        start = view.findViewById(R.id.start_xml);
        reset = view.findViewById(R.id.reset_xml);
        stop = view.findViewById(R.id.stop_xml);

        start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               startChrono();
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                resetChrono();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gomap();
            }
        });

        return view;
    }

    public void startChrono(){
        if(!currentlyTiming){
            chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chrono.start();
            start.setText("Pause");
            currentlyTiming = true;
        } else {
            timeWhenStopped = chrono.getBase() - SystemClock.elapsedRealtime();
            chrono.stop();
            start.setText("Start");
            currentlyTiming = false;
        }
    }

    public void resetChrono(){
        timeWhenStopped = 0;
        chrono.setBase(SystemClock.elapsedRealtime());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void gomap();
    }
}
