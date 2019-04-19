package cn.xhuww.recyclerview.tree;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xhuww.recyclerview.R;

public class ContactsTreeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Node> items = new ArrayList<>();// 当前展示
    private List<Node> nodes = new ArrayList<>();// 所有的

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycle_item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Node node = items.get(position);

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = ((ViewHolder) holder);
            viewHolder.nameTextView.setText(node.getName());

            viewHolder.rootView.setPadding(50 * node.getLevel(), 0, 0, 0);

            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandOrCollapse(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            nameTextView = view.findViewById(R.id.name_text_view);
        }
    }

    public void setNodes(List<Node> list) {
        this.nodes = treeToList(list, 0);
        items = filterVisibleNode(nodes, null);
        notifyDataSetChanged();
    }

    public void expandOrCollapse(int position) {
        Node node = items.get(position);

        if (node != null) {
            if (!node.isLeaf()) {
                node.setExpand(!node.isExpand());
                items = filterVisibleNode(nodes, node);
                notifyDataSetChanged();
            }
        }
    }

    public List<Node> treeToList(List<Node> nodes, int defaultExpandLevel) {
        List<Node> result = new ArrayList<>();
        //拿到根节点
        List<Node> rootNodes = getRootNodes(nodes);
        //排序依次添加
        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    private List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot())
                root.add(node);
        }
        return root;
    }

    private void addNode(List<Node> nodes, Node node, int defaultExpandLevel, int currentLevel) {

        nodes.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }

        if (node.isLeaf())
            return;
        List<Node> children = node.getChildren();

        for (Node childNode : children) {
            childNode.setParent(node);
            addNode(nodes, childNode, defaultExpandLevel, currentLevel + 1);
        }
    }

    public List<Node> filterVisibleNode(List<Node> nodes, Node currentNode) {
        List<Node> result = new ArrayList<>();

        for (Node node : nodes) {
            // 如果为跟节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                if (node.getParent() == currentNode) {
                    node.setExpand(false);
                }
                result.add(node);
            }
        }
        return result;
    }
}
