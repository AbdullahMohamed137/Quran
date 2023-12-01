package com.example.quran.response;

import com.example.quran.model.SurahDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurahDetailResponse {

    @SerializedName("result")
    private List<SurahDetail> result;

    public List<SurahDetail> getResult() {
        return result;
    }

    public void setResult(List<SurahDetail> result) {
        this.result = result;
    }
}
