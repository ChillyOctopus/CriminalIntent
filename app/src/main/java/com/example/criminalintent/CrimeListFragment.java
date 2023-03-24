package com.example.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.criminalintent.Crime;
import com.example.criminalintent.R;

import java.util.List;

public class CrimeListFragment extends Fragment {


    private static final String TAG = "CrimeListFragment";

    private RecyclerView crimeRecyclerView;
    private CrimeAdapter adapter;

    private CrimeListViewModel crimeListViewModel;


    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Crime crime;

        private final TextView titleTextView =
                itemView.findViewById(R.id.crime_title);
        private final TextView dateTextView =
                itemView.findViewById(R.id.crime_date);

        private final ImageView solvedImageView =
                itemView.findViewById(R.id.crime_solved);


        CrimeHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
        }

        void bind(Crime crime) {
            this.crime = crime;
            titleTextView.setText(crime.getTitle());
            dateTextView.setText(crime.getDate().toString());
            solvedImageView.setVisibility(
                    crime.isSolved() ? View.VISIBLE : View.GONE);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), crime.getTitle() + " clicked!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private final List<Crime> crimes;

        CrimeAdapter(List<Crime> crimes) {
            this.crimes = crimes;
        }


        @Override
        public int getItemCount() {
            return crimes.size();
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = crimes.get(position);
            holder.bind(crime);

        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        crimeListViewModel = ViewModelProviders.of(this)
                .get(CrimeListViewModel.class);

        Log.d(TAG, "Total crimes: " + crimeListViewModel.getCrimes().size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container,
                false);

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));
        updateUI();

        return view;
    }

    private void updateUI() {
        List<Crime> crimes = crimeListViewModel.getCrimes();
        adapter = new CrimeAdapter(crimes);
        crimeRecyclerView.setAdapter(adapter);
    }


    static CrimeListFragment newInstance() {
        return new CrimeListFragment();
    }
}

