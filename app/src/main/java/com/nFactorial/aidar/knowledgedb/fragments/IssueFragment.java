package com.nFactorial.aidar.knowledgedb.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.nFactorial.aidar.knowledgedb.DatabaseManager;
import com.nFactorial.aidar.knowledgedb.KnowledgeDB;
import com.nFactorial.aidar.knowledgedb.R;
import com.nFactorial.aidar.knowledgedb.activities.SolveIssueActivity;
import com.google.firebase.database.DataSnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IssueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IssueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssueFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    EditText issueEditText;
    DatabaseManager databaseManager;
    DataSnapshot companySnapshot;
    public IssueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssueFragment newInstance(String param1, String param2) {
        IssueFragment fragment = new IssueFragment();
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
        View view = inflater.inflate(R.layout.fragment_issue, container, false);

        //companySnapshot = DatabaseManager.getInstance().getTransferSnapshot();
        databaseManager = DatabaseManager.getInstance();

        //final String companyName = (String) companySnapshot.child(KnowledgeDB.getResourceString(R.string.dbTitle)).getValue();
        //getSupportActionBar().setTitle("Казахтелеком");
        issueEditText = (EditText) view.findViewById(R.id.issue_desription_editText);

        //Make editText multiline, other way doesn't work with keyboard
        issueEditText.setHorizontallyScrolling(false);
        issueEditText.setMaxLines(Integer.MAX_VALUE);
        issueEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        //Search button in keyboard
        issueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    startSolveIssueActivity();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

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

    private void startSolveIssueActivity(){
        companySnapshot = databaseManager.findCompanyByName("Казахтелеком".toString());
        Intent intentToStartSolveIssueActivity = new Intent(getActivity(), SolveIssueActivity.class);
        DatabaseManager.getInstance().setTransferSnapshot(companySnapshot);
        intentToStartSolveIssueActivity.putExtra(KnowledgeDB.getResourceString(R.string.javaIssue), issueEditText.getText().toString());
        startActivity(intentToStartSolveIssueActivity);
    }
}
