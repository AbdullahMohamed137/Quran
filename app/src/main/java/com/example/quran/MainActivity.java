package com.example.quran;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran.activities.SurahDetailActivity;
import com.example.quran.adapter.SurahAdapter;
import com.example.quran.common.Common;
import com.example.quran.listener.SurahListener;
import com.example.quran.model.Surah;
import com.example.quran.viewmodel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SurahListener {
    private RecyclerView recyclerView;
    private SurahAdapter surahAdapter;
    private List<Surah> list;
    private SurahViewModel surahViewModel;
    private SearchView searchView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        if (getSupportActionBar()!= null){
            getSupportActionBar().hide();
        }

        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        recyclerView = findViewById(R.id.surahRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);
        surahViewModel.getSurah().observe(this,surahResponse -> {
            for (int i=0; i<surahResponse.getList().size(); i++){
                list.add(new Surah(surahResponse.getList().get(i).getNumber(),
                        String.valueOf(surahResponse.getList().get(i).getName()),
                        String.valueOf(surahResponse.getList().get(i).getEnglishName()),
                        String.valueOf(surahResponse.getList().get(i).getEnglishNameTranslation()),
                        surahResponse.getList().get(i).getNumberOfAyahs(),
                        String.valueOf(surahResponse.getList().get(i).getRevelationType())
                ));
            }
            if (list.size()!=0){
                surahAdapter = new SurahAdapter(this,list,this::onSurahListener);
                recyclerView.setAdapter(surahAdapter);
                surahAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onSurahListener(Surah sura) {
        Intent intent = new Intent(this, SurahDetailActivity.class);
        intent.putExtra(Common.SURAH_NO,sura.getNumber());
        intent.putExtra(Common.SURAH_NAME,sura.getName());
        intent.putExtra(Common.SURAH_TOTAL_AYA,sura.getNumberOfAyahs());
        intent.putExtra(Common.SURAH_TYPE,sura.getRevelationType());
        intent.putExtra(Common.SURAH_TRANSLATION,sura.getEnglishNameTranslation());
        startActivity(intent);
    }

    private void filterList(String newText){
        List<Surah> filtteredList = new ArrayList<>();
        for (Surah surah : list){
            if (surah.getName().replaceAll("َ","")
                    .replaceAll("ُ","")
                    .replaceAll("ِ","")
                    .replaceAll("ْ","")
                    .replaceAll("ً","")
                    .replaceAll("ٌ","")
                    .replaceAll("ٍ","")
                    .replaceAll("ّ","")
                    .replaceAll("أ","ا")
                    .replaceAll("إ","ا")
                    .replaceAll("ۡ","")
                    .contains(newText)){
                filtteredList.add(surah);
            }
        }
        if (filtteredList.isEmpty()){
            Toast.makeText(this, "دخل الأسم صح", Toast.LENGTH_SHORT).show();
        }else {
            surahAdapter.setFiltredList(filtteredList);
        }
    }
}
