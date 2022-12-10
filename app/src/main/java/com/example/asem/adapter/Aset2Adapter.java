package com.example.asem.adapter;


import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asem.AddAsetActivity;
import com.example.asem.DetailAsetActivity;
import com.example.asem.LonglistAsetActivity;
import com.example.asem.R;
import com.example.asem.UpdateAsetActivity;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.AsetModel2;
import com.example.asem.api.model.Data2;
import com.example.asem.api.model.DeleteModel;
import com.example.asem.utils.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Aset2Adapter  extends RecyclerView.Adapter<Aset2Adapter.ViewHolder>{


    List<Data2> myAsetData;
    Context context;
    SharedPreferences sharedPreferences;
    private static final String PREF_LOGIN = "LOGIN_PREF";

    public Aset2Adapter(List<Data2> data, LonglistAsetActivity longlistAsetActivity){
        this.myAsetData =data;
        this.context = longlistAsetActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ly_longlist_aset,parent,false);
        Aset2Adapter.ViewHolder viewHolder = new Aset2Adapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data2 myPostData2 = myAsetData.get(position);

        holder.tvTglInput.setText(myPostData2.getTglInput());
        holder.tvNoSAP.setText(String.valueOf(String.valueOf(myPostData2.getNomorSap())));
        holder.tvAsetJenis.setText(String.valueOf(myPostData2.getAsetJenis()));
        holder.tvAfdeling.setText(String.valueOf(myPostData2.getAfdelingId()));
        holder.tvAsetName.setText(String.valueOf(myPostData2.getAsetName()));

        holder.tvNilaiAset.setText(utils.Formatrupiah(Double.parseDouble(String.valueOf(myPostData2.getNilaiOleh()))));
        holder.tvUmurEkonomis.setText(String.valueOf(utils.MonthToYear(myPostData2.getUmurEkonomisInMonth())));
        holder.tvStatusPosisi.setText(String.valueOf(myPostData2.getStatusPosisi()));
        if (myPostData2.getNoInv() != null) {
            holder.tvNoinv.setText(String.valueOf(myPostData2.getNoInv()));
        } else {
            holder.tvNoinv.setText("-");
        }


        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsetInterface asetInterface;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://202.148.9.226:7710/mnj_aset_repo/public/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                asetInterface = retrofit.create(AsetInterface.class);
                Call<DeleteModel> call = asetInterface.deleteReport(myPostData2.getAsetId());

                call.enqueue(new Callback<DeleteModel>(){

                    @Override
                    public void onResponse(Call<DeleteModel> call, Response<DeleteModel> response) {
                        if (response.isSuccessful() && response.body() != null){
                            Toast.makeText(context.getApplicationContext(),"aset deleted",Toast.LENGTH_LONG).show();
//                                            startActivity(new Intent(context.getApplicationContext(),LonglistAsetActivity.class));
                            return;
                        }

                        Toast.makeText(context.getApplicationContext(),"error data tidak dapat dimasukan",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<DeleteModel> call, Throwable t) {
                        Log.d("asetapix", "onError : "+t.getMessage());
                        Log.d("asetapix",String.valueOf(call.request().body()));
                        Log.d("asetapix",String.valueOf(call.request().url()));
                        Log.d("asetapix",String.valueOf(call.request().method()));
                        Toast.makeText(context.getApplicationContext(),"error " + t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailAsetActivity.class);
                intent.putExtra("id",(myPostData2.getAsetId()));
                context.startActivity(intent);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAsetActivity.class);
                intent.putExtra("id",(myPostData2.getAsetId()));
                context.startActivity(intent);
            }
        });

        sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");

        if(hak_akses_id == "1"){ //operator
            if(myPostData2.getStatusPosisiID().equals("1")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else if(myPostData2.getStatusPosisiID().equals("5")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if(hak_akses_id=="2"){ //pending asisten
            if(myPostData2.getStatusPosisiID().equals("2")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if (hak_akses_id == "3"){ //asisten
            if(myPostData2.getStatusPosisiID().equals("3")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        }else if (hak_akses_id == "4"){ //pending astu
            if(myPostData2.getStatusPosisiID().equals("4")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if (hak_akses_id == "5"){ //astu
            if(myPostData2.getStatusPosisiID().equals("5")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if (hak_akses_id == "6"){ //pending askep
            if(myPostData2.getStatusPosisiID().equals("6")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if (hak_akses_id ==  "7"){ //askep
            if(myPostData2.getStatusPosisiID().equals("7")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if (hak_akses_id == "8"){ //pending manajer
            if(myPostData2.getStatusPosisiID().equals("7")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if (hak_akses_id == "9"){ //manajer
            if(myPostData2.getStatusPosisiID().equals("9")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if (hak_akses_id == "10"){ //pending kasi
            if(myPostData2.getStatusPosisiID().equals("10")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if(hak_akses_id == "11"){ //kasi
            if(myPostData2.getStatusPosisiID().equals("11")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (myAsetData != null)
            return myAsetData.size();
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTglInput;
        TextView tvNoSAP;
        TextView tvAsetJenis;
        TextView tvAfdeling;
        TextView tvAsetName;
        TextView tvNilaiAset;
        TextView tvUmurEkonomis;
        TextView tvNoinv;
        TextView tvStatusPosisi;
        Button btnDetail;
        Button btnEdit;
        Button btnKirim;
        View btnHapus;

        public ViewHolder (@NonNull View v) {
            super(v);
            tvTglInput = v.findViewById(R.id.tv_tgl_input);
            tvNoSAP= v.findViewById(R.id.tv_nomor_sap);
            tvAsetJenis= v.findViewById(R.id.tv_aset_jenis);
            tvAfdeling= v.findViewById(R.id.tv_afdeling);
            tvAsetName= v.findViewById(R.id.tv_aset_name);
            tvNilaiAset= v.findViewById(R.id.tv_nilai);
            tvUmurEkonomis= v.findViewById(R.id.tv_umur_ekonomis);
            tvNoinv= v.findViewById(R.id.tv_no_inv);
            tvStatusPosisi= v.findViewById(R.id.status_posisi);


            btnDetail = v.findViewById(R.id.btn_detail);
            btnEdit = v.findViewById(R.id.btn_edit);
            btnKirim = v.findViewById(R.id.btn_kirim);
            btnHapus = v.findViewById(R.id.btn_delete);



        }
    }
}
