package cn.xhuww.recyclerview.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.xhuww.recyclerview.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Integer> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_item_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.showImage(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Integer> items) {
        if (items == null) {
            this.items.clear();
        } else {
            this.items = items;
        }
        notifyDataSetChanged();
    }


    public void addData(@DrawableRes int res) {
        items.add(res);
        notifyItemInserted(items.size() - 1);
    }

    public void insertData(@DrawableRes int res, int position) {
        if (outOfSize(position)) return;
        items.add(position, res);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        if (outOfSize(position)) return;
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void updateData(@DrawableRes int res, int position) {
        if (outOfSize(position)) return;
        items.set(position, res);
        notifyItemChanged(position);
    }

    private boolean outOfSize(int position) {
        return position < 0 || position >= items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
        }

        void showImage(@DrawableRes int res) {
            imageView.setImageResource(res);
        }
    }
}