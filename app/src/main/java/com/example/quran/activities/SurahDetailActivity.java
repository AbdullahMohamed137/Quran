package com.example.quran.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran.R;
import com.example.quran.adapter.SurahDetailAdapter;
import com.example.quran.common.Common;
import com.example.quran.model.SurahDetail;
import com.example.quran.viewmodel.SurahDetailViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SurahDetailActivity extends AppCompatActivity {
    private TextView surahName, surahType,bsmla, surahTranslation;
    private int no;
    private RecyclerView recyclerView;
    private List<SurahDetail> list;
    private SurahDetailAdapter adapter;
    private SurahDetailViewModel surahDetailViewModel;
    private String eng = "english_rwwad";
    private String germ = "german_bubenheim";
    private String type;
    int numbers;
    private SearchView search;
    private String formattedNumber;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);

        search = findViewById(R.id.search_view);
        search.clearFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });



        bsmla = findViewById(R.id.bsmla);
        if (getIntent().getIntExtra(Common.SURAH_NO,0)==9){
            bsmla.setVisibility(View.INVISIBLE);
        }else {
            bsmla.setVisibility(View.VISIBLE);
        }
        surahName = findViewById(R.id.surah_name);
        surahType = findViewById(R.id.type);
       // surahTranslation = findViewById(R.id.translation);
        recyclerView = findViewById(R.id.surah_detail_rv);

        if (getIntent().getStringExtra(Common.SURAH_TYPE).equals("Meccan")){
            type = "مَكِيَّة";
        }else {
            type = "مَدَنِيَّة";
        }

        numbers = getIntent().getIntExtra(Common.SURAH_TOTAL_AYA,0);
        Locale arabicLocal = new Locale("ar");
        NumberFormat numberFormat = NumberFormat.getInstance(arabicLocal);
        formattedNumber = numberFormat.format(numbers);

        no = getIntent().getIntExtra(Common.SURAH_NO,0);
        surahName.setText(getIntent().getStringExtra(Common.SURAH_NAME));
        surahType.setText("عدد الأيات"+" "+formattedNumber+" أية" +
                            " ( "+ type + " )");
        //surahTranslation.setText(getIntent().getStringExtra(Common.SURAH_TRANSLATION));

        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();

        surahTranslation(eng,no);
    }

    private void surahTranslation(String lan, int id) {
        if (list.size()>0){
            list.clear();
        }

        surahDetailViewModel = new ViewModelProvider(this).get(SurahDetailViewModel.class);
        surahDetailViewModel.getSurahDetail(lan,id).observe(this, surahDetailResponse -> {

            for (int i=0; i<surahDetailResponse.getResult().size(); i++){
                list.add(new SurahDetail(surahDetailResponse.getResult().get(i).getId(),
                        surahDetailResponse.getResult().get(i).getSura(),
                        surahDetailResponse.getResult().get(i).getAya(),
                        surahDetailResponse.getResult().get(i).getArabic_text(),
                        surahDetailResponse.getResult().get(i).getTranslation(),
                        surahDetailResponse.getResult().get(i).getFootnotes()));
            }

            if (list.size()!=0){
                adapter = new SurahDetailAdapter(this,list);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void filterList(String newText){
        List<SurahDetail> filtteredList = new ArrayList<>();
        for (SurahDetail aya : list){
            if (String.valueOf(aya.getAya()).contains(newText)){
                filtteredList.add(aya);
            }
        }
        if (filtteredList.isEmpty()){
            Toast.makeText(this, "عدد الأيات "+ formattedNumber, Toast.LENGTH_SHORT).show();
        }else {
            // Log.e("jojo",filtteredList.toString());
            adapter.setFiltredList(filtteredList);
        }
    }
}