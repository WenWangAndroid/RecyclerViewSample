package cn.xhuww.recyclerview.itemanimator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cn.xhuww.recyclerview.R;
import cn.xhuww.recyclerview.adapter.ImageAdapter;

public class ItemAnimatorFragment extends Fragment {
    ImageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_recycle_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_divider_vertical_line_white_8dp));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        LeftInRightOutAnimator itemAnimator = new LeftInRightOutAnimator();
        adapter = new ImageAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.item_animator, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.add_item:
                adapter.addData(R.mipmap.image_page_1);
                break;
            case R.id.insert_item:
                adapter.insertData(R.mipmap.image_page_2, 1);
                break;
            case R.id.remove_item:
                adapter.removeData(0);
                break;
            case R.id.update_item:
                adapter.updateData(R.mipmap.image_page_4, 2);
                break;
        }
        return true;
    }
}
