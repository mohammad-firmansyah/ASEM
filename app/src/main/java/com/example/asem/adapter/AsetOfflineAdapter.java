package com.example.asem.adapter;

import static android.content.Context.MODE_PRIVATE;

import static com.example.asem.utils.utils.CurrencyToNumber;

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

import com.example.asem.AddAsetActivity;
import com.example.asem.AsemApp;
import com.example.asem.AsetAddUpdateOfflineActivity;
import com.example.asem.DetailAsetActivity;
import com.example.asem.DetailAsetOfflineActivity;
import com.example.asem.LonglistAsetActivity;
import com.example.asem.R;
import com.example.asem.UpdateAsetActivity;
import com.example.asem.UpdateFotoQrAsetActivity;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.AsetModel2;
import com.example.asem.api.model.Data;
import com.example.asem.api.model.DeleteModel;
import com.example.asem.db.AsetHelper;
import com.example.asem.db.DatabaseHelper;
import com.example.asem.db.model.Aset;
import com.example.asem.utils.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
            sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
            Aset aset = listAset.get(position);
            holder.tvTglInput.setText(aset.getTglInput());
            holder.tvNoSAP.setText(String.valueOf(aset.getNomorSap()));
            holder.tvAsetJenis.setText(String.valueOf(aset.getAsetJenis()));
            holder.tvAfdeling.setText(String.valueOf(aset.getAfdelingId()));
            holder.tvAsetName.setText(String.valueOf(aset.getAsetName()));
            holder.tvUmurEkonomis.setText(utils.MonthToYear(utils.masaSusutToMonth(Integer.valueOf(aset.getMasaSusut()),aset.getTglOleh())));
            holder.tvNilaiAset.setText(utils.Formatrupiah(Double.parseDouble(String.valueOf(aset.getNilaiOleh()))));
            holder.tvStatusPosisi.setText("operator");
            holder.btnHapus.setVisibility(View.VISIBLE);



            if (listAset.get(position).getNoInv() != null) {
                holder.tvNoinv.setText(String.valueOf(aset.getNoInv()));
            } else {
                holder.tvNoinv.setText("-");
            }


            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog =new Dialog(context);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog.setContentView(R.layout.dialog_delete);
                    dialog.show();
                    Button cancel = dialog.findViewById(R.id.btnTidakKirim);
                    Button ya = dialog.findViewById(R.id.btnYaKirim);

                    cancel.setOnClickListener(v -> {dialog.dismiss();});
                    ya.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AsetHelper asetHelper = AsetHelper.getInstance(context);
                            asetHelper.open();
                            asetHelper.deleteById(String.valueOf(aset.getAsetId()));
                            context.startActivity(new Intent(context,LonglistAsetActivity.class));
                            asetHelper.close();
                            dialog.dismiss();
                            return;
                        }
                    });
                }
            });
            holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailAsetOfflineActivity.class);
                    intent.putExtra("id",listAset.get(holder.getAdapterPosition()).getAsetId());
                    context.startActivity(intent);
                    Toast.makeText(context,"Masuk Detail Offline",Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AsetAddUpdateOfflineActivity.class);
                    intent.putExtra("id",listAset.get(holder.getAdapterPosition()).getAsetId());
                    intent.putExtra("aset",listAset.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                    Toast.makeText(context,"Masuk Detail Offline",Toast.LENGTH_SHORT).show();
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

            holder.btnKirim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        MultipartBody.Part img1Part = null, img2Part = null, img3Part = null, img4Part = null, partBaFile = null;


                        RequestBody requestTipeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getAsetTipe()));
                        RequestBody requestJenisAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getAsetJenis()));
                        RequestBody requestKondisiAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getAsetKondisi()));
                        RequestBody requestKodeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getAsetKode()));
                        RequestBody requestNamaAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getAsetName()));
                        RequestBody requestNomorAsetSAP = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getNomorSap()));

                        RequestBody requestLuasAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getAsetLuas()));
                        RequestBody requestNilaiAsetSAP = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getNilaiOleh()));
                        RequestBody requestTglOleh = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getTglOleh()));
                        RequestBody requestMasaSusut = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getMasaSusut()));
                        RequestBody requestNomorBAST = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getNomorBast()));
                        RequestBody requestNilaiResidu = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getNilaiResidu()));
                        RequestBody requestHGU = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getHgu()));

                        Integer unit = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
                        Integer subUnit = Integer.valueOf(sharedPreferences.getString("sub_unit_id", "0"));
                        Integer afdeling_id = Integer.valueOf(sharedPreferences.getString("afdeling_id", "0"));

                        RequestBody requestSubUnit = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(subUnit));
                            RequestBody requestUnit = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(unit));
                        RequestBody requestAfdeling = RequestBody.create(MediaType.parse("text/plain"), String.valueOf((afdeling_id)));


                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        builder.addPart(MultipartBody.Part.createFormData("aset_name", null, requestNamaAset));
                        builder.addPart(MultipartBody.Part.createFormData("aset_tipe", null, requestTipeAset));
                        builder.addPart(MultipartBody.Part.createFormData("aset_jenis", null, requestJenisAset));
                        builder.addPart(MultipartBody.Part.createFormData("aset_kondisi", null, requestKondisiAset));
                        builder.addPart(MultipartBody.Part.createFormData("aset_kode", null, requestKodeAset));
                        builder.addPart(MultipartBody.Part.createFormData("nomor_sap", null, requestNomorAsetSAP));
                        builder.addPart(MultipartBody.Part.createFormData("hgu", null, requestHGU));

                        builder.addPart(MultipartBody.Part.createFormData("aset_luas", null, requestLuasAset));
                        builder.addPart(MultipartBody.Part.createFormData("tgl_oleh", null, requestTglOleh));
                        builder.addPart(MultipartBody.Part.createFormData("nilai_residu", null, requestNilaiResidu));
                        builder.addPart(MultipartBody.Part.createFormData("nilai_oleh", null, requestNilaiAsetSAP));
                        builder.addPart(MultipartBody.Part.createFormData("masa_susut", null, requestMasaSusut));
                        builder.addPart(MultipartBody.Part.createFormData("aset_sub_unit", null, requestSubUnit));
                        builder.addPart(MultipartBody.Part.createFormData("unit_id", null, requestUnit));
                        builder.addPart(MultipartBody.Part.createFormData("nomor_bast", null, requestNomorBAST));

                        if (subUnit == 2) {
                            builder.addPart(MultipartBody.Part.createFormData("afdeling_id", null, requestAfdeling));
                        }

                        if (Integer.valueOf(aset.getAsetJenis()) == 1 || Integer.valueOf(aset.getAsetJenis()) == 3) {
                            RequestBody requestJumlahPohon = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getJumlahPohon()));
                            builder.addPart(MultipartBody.Part.createFormData("jumlah_pohon", null, requestJumlahPohon));
                        }

                        if (Integer.valueOf(aset.getAsetJenis()) == 2) {
                            RequestBody requestPersenKondisi = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getPersenKondisi()));
                            builder.addPart(MultipartBody.Part.createFormData("persen_kondisi", null, requestPersenKondisi));
                        }

                        if (aset.getFotoAset1() != null) {

                            File img = new File(aset.getFotoAset1());
                            RequestBody requestGeoTag1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getGeoTag1()));
                            builder.addPart(MultipartBody.Part.createFormData("foto_aset1", img.getName(), RequestBody.create(MediaType.parse("image/*"), img)));
                            builder.addPart(MultipartBody.Part.createFormData("geo_tag1", null, requestGeoTag1));
                        }

                        if (aset.getFotoAset2() != null) {

                            File img = new File(aset.getFotoAset2());
                            RequestBody requestGeoTag2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getGeoTag2()));
                            builder.addPart(MultipartBody.Part.createFormData("foto_aset2", img.getName(), RequestBody.create(MediaType.parse("image/*"), img)));
                            builder.addPart(MultipartBody.Part.createFormData("geo_tag2", null, requestGeoTag2));
                        }

                        if (aset.getGeoTag3() != null) {

                            File img = new File(aset.getFotoAset3());
                            RequestBody requestGeoTag3 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getGeoTag3()));
                            builder.addPart(MultipartBody.Part.createFormData("foto_aset3", img.getName(), RequestBody.create(MediaType.parse("image/*"), img)));
                            builder.addPart(MultipartBody.Part.createFormData("geo_tag3", null, requestGeoTag3));
                        }

                        if (aset.getGeoTag4() != null) {
                            File img = new File(aset.getFotoAset4());
                            RequestBody requestGeoTag4 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getGeoTag4()));
                            builder.addPart(MultipartBody.Part.createFormData("foto_aset4", img.getName(), RequestBody.create(MediaType.parse("image/*"), img)));
                            builder.addPart(MultipartBody.Part.createFormData("geo_tag4", null, requestGeoTag4));
                        }


                        if (aset.getBeritaAcara() != null) {
                            File bafile_file = new File(aset.getBeritaAcara());
                            RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), bafile_file);
                            partBaFile = MultipartBody.Part.createFormData("ba_file", bafile_file.getName(), requestBaFile);
                            builder.addPart(partBaFile);
                        }

                        if (aset.getKeterangan() != null) {
                            RequestBody requestKeterangan = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getKeterangan()));
                            builder.addPart(MultipartBody.Part.createFormData("keterangan", null, requestKeterangan));
                        }


                        MultipartBody multipartBody = builder
                                .build();
                        String contentType = "multipart/form-data; charset=utf-8; boundary=" + multipartBody.boundary();


                        Call<AsetModel2> call = asetInterface.addAset(contentType, multipartBody);


                        call.enqueue(new Callback<AsetModel2>() {

                            @Override
                            public void onResponse(Call<AsetModel2> call, Response<AsetModel2> response) {
                                if (!response.isSuccessful() && response.body() == null) {
                                    if (response.code() == 401) {
//                                        dialog.dismiss();
//                                        customDialogAddAset.dismiss();
//                                        inpNoSAP.setError("Nomor SAP sudah ada");
//                                        inpNoSAP.requestFocus();
                                        Toast.makeText(context,"data sap sudah ada tolong diubah",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    Toast.makeText(context,"error :" + response.message() + String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                                    return;
                                }


                                String user_id = sharedPreferences.getString("user_id", "-");
                                showDialogKirim("Yakin Kirim Data?",
                                        asetInterface.kirimDataAset(response.body().getData().getAsetId(), Integer.parseInt(user_id))
                                );

                                return;

                            }

                            @Override
                            public void onFailure(Call<AsetModel2> call, Throwable t) {
                                Toast.makeText(context, "error " + t.getMessage(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        });

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
                        context.startActivity(new Intent(context, LonglistAsetActivity.class));
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


}


