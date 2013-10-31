package com.muzima.service;

import android.content.SharedPreferences;

import com.muzima.MuzimaApplication;
import com.muzima.testSupport.CustomTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.muzima.utils.Constants.COHORT_PREFIX_PREF;
import static com.muzima.utils.Constants.COHORT_PREFIX_PREF_KEY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(CustomTestRunner.class)
public class PreferenceHelperTest {

    private PreferenceHelper preferenceHelper;
    private MuzimaApplication muzimaApplication;
    private SharedPreferences sharedPref;


    @Before
    public void setUp() throws Exception {
        muzimaApplication = mock(MuzimaApplication.class);
        sharedPref = mock(SharedPreferences.class);
        preferenceHelper = new PreferenceHelper(muzimaApplication);

        when(muzimaApplication.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPref);
    }

    @Test
    public void getCohorts_shouldReturnEmptyListWhenNoCohortsInPreferences() throws Exception {
        when(muzimaApplication.getSharedPreferences(COHORT_PREFIX_PREF, android.content.Context.MODE_PRIVATE)).thenReturn(sharedPref);

        assertThat(preferenceHelper.getCohortPrefixes().isEmpty(), is(true));
    }

    @Test
    public void getCohorts_shouldReturnCohortPrefixesInListWhenCohortsPrefixesDefinedInPreferences() throws Exception {
        when(muzimaApplication.getSharedPreferences(COHORT_PREFIX_PREF, android.content.Context.MODE_PRIVATE)).thenReturn(sharedPref);
        when(sharedPref.getString(COHORT_PREFIX_PREF_KEY, "")).thenReturn("[\"Prefix1\",\"Prefix2\",\"Prefix3\"]");

        assertThat(preferenceHelper.getCohortPrefixes().size(), is(3));
    }

    @Test
    public void putCohorts_shouldAddCohortPrefixesCommit() throws Exception {
        when(muzimaApplication.getSharedPreferences(COHORT_PREFIX_PREF, android.content.Context.MODE_PRIVATE)).thenReturn(sharedPref);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        when(sharedPref.edit()).thenReturn(editor);

        List<String> cohortPrefixes = new ArrayList<String>() {{
        }};

        preferenceHelper.putCohortPrefixes(cohortPrefixes);

        verify(editor).putString(anyString(), anyString());
        verify(editor).commit();
        verifyNoMoreInteractions(editor);
    }
}
