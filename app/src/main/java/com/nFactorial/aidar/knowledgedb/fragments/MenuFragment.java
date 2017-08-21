package com.nFactorial.aidar.knowledgedb.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nFactorial.aidar.knowledgedb.DatabaseManager;
import com.nFactorial.aidar.knowledgedb.KnowledgeDB;
import com.nFactorial.aidar.knowledgedb.ListItem;
import com.nFactorial.aidar.knowledgedb.R;
import com.nFactorial.aidar.knowledgedb.RecyclerViewAdapter;
import com.nFactorial.aidar.knowledgedb.activities.TopicActivity;
import com.google.firebase.database.DataSnapshot;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DatabaseManager databaseManager;
    private DataSnapshot companySpanshot;
    List<ListItem> listItems;
    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        databaseManager = DatabaseManager.getInstance();
        companySpanshot = databaseManager.findCompanyByName("Казахтелеком");
        listItems = getList();
        recyclerViewAdapter = new RecyclerViewAdapter(listItems, this);
        recyclerView = (RecyclerView) view.findViewById(R.id.menu_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerViewAdapter);
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

    @Override
    public void onClick(int position) {
        startTopicActivity(position);
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

    private List<ListItem> getList(){
        DataSnapshot categories = companySpanshot.child(KnowledgeDB.getResourceString(R.string.dbCategories));
        List<ListItem> listItems = new LinkedList<ListItem>();
        for(DataSnapshot dataSnapshot: categories.getChildren()){
            ListItem listItem = new ListItem();
            listItem.setText(dataSnapshot.child(KnowledgeDB.getResourceString(R.string.dbTitle)).getValue().toString());
            listItem.setDataSnapshot(dataSnapshot);
            listItems.add(listItem);
        }
        return  listItems;
    }

    private void startTopicActivity(int position) {
        ListItem listItem = listItems.get(position);
        Intent intentToStartTopicActivity = new Intent(getActivity(), TopicActivity.class);
        intentToStartTopicActivity.putExtra(KnowledgeDB.getResourceString(R.string.dbTitle), listItem.getText());
        databaseManager.setTransferSnapshot(listItem.getDataSnapshot());
        startActivity(intentToStartTopicActivity);
    }

}
