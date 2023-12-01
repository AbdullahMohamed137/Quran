package com.example.quran.response;

import com.example.quran.model.Surah;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurahResponse {
    @SerializedName("data")
    private List<Surah> data;

    public List<Surah> getList() {
        return data;
    }

    public void setList(List<Surah> data) {
        this.data = data;
    }
}
