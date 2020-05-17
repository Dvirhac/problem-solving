
import java.util.ArrayList;

public class Operation {
    private Node _node;


    public  ArrayList<Node> getChildren(){

        ArrayList<Node> children = new ArrayList<>();
        int [] index = find_();
        Node right = null, left = null, down = null , up = null;

        if (index != null)
        {
            if (!_node.is_came_from_left())
                 right = swap(index, new int[]{index[0], index[1] - 1});
            if (!_node.is_came_from_right())
                 left = swap(index, new int[]{index[0], index[1] + 1});
            if (!_node.is_came_from_up())
                 down = swap(index, new int[]{index[0] - 1, index[1]});
            if (!_node.is_came_from_down())
                 up = swap(index, new int[]{index[0] + 1, index[1]});

            if (left != null){
                left.set_cost(_node.get_cost() + _node.get_board()[index[0]][index[1] + 1].get_cost());
                left.set_way_of_getting(_node.get_way_of_getting() + _node.get_board()[index[0]][index[1] + 1].get_val() + "L"+"-");
                left.set_came_from_left(true);
                children.add(left);
            }

            if (up!=null){
                up.set_cost(_node.get_cost() + _node.get_board()[index[0] + 1][index[1]].get_cost());
                up.set_way_of_getting(_node.get_way_of_getting() + _node.get_board()[index[0] + 1][index[1]].get_val() + "U"+"-");
                up.set_came_from_up(true);
                children.add(up);
            }

            if (right!=null){
                right.set_cost(_node.get_cost() + _node.get_board()[index[0]][index[1] - 1].get_cost());
                right.set_way_of_getting(_node.get_way_of_getting() + _node.get_board()[index[0]][index[1] - 1].get_val() + "R"+"-");
                right.set_came_from_right(true);
                children.add(right);
            }

            if (down!=null){
                down.set_cost(_node.get_cost() + _node.get_board()[index[0] - 1][index[1]].get_cost());
                down.set_way_of_getting(_node.get_way_of_getting() + _node.get_board()[index[0] - 1][index[1]].get_val() + "D"+"-");
                down.set_came_from_down(true);
                children.add(down);
            }

        }
        return children;
    }

    private  int[] find_(){
        for (int i = 0 ; i < _node.get_board().length; ++i){
            for (int j = 0; j < _node.get_board()[i].length ; ++j){
                if (_node.get_board()[i][j].get_val().equals("_")){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    Node swap(int [] from, int[] to){
        if (to[0] >= 0 && to[0] < _node.get_board().length && to[1] >= 0 && to[1] < _node.get_board()[0].length){
            Node node1 = new Node(_node);
            node1.set_parent(node1);
            Slot tmp = new Slot(node1.get_board()[from[0]] [from[1]]);
            node1.get_board()[from[0]][from[1]] = new Slot(node1.get_board()[to[0]][to[1]]);
            node1.get_board()[to[0]][to[1]] = tmp;
            return node1;
        }

        return null;
    }

    public Node get_node() {
        return _node;
    }

    public void set_node(Node _node) {
        this._node = _node;
    }
}
