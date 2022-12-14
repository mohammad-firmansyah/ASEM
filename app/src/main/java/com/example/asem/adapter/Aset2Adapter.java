package com.example.asem.adapter;


import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;
import static androidx.core.content.ContextCompat.startActivity;

import static com.example.asem.LonglistAsetActivity.baseUrl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asem.AddAsetActivity;
import com.example.asem.DetailAsetActivity;
import com.example.asem.LonglistAsetActivity;
import com.example.asem.R;
import com.example.asem.UpdateAsetActivity;
import com.example.asem.UpdateFotoQrAsetActivity;
import com.example.asem.api.ApiFunction;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Aset;
import com.example.asem.api.model.AsetModel2;
import com.example.asem.api.model.Data2;
import com.example.asem.api.model.DeleteModel;
import com.example.asem.utils.utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Aset2Adapter  extends RecyclerView.Adapter<Aset2Adapter.ViewHolder>{

    private AsetInterface asetInterface;

    Integer id;
    List<Data2> myAsetData;
    Context context;
    SharedPreferences sharedPreferences;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String TAG = "KirimTAG";


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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);


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
                sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
                Integer hak_akses_id = Integer.valueOf(sharedPreferences.getString("hak_akses_id", "0"));

                if (myPostData2.getStatusPosisiID() == 5 && hak_akses_id == 7) {
                    Intent intent = new Intent(context, UpdateFotoQrAsetActivity.class);
                    intent.putExtra("id",(myPostData2.getAsetId()));
                    context.startActivity(intent);
                    return;
                }
                Intent intent = new Intent(context, UpdateAsetActivity.class);
                intent.putExtra("id",(myPostData2.getAsetId()));
                context.startActivity(intent);
            }
        });

        int id = myPostData2.getAsetId();
//        String subunit = myPostData2.getAsetSubUnit();
//        String stat_posisi_id = myPostData2.getStatusPosisiID();

        holder.btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKirim("Yakin Kirim Data?",
                        asetInterface.kirimDataAset(id)
                );
            }
        });

        sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");

        if(hak_akses_id == "1"){ //operator
            if(myPostData2.getStatusPosisiID().equals("1")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                //hapus statusreject, dan keterangan reject
                //btnFAB status visible hanya di op
            } else if(myPostData2.getStatusPosisiID().equals("5")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.GONE);
            }
        } else if(hak_akses_id=="2"){ //pending asisten
            if(myPostData2.getStatusPosisiID().equals("2")){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
            }
        } else if (hak_akses_id.equals("3") ){ //asisten
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
                holder.btnKirim.setOnClickListener(v -> {
                    if(myPostData2.getFotoAsetQr() == null){
                        Toast.makeText(context.getApplicationContext(), "Mohon Foto Aset dengan QRCODE Dilengkapi oleh Operator", Toast.LENGTH_SHORT).show();
                    } else{
                    }
                });
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
            if(myPostData2.getStatusPosisiID().equals("8")){
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

        // jika data di posisi reject maka bikin bg jadi kuning
        // selain posisi tersebut bg normal
//        switch (myPostData2.getStatusPosisiID()){
//            case posisi reject:
//            case posisi reject:
//                holder.cvAset.setBackgroundResource(R.drawable.bg_border_cancel);
//                break;
//            default:
//                holder.cvAset.setBackgroundResource(R.drawable.bg_border);
//                // bikin bg cardview longlist aset reject
//                if (!myPostData2.getStatusReject().equals("0")){
//                    holder.cvAset.setBackgroundResource(R.color.Khaki);
//                }
//                break;
//        }

    }

    void showDialogKirim(String customtext,Call<AsetModel2> call) {

//        Intent intent = getIntent();
//        id = intent.getIntExtra("id", 0);

        final Dialog dialog =new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ly_kirim_sukses);
        dialog.show();
        //TextView textdialog = dialog.findViewById(R.id.tvKirim);
        //TextView texttanya = dialog.findViewById(R.id.tvTanyaKirim);
        Button cancel = dialog.findViewById(R.id.btnTidakKirim);
        Button kirim = dialog.findViewById(R.id.btnYaKirim);

        //textdialog.setText(customtext);
        //texttanya.setText(customtext);

        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        kirim.setOnClickListener(view -> {
            call.enqueue(new Callback<AsetModel2>() {
                @Override
                public void onResponse(@NotNull Call<AsetModel2> call, @NotNull Response<AsetModel2> response) {

                    if (response.isSuccessful() && response.body() != null){
                        Toast.makeText(context.getApplicationContext(), "berhasil mengirim data", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context.getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NotNull Call<AsetModel2> call, @NotNull Throwable t) {
                    Log.d(TAG, "onResponse: teskirim : "+t.getMessage());
                    Toast.makeText(context.getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
            notifyDataSetChanged();
            Intent gas = new Intent(context, LonglistAsetActivity.class);
            context.startActivity(gas);
        });
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
        LinearLayout cvAset;
        Data2 myPostData2;


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

            cvAset = v.findViewById(R.id.cvAset);

        }
    }
}
