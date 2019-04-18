package com.example.hoangviet.mytravelapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link SelectLanguageDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectLanguageDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectLanguageDialog extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    private RadioGroup selectLanguageGroup;

//    public Dialog SelectLanguageDialog(Bundle savedInstanceState) {
//        // Required empty public constructor
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Select Language");
//
//        return builder.create();
//    }


    public static SelectLanguageDialog newInstance(String param1, String param2) {
        SelectLanguageDialog fragment = new SelectLanguageDialog();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_language_dialog, container, false);
        this.selectLanguageGroup = view.findViewById(R.id.select_language_group);
        this.selectLanguageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnSelectLanguageChanged(group, checkedId);
            }
        });


        return view;
    }
    public void doOnSelectLanguageChanged(RadioGroup group, int checkedId){
        int checkedRadioId = group.getCheckedRadioButtonId();

        if(checkedRadioId== R.id.radio_english) {
           mListener.onSelectLanguageFinish("en_US");
        } else if(checkedRadioId== R.id.radio_vietnamese ) {
            mListener.onSelectLanguageFinish("vi_VN");
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

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
        void onSelectLanguageFinish(String language);
    }

}
