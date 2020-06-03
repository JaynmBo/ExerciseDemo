package com.caobo.exercise_1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.caobo.exercise_1.R;

import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private int[] images = {R.mipmap.item1, R.mipmap.item2, R.mipmap.item3,
            R.mipmap.item4, R.mipmap.item5, R.mipmap.item6, R.mipmap.item7,
            R.mipmap.item8, R.mipmap.item9, R.mipmap.item10,R.mipmap.item1, R.mipmap.item2, R.mipmap.item3,
            R.mipmap.item4, R.mipmap.item5, R.mipmap.item6, R.mipmap.item7,
            R.mipmap.item8, R.mipmap.item9, R.mipmap.item10};

    public OnItemClickListener onItemClickListener;

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
