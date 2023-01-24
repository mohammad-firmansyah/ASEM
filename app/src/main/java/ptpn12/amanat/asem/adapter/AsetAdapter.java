package ptpn12.amanat.asem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ptpn12.amanat.asem.DetailAsetActivity;
import ptpn12.amanat.asem.LonglistAsetActivity;

import ptpn12.amanat.asem.R;
import ptpn12.amanat.asem.UpdateAsetActivity;
import ptpn12.amanat.asem.api.model.Data;

public class AsetAdapter extends RecyclerView.Adapter<AsetAdapter.ViewHolder> {


    Data[] myAsetData;
    Context context;

    public AsetAdapter(Data[] data, LonglistAsetActivity longlistAsetActivity){
        this.myAsetData =data;
        this.context = longlistAsetActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ly_longlist_aset,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Data myPostData2 = myAsetData[position];
        holder.tvTglInput.setText(myPostData2.getTglInput());
        holder.tvNoSAP.setText(String.valueOf(String.valueOf(myPostData2.getNomorSap())));
        holder.tvAsetJenis.setText(String.valueOf(myPostData2.getAsetJenis()));
        holder.tvAfdeling.setText(String.valueOf(myPostData2.getAfdelingId()));
        holder.tvAsetName.setText(String.valueOf(myPostData2.getAsetName()));
        holder.tvNilaiAset.setText(String.valueOf(myPostData2.getNilaiOleh()));
        holder.tvUmurEkonomis.setText(String.valueOf(myPostData2.getUmurEkonomisInMonth()));
        holder.tvNoinv.setText(String.valueOf(myPostData2.getNoInv()));

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
    }

    @Override
    public int getItemCount() {
        if (myAsetData != null)
            return myAsetData.length;
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



        }
    }


}

