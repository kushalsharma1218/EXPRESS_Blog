package com.example.expressblog.Fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expressblog.Adapters.DraftAdapter;
import com.example.expressblog.R;
import com.example.expressblog.database.DraftDatabase;
import com.example.expressblog.entities.Draft;

import java.util.ArrayList;
import java.util.List;

public class DraftFragment extends Fragment {



    private RecyclerView recyclerView;
    private List<Draft> draftsList;

    private DraftAdapter draftAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DraftFragment() {

    }




    public static DraftFragment newInstance(String param1, String param2) {
        DraftFragment fragment = new DraftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View fragmentView = inflater.inflate(R.layout.fragment_draft, container, false);
        recyclerView = fragmentView.findViewById(R.id.draft_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        draftsList = new ArrayList<>();
        draftAdapter = new DraftAdapter(draftsList, new DraftAdapter.DraftAdapterListener() {
            @Override
            public void sendData(String title, String blogBody) {

            }
        },getContext());
        recyclerView.setAdapter(draftAdapter);

        getDrafts();
        return fragmentView;
    }



    private void getDrafts(){
        Log.e("GetDraft Called","Called Success");
        @SuppressLint("StaticFieldLeak")
        class GetDraftsTask extends AsyncTask<Void, Void, List<Draft>> {

            @Override
            protected List<Draft> doInBackground(Void... voids) {
                Log.e("GetDraft Called","Called Success 2");
                return DraftDatabase
                        .getDatabase(getContext())
                        .draftDao().getAllDraft();
            }

            @Override
            protected void onPostExecute(List<Draft> drafts) {
                Log.e("GetDraft Called","Called Success 3");
                super.onPostExecute(drafts);
                if(draftsList.size() == 0){
                    draftsList.addAll(drafts);
                    draftAdapter.notifyDataSetChanged();

                }
                else{
                    draftsList.add(0, drafts.get(0));
                    draftAdapter.notifyItemInserted(0);
                }

                recyclerView.smoothScrollToPosition(0);
            }
        }

        new GetDraftsTask().execute();
    }
}