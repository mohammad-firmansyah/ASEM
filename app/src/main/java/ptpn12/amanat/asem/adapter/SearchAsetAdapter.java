package ptpn12.amanat.asem.adapter;

import static android.content.Context.MODE_PRIVATE;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import ptpn12.amanat.asem.AsemApp;
import ptpn12.amanat.asem.DetailAsetActivity;
import ptpn12.amanat.asem.LonglistAsetActivity;
import ptpn12.amanat.asem.R;
import ptpn12.amanat.asem.UpdateAsetActivity;
import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.AsetModel2;
import ptpn12.amanat.asem.api.model.DeleteModel;
import ptpn12.amanat.asem.api.model.Search;
import ptpn12.amanat.asem.utils.utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAsetAdapter extends RecyclerView.Adapter<SearchAsetAdapter.ViewHolder> {

    private AsetInterface asetInterface;
    List<Search> dataSearch;
    Context context;
    SharedPreferences sharedPreferences;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String TAG = "cekTAG";

    Dialog customDialogBelumApprove, customDialogCekDataReject, customDialogFotoBelumLengkap;

    public SearchAsetAdapter(List<Search> data, LonglistAsetActivity longlistAsetActivity) {
        this.dataSearch = data;
        this.context = longlistAsetActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ly_longlist_aset,parent,false);
        SearchAsetAdapter.ViewHolder viewHolder = new SearchAsetAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);
        final Search mySearchData = dataSearch.get(position);

        holder.tvTglInput.setText(mySearchData.getTglInput().split(" ")[0]);
        holder.tvNoSAP.setText(String.valueOf(String.valueOf(mySearchData.getNomorSap())));
        holder.tvAsetJenis.setText(String.valueOf(mySearchData.getAsetJenis()));
        holder.tvAfdeling.setText(String.valueOf(mySearchData.getAfdelingId()));
        holder.tvAsetName.setText(String.valueOf(mySearchData.getAsetName()));

        holder.tvNilaiAset.setText(utils.Formatrupiah(Double.parseDouble(String.valueOf(mySearchData.getNilaiOleh()))));
        holder.tvUmurEkonomis.setText(String.valueOf(utils.MonthToYear(mySearchData.getUmurEkonomisInMonth())));
        holder.tvStatusPosisi.setText(String.valueOf(mySearchData.getStatusPosisi()));
        if (mySearchData.getNoInv() != null) {
            holder.tvNoinv.setText(String.valueOf(mySearchData.getNoInv()));
        } else {
            holder.tvNoinv.setText("-");
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
                Integer hak_akses_id = Integer.valueOf(sharedPreferences.getString("hak_akses_id", "0"));

//                if (mySearchData.getStatusPosisiID() == 5 && hak_akses_id == 7) {
//                    Intent intent = new Intent(context, UpdateFotoQrAsetActivity.class);
//                    intent.putExtra("id",(mySearchData.getAsetId()));
//                    context.startActivity(intent);
//                    return;
//                }
//                if (mySearchData.getAsetFotoQrStatus() != null && hak_akses_id == 7){
//
//                    Intent intent = new Intent(context, UpdateFotoQrAsetActivity.class);
//                    intent.putExtra("id",(mySearchData.getAsetId()));
//                    context.startActivity(intent);
//                    return;
//                }

                Intent intent = new Intent(context, UpdateAsetActivity.class);
                intent.putExtra("id",(mySearchData.getAsetId()));
                context.startActivity(intent);
            }
        });

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAsetActivity.class);
                intent.putExtra("id",(mySearchData.getAsetId()));
                context.startActivity(intent);
            }
        });

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

                        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);
                        Call<DeleteModel> call = asetInterface.deleteReport(mySearchData.getAsetId());

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

        int id = mySearchData.getAsetId();

        holder.btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int statpos = mySearchData.getStatusPosisiID();
                if (statpos == 2||statpos == 4||statpos == 6||statpos == 8||statpos == 10){
                    initDialogBelomApprove();
                } else if(statpos == 1 && mySearchData.getStatusReject()!=null){
                    initDataReject();
                }else if (mySearchData.getAsetJenis().equals("tanaman") && (mySearchData.getFotoAset1() == null ||
                        mySearchData.getFotoAset2() == null || mySearchData.getFotoAset3() == null ||
                        mySearchData.getFotoAset4() == null || mySearchData.getFotoAset5() == null)){
                    initDialogFotoBelomLengkap();
                }else{
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
            if(mySearchData.getStatusPosisiID()==1){ //if status posisi operator
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnHapus.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
                //hapus statusreject, dan keterangan reject
            } else if(mySearchData.getStatusPosisiID()== 5){
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.GONE);
                if (mySearchData.getFotoAsetQr() != null){
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
            if(mySearchData.getStatusPosisiID()==2 ){ //if status posisi pending dan asisten
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            } else if ( mySearchData.getStatusPosisiID()==3 ) {
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
            if(mySearchData.getStatusPosisiID() == 4) { //if statpos pending astuu dan astuu
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            } else if(mySearchData.getStatusPosisiID()== 5) {
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnKirim.setOnClickListener(v -> {
                    if (mySearchData.getFotoAsetQr() == null) {
                        Toast.makeText(context.getApplicationContext(), "Mohon Foto Aset dengan QRCODE Dilengkapi oleh Operator", Toast.LENGTH_SHORT).show();
                    } else {
                        sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
                        String user_id = sharedPreferences.getString("user_id", "-");
                        showDialogKirim("Yakin Kirim Data?",
                                asetInterface.kirimDataAset(id, Integer.parseInt(user_id))
                        );
                    }
                });
                if (mySearchData.getFotoAsetQr() != null){
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
            if(mySearchData.getStatusPosisiID()== 6 || mySearchData.getStatusPosisiID()== 7){ //if statpos pending askep dan askep
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
            if(mySearchData.getStatusPosisiID()== 8 || mySearchData.getStatusPosisiID()== 9){ //if statpos pending mnj dan mnj
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
            if (mySearchData.getStatusPosisiID()== 10 ) { //if statpos pendingkasi dan kasi
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnKirim.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnQR.setVisibility(View.GONE);
                holder.btnQRijo.setVisibility(View.GONE);
            } else if (mySearchData.getStatusPosisiID()== 11){
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

        // jika data di posisi reject maka bikin bg jadi kuning
        // selain posisi tersebut bg normal
        if (mySearchData.getKetReject()!=null){
            //holder.cvRuang.setBackgroundColor(getColor(context,1));
            holder.bgCardView.setBackgroundResource(R.color.Khaki);
        } else{
            holder.bgCardView.setBackgroundResource(R.color.white);
        }


    }

    void initDialogFotoBelomLengkap(){
        customDialogFotoBelumLengkap = new Dialog(context, R.style.MyAlertDialogTheme);
        customDialogFotoBelumLengkap.setContentView(R.layout.dialog_foto);
        customDialogFotoBelumLengkap.setCanceledOnTouchOutside(false);
        customDialogFotoBelumLengkap.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        customDialogFotoBelumLengkap.show();

        Button btnTutup = customDialogFotoBelumLengkap.findViewById(R.id.btnTutup);
        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { customDialogFotoBelumLengkap.dismiss();}
        });
    }

    void showDialogKirim(String customtext,Call<AsetModel2> call) {

        final Dialog dialog =new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ly_kirim_sukses);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
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

    @Override
    public int getItemCount() {
        if (dataSearch != null)
            return dataSearch.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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

        public ViewHolder(@NonNull View v) {
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
