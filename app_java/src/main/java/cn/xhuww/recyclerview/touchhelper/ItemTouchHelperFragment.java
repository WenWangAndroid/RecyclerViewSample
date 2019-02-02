package cn.xhuww.recyclerview.touchhelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import cn.xhuww.recyclerview.R;
import cn.xhuww.recyclerview.adapter.ImageAdapter;

public class ItemTouchHelperFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_recycle_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        ImageAdapter adapter = new ImageAdapter();
        layoutManager = new GridLayoutManager(getContext(), 3);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        List<Integer> list = Arrays.asList(
                R.mipmap.image_page_1, R.mipmap.image_page_2, R.mipmap.image_page_3,
                R.mipmap.image_page_4, R.mipmap.image_page_1, R.mipmap.image_page_2,
                R.mipmap.image_page_3, R.mipmap.image_page_4, R.mipmap.image_page_1
        );
        adapter.setItems(list);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.item_touch_helper, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.linear:
                layoutManager = new LinearLayoutManager(getContext());
                break;
            case R.id.grid:
                layoutManager = new GridLayoutManager(getContext(), 3);
                break;
        }
        recyclerView.setLayoutManager(layoutManager);
        return true;
    }
}
