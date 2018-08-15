package com.verbosetech.yoohoo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.verbosetech.yoohoo.CountrySelection.Country;
import com.verbosetech.yoohoo.CountrySelection.CountryCodeAdapter;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.utils.Constant;

import java.util.List;


public class CountrySelectionActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Toolbar toolbar;
    TextView textView_toolbar;
    EditText et_searchbar;
    RecyclerView recyclerView;
    TextView tv_error_no_country;
    ImageButton imgbtn_close;
    CountryCodeAdapter countryCodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_selection);
        imgbtn_close = (ImageButton) findViewById(R.id.imgbtn_close);
        imgbtn_close.setOnClickListener(this);

        String image_id = getIntent().getStringExtra(Constant.INDEX);

        tv_error_no_country = (TextView) findViewById(R.id.tv_error_no_country);
        et_searchbar = (EditText) findViewById(R.id.et_searchbar);
        et_searchbar.addTextChangedListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        List<Country> countries = Country.getLibraryMasterCountriesEnglish();

        countryCodeAdapter = new CountryCodeAdapter(countries, this, image_id);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(countryCodeAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_close:
                et_searchbar.setText("");
                imgbtn_close.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String filterText = et_searchbar.getText().toString().trim().toLowerCase();
        countryCodeAdapter.filter(filterText);
        if (countryCodeAdapter.getItemCount() == 0)
            tv_error_no_country.setVisibility(View.VISIBLE);
        else
            tv_error_no_country.setVisibility(View.GONE);

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!et_searchbar.getText().toString().isEmpty())
            imgbtn_close.setVisibility(View.VISIBLE);
        else
            imgbtn_close.setVisibility(View.GONE);

    }
}
