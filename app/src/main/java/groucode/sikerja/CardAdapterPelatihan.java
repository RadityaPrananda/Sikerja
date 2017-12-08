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

import groucode.sikerja.activity.DetailPelatihanActivity;

public class CardAdapterPelatihan extends RecyclerView.Adapter<CardAdapterPelatihan.ViewHolder> {
    List<ListItemPelatihan> items;
    Context context;

    public CardAdapterPelatihan(Context context, String[] id, String[] namapelatihan, String[] logopelatihan, String[] penyelenggara, String[] waktu, String[] tempat, String[] kuota){
        super();
        this.context = context;
        items = new ArrayList<ListItemPelatihan>();
        for (int i = 0; i<id.length; i++){
            ListItemPelatihan item = new ListItemPelatihan();
            item.setId(id[i]);
            item.setNamapelatihan(namapelatihan[i]);
            item.setLogopelatihan(logopelatihan[i]);
            item.setPenyelenggara(penyelenggara[i]);
            item.setWaktu(waktu[i]);
            item.setTempat(tempat[i]);
            item.setKuota(kuota[i]);
            items.add(item);
        }
    }

    public CardAdapterPelatihan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cardviewpelatihan, parent, false);
        CardAdapterPelatihan.ViewHolder viewHolder = new CardAdapterPelatihan.ViewHolder(v);

        return viewHolder;
    }

    public void onBindViewHolder(CardAdapterPelatihan.ViewHolder holder, int position){

        final ListItemPelatihan list = items.get(position);
        holder.textViewNamaPelatihan.setText(list.getNamapelatihan());
        Glide.with(context).load(list.getLogopelatihan())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ImgViewLogoPelatihan);
        holder.textViewPenyelenggara.setText(list.getPenyelenggara());
        holder.textViewWaktu.setText(list.getWaktu());
        holder.textViewTempat.setText(list.getTempat());
        holder.textViewKuota.setText(list.getKuota());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, DetailPelatihanActivity.class);
                intent.putExtra("id",list.getId());
                v.getContext().startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewNamaPelatihan;
        public ImageView ImgViewLogoPelatihan;
        public TextView textViewPenyelenggara;
        public TextView textViewWaktu;
        public TextView textViewTempat;
        public TextView textViewKuota;


        public ViewHolder(View itemView){
            super(itemView);
            textViewNamaPelatihan = (TextView) itemView.findViewById(R.id.nama_pelatihan);
            ImgViewLogoPelatihan = (ImageView) itemView.findViewById(R.id.logopelatihan);
            textViewPenyelenggara = (TextView) itemView.findViewById(R.id.penyelenggara);
            textViewWaktu = (TextView) itemView.findViewById(R.id.waktu);
            textViewTempat = (TextView) itemView.findViewById(R.id.tempat);
            textViewKuota = (TextView) itemView.findViewById(R.id.kuota);
        }

    }

}
