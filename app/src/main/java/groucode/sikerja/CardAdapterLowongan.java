package groucode.sikerja;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import groucode.sikerja.activity.DetailLowonganActivity;

public class CardAdapterLowongan extends RecyclerView.Adapter<CardAdapterLowongan.ViewHolder> {

    List<ListItemLowongan> items;
    Context context;

    public CardAdapterLowongan(Context context, String[] id, String[] bataswaktu, String[] logoperusahaan,String[] namaperusahaan, String[] jabatan, String[] lokasi){
        super();
        this.context = context;
        items = new ArrayList<ListItemLowongan>();
        for (int i = 0; i<bataswaktu.length; i++){
            ListItemLowongan item = new ListItemLowongan();
            item.setId(id[i]);
            item.setBataswaktu(bataswaktu[i]);
            item.setLogoperusahaan(logoperusahaan[i]);
            item.setNamaperusahaan(namaperusahaan[i]);
            item.setJabatan(jabatan[i]);
            item.setLokasi(lokasi[i]);
            items.add(item);
        }
    }

    public CardAdapterLowongan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cardviewlowongan, parent, false);
        CardAdapterLowongan.ViewHolder viewHolder = new CardAdapterLowongan.ViewHolder(v);

        return viewHolder;
    }

    public void onBindViewHolder(CardAdapterLowongan.ViewHolder holder, int position){

        final ListItemLowongan list = items.get(position);
        holder.textViewBatasWaktu.setText(list.getBataswaktu());
        Glide.with(context).load(list.getLogoperusahaan())
                .crossFade()
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageViewLogoperusahaan);
        holder.textViewNamaPerusahaan.setText(list.getNamaperusahaan());
        holder.textViewJabatan.setText(list.getJabatan());
        holder.textViewLokasi.setText(list.getLokasi());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, DetailLowonganActivity.class);
                intent.putExtra("id",list.getId());
                v.getContext().startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewBatasWaktu;
        public ImageView imageViewLogoperusahaan;
        public TextView textViewNamaPerusahaan;
        public TextView textViewJabatan;
        public TextView textViewLokasi;

        public ViewHolder(View itemView){
            super(itemView);
            textViewBatasWaktu = (TextView) itemView.findViewById(R.id.batas_waktu);
            imageViewLogoperusahaan = (ImageView) itemView.findViewById(R.id.logoperusahaan);
            textViewNamaPerusahaan = (TextView) itemView.findViewById(R.id.nama_perusahaan);
            textViewJabatan = (TextView) itemView.findViewById(R.id.jabatan);
            textViewLokasi = (TextView) itemView.findViewById(R.id.lokasi);
        }

    }
}
