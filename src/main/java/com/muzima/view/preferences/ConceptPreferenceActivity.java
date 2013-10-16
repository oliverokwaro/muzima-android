package com.muzima.view.preferences;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import com.muzima.MuzimaApplication;
import com.muzima.R;
import com.muzima.adapters.concept.AutoCompleteConceptAdapter;
import com.muzima.adapters.concept.SelectedConceptAdapter;
import com.muzima.api.model.Concept;
import com.muzima.search.api.util.StringUtil;
import com.muzima.view.BaseActivity;

public class ConceptPreferenceActivity extends BaseActivity {
    private static final String TAG = ConceptPreferenceActivity.class.getSimpleName();
    private SelectedConceptAdapter selectedConceptAdapter;
    private ListView selectedConceptListView;
    private AutoCompleteTextView autoCompleteConceptTextView;
    private AutoCompleteConceptAdapter autoCompleteConceptAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        selectedConceptListView = (ListView) findViewById(R.id.concept_preference_list);
        selectedConceptAdapter = new SelectedConceptAdapter(getApplicationContext(), R.layout.item_concept_list,
                ((MuzimaApplication)getApplicationContext()).getConceptController());
        selectedConceptListView.setAdapter(selectedConceptAdapter);

        autoCompleteConceptTextView = (AutoCompleteTextView) findViewById(R.id.concept_add_concept);
        autoCompleteConceptAdapter = new AutoCompleteConceptAdapter(getApplicationContext(), R.layout.item_concept_autocomplete);
        autoCompleteConceptTextView.setAdapter(autoCompleteConceptAdapter);
        autoCompleteConceptTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                Concept selectedConcept = (Concept) parent.getItemAtPosition(position);
                selectedConceptAdapter.add(selectedConcept);
                selectedConceptAdapter.notifyDataSetChanged();
                autoCompleteConceptTextView.setText(StringUtil.EMPTY);
            }
        });
    }

    protected int getContentView() {
        return R.layout.activity_concept_preference;
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedConceptAdapter.reloadData();
    }
}
