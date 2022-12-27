package com.example.asem.adapter;


import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;
import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.startActivity;

import static com.example.asem.LonglistAsetActivity.baseUrl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Aset2Adapter  extends RecyclerView.Adapter<Aset2Adapter.ViewHolder>{

    private AsetInterface asetInterface;
    Dialog customDialogBelumApprove;
    Dialog customDialogCekDataReject;

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

        // jika data di posisi reject maka bikin bg jadi kuning
        // selain posisi tersebut bg normal
        if (myPostData2.getKetReject()!=null){
            //holder.cvRuang.setBackgroundColor(getColor(context,1));
            holder.bgCardView.setBackgroundResource(R.color.Khaki);
        } else{
            holder.bgCardView.setBackgroundResource(R.color.white);
        }

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog =new Dialog(context);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setContentView(R.layout.dialog_delete);
                dialog.show();
                Button cancel = dialog.findViewById(R.id.btnTidakKirim);
                Button ya = dialog.findViewById(R.id.btnYaKirim);

                cancel.setOnClickListener(v -> {dialog.dismiss();});

                ya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AsetInterface asetInterface;
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        asetInterface = retrofit.create(AsetInterface.class);
                        Call<DeleteModel> call = asetInterface.deleteReport(myPostData2.getAsetId());

                        call.enqueue(new Callback<DeleteModel>(){

                            @Override
                            public void onResponse(Call<DeleteModel> call, Response<DeleteModel> response) {
                                if (response.isSuccessful() && response.body() != null){
                                    context.startActivity(new Intent(context,LonglistAsetActivity.class));
                                    Toast.makeText(context.getApplicationContext(),"aset terhapus",Toast.LENGTH_LONG).show();
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

                if (myPostData2.getAsetFotoQrStatus() != null && hak_akses_id == 7){

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
                int statpos = myPostData2.getStatusPosisiID();
                if (statpos == 2 || statpos == 4 ||statpos == 6|| statpos==8|| statpos==10){ //init statpos pending
                    initDialogBelomApprove();
                    //Toast.makeText(context, "masuk ga", Toast.LENGTH_SHORT).show();
                } else if (statpos == 1 && myPostData2.getStatusReject()!=null){
                    initDataReject();
                }
                else{
                    sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
                    String user_id = sharedPreferences.getString("user_id", "-");
                    showDialogKirim("Yakin Kirim Data?",
                            asetInterface.kirimDataAset(id, Integer.parseInt(user_id))
                    );
                }
            }
        });

        sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");
        //Log.d("asetapix", hak_akses_id);

        if(hak_akses_id.equals("7") ){ //operator
            if(myPostData2.getStatusPosisiID()==1){ //if status posisi operator
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnHapus.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
                //hapus statusreject, dan keterangan reject
            } else if(myPostData2.getStatusPosisiID()== 5){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.GONE);
                if (myPostData2.getFotoAsetQr() != null){
                    //tombol edit hilang, karna fotoasetqr sudah terisi
                    holder.btnEdit.setVisibility(View.GONE);

                    holder.btnQRijo.setVisibility(View.VISIBLE);
                    holder.btnQR.setVisibility(View.GONE);
                }else{
                    holder.btnQRijo.setVisibility(View.GONE);
                    holder.btnQR.setVisibility(View.VISIBLE);
                }
            }else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            }
        } else if(hak_akses_id.equals("6")){ //asisten
            if(myPostData2.getStatusPosisiID()==2 ){ //if status posisi pending dan asisten
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            } else if ( myPostData2.getStatusPosisiID()==3 ) {
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            }
            else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            }
        } else if (hak_akses_id.equals("5") ){ //astuu
            if(myPostData2.getStatusPosisiID() == 4) { //if statpos pending astuu dan astuu
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            } else if(myPostData2.getStatusPosisiID()== 5) {
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnKirim.setOnClickListener(v -> {
                    if (myPostData2.getFotoAsetQr() == null) {
                        Toast.makeText(context.getApplicationContext(), "Mohon Foto Aset dengan QRCODE Dilengkapi oleh Operator", Toast.LENGTH_SHORT).show();
                    } else {
                        sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
                        String user_id = sharedPreferences.getString("user_id", "-");
                        showDialogKirim("Yakin Kirim Data?",
                                asetInterface.kirimDataAset(id, Integer.parseInt(user_id))
                        );
                    }
                });
                if (myPostData2.getFotoAsetQr() != null){
                    holder.btnQRijo.setVisibility(View.VISIBLE);
                    holder.btnQR.setVisibility(View.GONE);
                }else{
                    holder.btnQRijo.setVisibility(View.GONE);
                    holder.btnQR.setVisibility(View.VISIBLE);
                }

            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            }
        }else if (hak_akses_id.equals("4")){ //askep
            if(myPostData2.getStatusPosisiID()== 6 || myPostData2.getStatusPosisiID()== 7){ //if statpos pending askep dan askep
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            }
        } else if (hak_akses_id.equals("3")){ //mnj
            if(myPostData2.getStatusPosisiID()== 8 || myPostData2.getStatusPosisiID()== 9){ //if statpos pending mnj dan mnj
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            } else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            }
        } else if (hak_akses_id.equals("2")) { //kasi
            if (myPostData2.getStatusPosisiID()== 10 ) { //if statpos pendingkasi dan kasi
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            } else if (myPostData2.getStatusPosisiID()== 11){
                holder.btnKirim.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            }
            else {
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnKirim.setVisibility(View.GONE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            }
        }



//        switch (myPostData2.getStatusReject()){
//            case :
//            case posisi reject:
//                holder.cvAset.setBackgroundResource(R.drawable.bg_border_cancel);
//                break;
//            default:
//                holder.cvAset.setBackgroundResource(R.drawable.bg_border);
//                // bikin bg cardview longlist aset reject
//                if (!myPostData2.getAsetId().equals("0")){
//                    holder.cvAset.setBackgroundResource(R.color.Khaki);
//                }
//                break;
//        }

    }

    void showDialogKirim(String customtext,Call<AsetModel2> call) {

        final Dialog dialog =new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ly_kirim_sukses);
        dialog.show();
        Button cancel = dialog.findViewById(R.id.btnTidakKirim);
        Button kirim = dialog.findViewById(R.id.btnYaKirim);

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
                        //cek image apakah sudah terfoto semua atau belum
                        //get response body data,if (url img 1-4 = adaa) then bisa kirim, else
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

    void initDialogBelomApprove(){
        customDialogBelumApprove = new Dialog(context, R.style.MyAlertDialogTheme);
        customDialogBelumApprove.setContentView(R.layout.dialog_pending);
        customDialogBelumApprove.setCanceledOnTouchOutside(false);
        customDialogBelumApprove.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        customDialogBelumApprove.show();

        Button btnTutup = customDialogBelumApprove.findViewById(R.id.btnTutup);
        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { customDialogBelumApprove.dismiss();}
        });
    }

    void initDataReject(){
        customDialogCekDataReject = new Dialog(context, R.style.MyAlertDialogTheme);
        customDialogCekDataReject.setContentView(R.layout.dialog_datareject);
        customDialogCekDataReject.setCanceledOnTouchOutside(false);
        customDialogCekDataReject.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        customDialogCekDataReject.show();

        Button btnTutup = customDialogCekDataReject.findViewById(R.id.btnTutup);
        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { customDialogCekDataReject.dismiss(); }
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
        View btnQR;
        View btnQRijo;
        LinearLayout cvAset;
        RelativeLayout bgCardView;
        CardView cvKirimSukses;


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
            btnQR = v.findViewById(R.id.btnQR);
            btnQRijo = v.findViewById(R.id.btnQRijo);

            cvKirimSukses = v.findViewById(R.id.cvKirimSukses);
            cvAset = v.findViewById(R.id.cvAset);
            bgCardView = v.findViewById(R.id.bgCardView);

        }
    }
}
