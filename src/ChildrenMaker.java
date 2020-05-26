import java.util.ArrayList;

public class ChildrenMaker {
    private Node _node;
    private int _move_to = 0;
    int[] index;

    protected Node getChild(){
        if (_move_to > 3)
            return null;
        Node child = null;
        if (index != null)
        {
            if (_node.get_came_from() != 2 && _move_to == 0) // can go left
                child = swap(index, new int[]{index[0], index[1] + 1});
            else if (_node.get_came_from() != 3 && _move_to == 1) // can go up
                child = swap(index, new int[]{index[0] + 1, index[1]});
            else if (_node.get_came_from() != 0 && _move_to == 2) // can go right
                child = swap(index, new int[]{index[0], index[1] - 1});
            else if (_node.get_came_from() != 1 && _move_to == 3) // can go down
                child = swap(index, new int[]{index[0] - 1, index[1]});


            if (child != null && _move_to == 0)
                child.set_path( _node.get_board()[index[0]][index[1] + 1].get_val() + "L"+"-");

            else if (child != null && _move_to == 1)
                child.set_path( _node.get_board()[index[0] + 1][index[1]].get_val() + "U"+"-");

            else if (child != null && _move_to == 2)
                child.set_path(_node.get_board()[index[0]][index[1] - 1].get_val() + "R"+"-");

            else if (child != null && _move_to == 3)
                child.set_path(_node.get_board()[index[0] - 1][index[1]].get_val() + "D"+"-");
        }
        _move_to++;
        if (child == null){
            return getChild();
        }
        return child;
    }

    private int[] find(){
        for (int i = 0 ; i < _node.get_board().length; ++i){
            for (int j = 0; j < _node.get_board()[i].length ; ++j){
                if (_node.get_board()[i][j].get_val() == -1){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private Node swap(int [] from, int[] to){
        if (to[0] >= 0 && to[0] < _node.get_board().length && to[1] >= 0 && to[1] < _node.get_board()[0].length){
            Node child = new Node(_node, _move_to, _node.get_board()[to[0]][to[1]].get_cost());
            Square tmp = new Square(child.get_board()[from[0]] [from[1]]);
            child.get_board()[from[0]][from[1]] = new Square(child.get_board()[to[0]][to[1]]);
            child.get_board()[to[0]][to[1]] = tmp;
            return child;
        }
        return null;
    }

    public void set_node(Node _node) {
        this._move_to = 0;
        this._node = _node;
        index = find();
    }

    public ArrayList<Node> getChildren() {
        ArrayList<Node> children = new ArrayList<>();
        for (Node child = getChild(); child != null ; child = getChild()){
            children.add(child);
        }
        return children;
    }
}
