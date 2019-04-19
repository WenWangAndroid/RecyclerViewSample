package cn.xhuww.recyclerview.tree;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.xhuww.recyclerview.AppConstant;
import cn.xhuww.recyclerview.R;
import cn.xhuww.recyclerview.RecyclerViewActivity;
import cn.xhuww.recyclerview.tree.ContactsTreeRecyclerAdapter;
import cn.xhuww.recyclerview.tree.Node;

public class TreeListShowActivity extends AppCompatActivity {

    ContactsTreeRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_list_show);

        adapter = new ContactsTreeRecyclerAdapter();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        initData();
    }

    private void initData() {
        List<Node> nodes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Node parentNode = new Node();
            parentNode.setName("部门" + i);
            parentNode.setLevel(0);
            List<Node> childNodes = new ArrayList<>();
            for (int j = 1; j <= 5; j++) {
                Node childNode = new Node();
                List<Node> childchildNodes = new ArrayList<>();
                childNode.setName("子部门  " + i);
                childNode.setLevel(1);

                for (int k = 1; k <= 5; k++) {
                    Node childchildNode = new Node();
                    childchildNode.setName("成员  " + i);
                    childchildNode.setLevel(2);
                    childchildNodes.add(childchildNode);
                }

                childNode.setChildren(childchildNodes);
                childNodes.add(childNode);
            }
            parentNode.setChildren(childNodes);
            nodes.add(parentNode);
        }
        adapter.setNodes(nodes);
    }
}
