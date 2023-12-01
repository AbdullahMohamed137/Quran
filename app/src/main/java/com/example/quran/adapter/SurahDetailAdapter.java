package com.example.quran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran.R;
import com.example.quran.model.SurahDetail;

import java.util.List;

public class SurahDetailAdapter extends RecyclerView.Adapter<SurahDetailAdapter.ViewHolder> {
    private Context context;
    private List<SurahDetail> list;

    public void setFiltredList(List<SurahDetail> filtredList){
        this.list = filtredList;
        notifyDataSetChanged();
    }

    public SurahDetailAdapter(Context context, List<SurahDetail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.surah_detail_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ayaNo.setText(String.valueOf(list.get(position).getAya())
                .replaceAll("0","٠")
                .replaceAll("1","١")
                .replaceAll("2","٢")
                .replaceAll("3","٣")
                .replaceAll("4","٤")
                .replaceAll("5","٥")
                .replaceAll("6","٦")
                .replaceAll("7","٧")
                .replaceAll("8","٨")
                .replaceAll("9","٩"));
        holder.arabicText.setText(list.get(position).getArabic_text());
        //holder.translation.setText(list.get(position).getTranslation());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ayaNo, arabicText, translation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ayaNo = itemView.findViewById(R.id.surah_no);
            arabicText = itemView.findViewById(R.id.arabic_text);
            translation = itemView.findViewById(R.id.translat);
        }
    }
}
