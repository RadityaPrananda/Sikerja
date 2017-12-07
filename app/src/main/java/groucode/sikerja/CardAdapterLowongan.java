package groucode.sikerja;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapterLowongan extends RecyclerView.Adapter<CardAdapterLowongan.ViewHolder> {

    List<ListItemLowongan> items;
    Context context;

    public CardAdapterLowongan(Context context, String[] bataswaktu, String[] namaperusahaan, String[] jabatan, String[] lokasi){
        super();
        this.context = context;
        items = new ArrayList<ListItemLowongan>();
        for (int i = 0; i<bataswaktu.length; i++){
            ListItemLowongan item = new ListItemLowongan();
            item.setBataswaktu(bataswaktu[i]);
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
        holder.textViewNamaPerusahaan.setText(list.getNamaperusahaan());
        holder.textViewJabatan.setText(list.getJabatan());
        holder.textViewLokasi.setText(list.getLokasi());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(context, DetailBerkasPerkaraActivity.class);
//                intent.putExtra("noreg_berkas",list.getNo_berkas());
//                v.getContext().startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewBatasWaktu;
        public TextView textViewNamaPerusahaan;
        public TextView textViewJabatan;
        public TextView textViewLokasi;

        public ViewHolder(View itemView){
            super(itemView);
            textViewBatasWaktu = (TextView) itemView.findViewById(R.id.batas_waktu);
            textViewNamaPerusahaan = (TextView) itemView.findViewById(R.id.nama_perusahaan);
            textViewJabatan = (TextView) itemView.findViewById(R.id.jabatan);
            textViewLokasi = (TextView) itemView.findViewById(R.id.lokasi);
        }

    }
}
