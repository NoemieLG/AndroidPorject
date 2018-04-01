package com.example.noemie.projectapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseInBddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseInBddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseInBddFragment extends Fragment {

    private TextView nom;
    private TextView date;
    private TextView latitude;
    private TextView longitude;
    private TextView tps;
    private TextView com;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CourseInBddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseInBddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseInBddFragment newInstance(String param1, String param2) {
        CourseInBddFragment fragment = new CourseInBddFragment();
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
        View view = inflater.inflate(R.layout.fragment_course_in_bdd, container, false);
        nom = view.findViewById(R.id.nom);
        date = view.findViewById(R.id.datePicker);
        latitude = view.findViewById(R.id.lat);
        longitude = view.findViewById(R.id.lon);
        tps = view.findViewById(R.id.tps);
        com = view.findViewById(R.id.com);
        return view;
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
    }

    public void setCourseFromBdd(CourseTable course){
        if(course != null){
            nom.setText("Nom du sprinter: " + course.getNom());
            date.setText("Date de la course: " + course.getDate());
            latitude.setText("Latitude: " + course.getLat());
            longitude.setText("Longitude: " + course.getLon());
            tps.setText("Temps: " + course.getTemps());
            com.setText("Commentaire: " + course.getCom());
        }
    }
}
