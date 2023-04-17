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
import ptpn12.amanat.asem.AsetAddUpdateOfflineActivity;
import ptpn12.amanat.asem.DetailAsetOfflineActivity;
import ptpn12.amanat.asem.LonglistAsetActivity;
import ptpn12.amanat.asem.R;

import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.AsetModel2;
import ptpn12.amanat.asem.api.model.Data;
import ptpn12.amanat.asem.offline.AsetHelper;
import ptpn12.amanat.asem.offline.DatabaseHelper;
import ptpn12.amanat.asem.offline.model.Aset;
import ptpn12.amanat.asem.utils.utils;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsetOfflineAdapter extends RecyclerView.Adapter<AsetOfflineAdapter.AsetViewHolder> {

    SharedPreferences sharedPreferences;
    Context context;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String TAG = "KirimTAG";

    ArrayList<Aset> listAset;
    DatabaseHelper dbAset;
    AsetInterface asetInterface;
    Dialog customDialogFotoBelumLengkap;


    public AsetOfflineAdapter(Context context, ArrayList<Aset> listAset) {
        this.listAset = listAset;
        this.context = context;
        this.dbAset = new DatabaseHelper(context);
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

    Dialog dialog;
    @NonNull
    @Override
    public AsetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        dialog =new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        View view = LayoutInflater.from(context).inflate(R.layout.ly_longlist_aset, viewGroup, false);
        return new AsetViewHolder(view);
    }
        @Override
        public void onBindViewHolder(@NonNull AsetViewHolder holder, int position) {

            sharedPreferences = context.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
            asetInterface = AsemApp.getApiClient().create(AsetInterface.class);
            final Aset aset = listAset.get(position);

            holder.tvTglInput.setText(aset.getTglInput().split(" ")[0]);
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
                            deleteFotoOffline(aset);
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
//                    Toast.makeText(context,"Masuk Detail Offline",Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AsetAddUpdateOfflineActivity.class);
                    intent.putExtra("id",listAset.get(holder.getAdapterPosition()).getAsetId());
                    intent.putExtra("aset",listAset.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
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
//                    Toast.makeText(context,"Masuk Edit Offline", Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnKirim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((aset.getAsetJenis().equals("1") || aset.getAsetJenis().equals("3"))
                            && (aset.getFotoAset1() == null ||
                            aset.getFotoAset2() == null || aset.getFotoAset3() == null ||
                            aset.getFotoAset4() == null || aset.getFotoAset5() == null)){
                        initDialogFotoBelomLengkap();
                    } else{
                    showDialogKirim(aset);
                    }
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

    public void deleteFotoOffline(Aset aset) {
        if (aset.getFotoAset1() != null){
            File img1 = new File("file://"+aset.getFotoAset1());
            img1.delete();
        }

        if (aset.getFotoAset2() != null){
            File img2 = new File("file://"+aset.getFotoAset2());
            img2.delete();
        }

        if (aset.getFotoAset3() != null) {
            File img3 = new File("file://"+aset.getFotoAset3());
            img3.delete();
        }
        if (aset.getFotoAset4() != null) {
            File img4 = new File("file://"+aset.getFotoAset4());
            img4.delete();
        }
        if (aset.getFotoAset5() != null) {
            File img5 = new File("file://"+aset.getFotoAset5());
            img5.delete();
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
    void showDialogKirim(Aset aset) {
        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);

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
            kirimData(aset);
        });
    }

    void kirimData(Aset aset) {
        dialog.show();
        MultipartBody.Part img1Part = null, img2Part = null, img3Part = null, img4Part = null,img5Part = null, partBaFile = null, partBASTFile = null;
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

//        if (aset.getAsetJenis().equals("1")){
//            RequestBody requestPopulasiTotalSaatIni = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble(aset.getPop_total_ini())));
//            RequestBody requestPopulasiTotalStandar = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble(aset.getPop_total_std())));
//            RequestBody requestPopulasiHektarSaatIni = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble(aset.getPop_hektar_ini())));
//            RequestBody requestPopulasiHektarStandar = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble(aset.getPop_hektar_std())));
//            builder.addPart(MultipartBody.Part.createFormData("pop_pohon_saat_ini", null, requestPopulasiTotalSaatIni));
//            builder.addPart(MultipartBody.Part.createFormData("pop_standar", null, requestPopulasiTotalStandar));
//            builder.addPart(MultipartBody.Part.createFormData("pop_per_ha", null, requestPopulasiHektarSaatIni));
//            builder.addPart(MultipartBody.Part.createFormData("presentase_pop_per_ha", null, requestPopulasiHektarStandar));
//
//        }
        //multipart pohon tanaman
        if ((aset.getAsetJenis().equals("1") && !aset.getAsetKode().equals("22")) || aset.getAsetJenis().equals("3")) {
            RequestBody requestPopulasiPohonSaatIni = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble(aset.getPop_total_ini())));
            RequestBody requestPopulasiStandar = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble(aset.getPop_total_std())));
            builder.addPart(MultipartBody.Part.createFormData("pop_pohon_saat_ini", null, requestPopulasiPohonSaatIni));
            builder.addPart(MultipartBody.Part.createFormData("pop_standar", null, requestPopulasiStandar));

            if (!aset.getPop_per_ha().equals("")) {

                RequestBody requestPopulasiPerHA = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getPop_per_ha()));
                RequestBody requestPresentasePopulasiPerHA = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getPresentase_pop_per_ha()));
                builder.addPart(MultipartBody.Part.createFormData("pop_per_ha", null, requestPopulasiPerHA));
                builder.addPart(MultipartBody.Part.createFormData("presentase_pop_per_ha", null, requestPresentasePopulasiPerHA));
            }

        }

        if (aset.getAsetJenis().equals("1") || aset.getAsetJenis().equals("3")){
            RequestBody requestTahunTanam = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getTahun_tanam()));
            builder.addPart(MultipartBody.Part.createFormData("tahun_tanam", null, requestTahunTanam));

        }

        if ((aset.getAsetJenis().equals("1") && !aset.getAsetKode().equals("ZC06/S001/Tebu")) || aset.getAsetJenis().equals("3") ){
            RequestBody requestSistemTanam = RequestBody.create(MediaType.parse("text/plain"), aset.getSistem_tanam());
            builder.addPart(MultipartBody.Part.createFormData("sistem_tanam", null, requestSistemTanam));

        }else if (aset.getAsetKode().equals("ZC06/S001/Tebu")){
            RequestBody requestTanamMono = RequestBody.create(MediaType.parse("text/plain"), "Mono");
            builder.addPart(MultipartBody.Part.createFormData("sistem_tanam",null,requestTanamMono));
        }

        if (aset.getAsetJenis().equals("2")) {
            RequestBody requestPersenKondisi = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getPersenKondisi()));
            builder.addPart(MultipartBody.Part.createFormData("persen_kondisi", null, requestPersenKondisi));
        }

//        if (aset.getAsetJenis().equals("3")) {
//            RequestBody requestJumlahPohon = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getJumlahPohon()));
//            builder.addPart(MultipartBody.Part.createFormData("jumlah_pohon", null, requestJumlahPohon));
//        }

        if (subUnit == 2) {
            builder.addPart(MultipartBody.Part.createFormData("afdeling_id", null, requestAfdeling));
        }

        if (aset.getAsetJenis().equals("2")) {
            RequestBody requestPersenKondisi = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getPersenKondisi()));
            builder.addPart(MultipartBody.Part.createFormData("persen_kondisi", null, requestPersenKondisi));

            if (aset.getAlat_pengangkutan() != null){
                RequestBody requestAlatAngkut = RequestBody.create(MediaType.parse("text/plain"), aset.getAlat_pengangkutan());
                builder.addPart(MultipartBody.Part.createFormData("alat_angkut", null, requestAlatAngkut));
            }
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

        if (aset.getFotoAset3() != null) {

            File img = new File(aset.getFotoAset3());
            RequestBody requestGeoTag3 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getGeoTag3()));
            builder.addPart(MultipartBody.Part.createFormData("foto_aset3", img.getName(), RequestBody.create(MediaType.parse("image/*"), img)));
            builder.addPart(MultipartBody.Part.createFormData("geo_tag3", null, requestGeoTag3));
        }

        if (aset.getFotoAset4() != null) {
            File img = new File(aset.getFotoAset4());
            RequestBody requestGeoTag4 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getGeoTag4()));
            builder.addPart(MultipartBody.Part.createFormData("foto_aset4", img.getName(), RequestBody.create(MediaType.parse("image/*"), img)));
            builder.addPart(MultipartBody.Part.createFormData("geo_tag4", null, requestGeoTag4));
        }


        if (aset.getFotoAset5() != null) {
            File img = new File(aset.getFotoAset5());
            RequestBody requestGeoTag5 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getGeoTag5()));
            builder.addPart(MultipartBody.Part.createFormData("foto_aset5", img.getName(), RequestBody.create(MediaType.parse("image/*"), img)));
            builder.addPart(MultipartBody.Part.createFormData("geo_tag5", null, requestGeoTag5));
        }



        if (aset.getSatuan_luas() != null) {
            RequestBody requestSatuan = RequestBody.create(MediaType.parse("text/plain"), aset.getSatuan_luas());
            builder.addPart(MultipartBody.Part.createFormData("satuan_luas", null, requestSatuan));

        }


        if (aset.getBeritaAcara() != null) {
            File bafile_file = new File(aset.getBeritaAcara());
            RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), bafile_file);
            partBaFile = MultipartBody.Part.createFormData("ba_file", bafile_file.getName(), requestBaFile);
            builder.addPart(partBaFile);
        }

        if (aset.getFileBAST() != null) {
            File file_bast = new File(aset.getFileBAST());
            RequestBody requestBASTFile = RequestBody.create(MediaType.parse("multipart/form-file"), file_bast);
            partBASTFile = MultipartBody.Part.createFormData("file_bast", file_bast.getName(), requestBASTFile);
            builder.addPart(partBASTFile);
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
                dialog.dismiss();
                if (!response.isSuccessful() && response.body() == null) {
                    if (response.code() >= 400 && response.code() < 500) {
                        Toast.makeText(context,"Data SAP sudah digunakan\nSilakan diubah!",Toast.LENGTH_LONG).show();
                        return;
                    }


                    Toast.makeText(context,"Tidak ada Koneksi Internet " ,Toast.LENGTH_LONG).show();
                    return;
                }


                dialog.dismiss();
                String user_id = sharedPreferences.getString("user_id", "0");
                Call<AsetModel2> call2 = asetInterface.kirimDataAset(response.body().getData().getAsetId(), Integer.parseInt(user_id));
                call2.enqueue(new Callback<AsetModel2>(){
                    @Override
                    public void onResponse(Call<AsetModel2> call, Response<AsetModel2> response) {
                        if (response.isSuccessful() && response.body() != null){
                            AsetHelper asetHelper = AsetHelper.getInstance(context);
                            asetHelper.open();
                            asetHelper.deleteById(String.valueOf(aset.getAsetId()));
                            asetHelper.close();
                            Toast.makeText(context,"Terkirim",Toast.LENGTH_SHORT).show();
                            return;
                        }else {
//                            Toast.makeText(context.getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(context,"Tidak ada Koneksi Internet",Toast.LENGTH_LONG).show();
                            return;
                        }


                    }

                    @Override
                    public void onFailure(Call<AsetModel2> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(context,"Tidak ada Koneksi Internet",Toast.LENGTH_LONG).show();
                        return;
                    }
                });
                dialog.dismiss();
                notifyDataSetChanged();
                Intent gas = new Intent(context, LonglistAsetActivity.class);
                context.startActivity(gas);
                return;

            }

            @Override
            public void onFailure(Call<AsetModel2> call, Throwable t) {
                Toast.makeText(context, "error " + t.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

    }

}


