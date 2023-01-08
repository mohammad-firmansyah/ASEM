package com.example.asem.adapter;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asem.AsemApp;
import com.example.asem.DetailAsetActivity;
import com.example.asem.LonglistAsetActivity;
import com.example.asem.R;
import com.example.asem.UpdateAsetActivity;
import com.example.asem.UpdateFotoQrAsetActivity;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Data;
import com.example.asem.api.model.DeleteModel;
import com.example.asem.db.AsetHelper;
import com.example.asem.db.DatabaseHelper;
import com.example.asem.db.model.Aset;
import com.example.asem.utils.utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsetOfflineAdapter extends RecyclerView.Adapter<AsetOfflineAdapter.AsetViewHolder> {

    SharedPreferences sharedPreferences;
    Context context;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String TAG = "KirimTAG";

    ArrayList<Aset> listAset;// = new ArrayList<>();
    DatabaseHelper dbAset;
    AsetInterface asetInterface;

    Data[] myAsetData;

    public AsetOfflineAdapter(Context context, ArrayList<Aset> listAset) {
        this.listAset = listAset;
        this.context = context;
        this.dbAset = new DatabaseHelper(context);
        this.asetInterface = AsemApp.getApiClient().create(AsetInterface.class);
    }

    public ArrayList<Aset> getListAset() {
        return listAset;
    }
    public void setListAset(ArrayList<Aset> listAset) {
        if (listAset.size() > 0) {
            this.listAset.clear();
        }
        this.listAset.addAll(listAset);
    }
//
//    public void addItem(Aset note) {
//        this.listAset.add(note);
//        notifyItemInserted(listAset.size() - 1);
//    }
//    public void updateItem(int position, Aset note) {
//        this.listAset.set(position, note);
//        notifyItemChanged(position, note);
//    }
//    public void removeItem(int position) {
//        this.listAset.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position,listAset.size());
//    }

    @NonNull
    @Override
    public AsetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ly_longlist_aset, viewGroup, false);
        return new AsetViewHolder(view);
    }
        @Override
        public void onBindViewHolder(@NonNull AsetViewHolder holder, int position) {
            holder.tvTglInput.setText(listAset.get(position).getTglInput());
            holder.tvNoSAP.setText(String.valueOf(listAset.get(position).getNomorSap()));
            holder.tvAsetJenis.setText(String.valueOf(listAset.get(position).getAsetJenis()));
            holder.tvAfdeling.setText(String.valueOf(listAset.get(position).getAfdelingId()));
            holder.tvAsetName.setText(String.valueOf(listAset.get(position).getAsetName()));

            holder.tvNilaiAset.setText(utils.Formatrupiah(Double.parseDouble(String.valueOf(listAset.get(position).getNilaiOleh()))));
            holder.tvUmurEkonomis.setText(utils.MonthToYear(listAset.get(position).getUmurEkonomisInMonth()));
            holder.tvStatusPosisi.setText(String.valueOf(listAset.get(position).getStatusPosisi()));
            if (listAset.get(position).getNoInv() != null) {
                holder.tvNoinv.setText(String.valueOf(listAset.get(position).getNoInv()));
            } else {
                holder.tvNoinv.setText("-");
            }


            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog =new Dialog(context);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                    dialog.setContentView(R.layout.dialog_delete);
//                    dialog.show();
//                    Button cancel = dialog.findViewById(R.id.btnTidakKirim);
//                    Button ya = dialog.findViewById(R.id.btnYaKirim);
//
//                    cancel.setOnClickListener(v -> {dialog.dismiss();});
//
//                    ya.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AsetInterface asetInterface;
//                            Retrofit retrofit = new Retrofit.Builder()
//                                    .baseUrl(AsemApp.BASE_URL_API)
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .build();
//
//                            asetInterface = retrofit.create(AsetInterface.class);
//                            Call<DeleteModel> call = asetInterface.deleteReport(listAset.get(holder.getAdapterPosition()).getAsetId());
//
//                            call.enqueue(new Callback<DeleteModel>(){
//
//                                @Override
//                                public void onResponse(Call<DeleteModel> call, Response<DeleteModel> response) {
//                                    if (response.isSuccessful() && response.body() != null){
//                                        context.startActivity(new Intent(context, LonglistAsetActivity.class));
//                                        Toast.makeText(context.getApplicationContext(),"aset terhapus",Toast.LENGTH_LONG).show();
//                                        return;
//                                    }
//
//                                    Toast.makeText(context.getApplicationContext(),"error data tidak dapat dimasukan",Toast.LENGTH_LONG).show();
//                                }
//
//                                @Override
//                                public void onFailure(Call<DeleteModel> call, Throwable t) {
//                                    Toast.makeText(context.getApplicationContext(),"error " + t.getMessage(),Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        }
//                    });
                    Toast.makeText(context,"Masuk Kirim Offline", Toast.LENGTH_SHORT).show();
                }
            });
            holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(context, DetailAsetActivity.class);
//                    intent.putExtra("id",listAset.get(holder.getAdapterPosition()).getAsetId());
//                    context.startActivity(intent);
                    Toast.makeText(context,"Masuk Detail Offline",Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
//                    Integer hak_akses_id = Integer.valueOf(sharedPreferences.getString("hak_akses_id", "0"));
//
//                    if (listAset.get(holder.getAdapterPosition()).getStatusPosisiID() == 5 && hak_akses_id == 7) {
//                        Intent intent = new Intent(context, UpdateFotoQrAsetActivity.class);
//                        intent.putExtra("id",listAset.get(holder.getAdapterPosition()).getAsetId());
//                        context.startActivity(intent);
//                        return;
//                    }
//
//                    if (listAset.get(holder.getAdapterPosition()).getAsetFotoQrStatus() != null && hak_akses_id == 7){
//
//                        Intent intent = new Intent(context, UpdateFotoQrAsetActivity.class);
//                        intent.putExtra("id",listAset.get(holder.getAdapterPosition()).getAsetId());
//                        context.startActivity(intent);
//                        return;
//                    }
//
//                    Intent intent = new Intent(context, UpdateAsetActivity.class);
//                    intent.putExtra("id",listAset.get(holder.getAdapterPosition()).getAsetId());
//                    context.startActivity(intent);
                    Toast.makeText(context,"Masuk Edit Offline", Toast.LENGTH_SHORT).show();
                }
            });
    }
        @Override
        public int getItemCount() {
            return listAset.size();
        }
        static class AsetViewHolder extends RecyclerView.ViewHolder {
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

            public AsetViewHolder(@NonNull View v) {
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


