import java.util.Arrays;

public class Node {
    private boolean _came_from_left = false;
    private boolean _came_from_right = false;
    private boolean _came_from_up = false;
    private boolean _came_from_down = false;
    private Slot [][] _board;
    private Node _parent;
    private int _cost;
    private String path;


    public Node(Slot[][] locations) {
        this._board = locations;
        _cost = 0;
    }

    public Node(Node o){
        Slot[][] slots = new Slot[o._board.length][o._board[0].length];
        for (int i = 0 ; i < o._board.length; ++i){
            for (int j = 0 ; j < o._board[i].length ; ++j){
                Slot slot = new Slot(o._board[i][j]);
                slots[i][j] = slot;
            }
        }

        this._board = slots;
        this._parent = o._parent;
        this._cost = o._cost;
        this.path = o.path;
    }



    public Slot[][] get_board() {
        return _board;
    }
    public void set_board(Slot[][] _board) {
        this._board = _board;
    }
    public Node get_parent() {
        return _parent;
    }
    public void set_parent(Node _parent) {
        this._parent = _parent;
    }
    public int get_cost() {
        return _cost;
    }
    public void set_cost(int _cost) {
        this._cost = _cost;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public String toString() {
        String [][] currentMode = new String [_board.length][_board[0].length];
        for (int i = 0 ; i < _board.length; ++i){
            for (int j = 0 ; j < _board[i].length; ++j){
                currentMode[i][j] = _board[i][j].get_val();
            }
        }
        StringBuilder ans = new StringBuilder();
        for (String [] row : currentMode){
            ans.append(Arrays.toString(row)).append("\n");
        }
        return ans.toString();
    }

    @Override
    public int hashCode() {
           return Arrays.hashCode(_board);
        //return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        for (int i = 0 ; i < _board.length ; ++i){
            boolean equals = Arrays.equals(_board[i], node._board[i]);
            if (!equals)
                return false;
        }
        return true;
    }

    public boolean is_came_from_left() {
        return _came_from_left;
    }

    public void set_came_from_left(boolean _came_from_left) {
        this._came_from_left = _came_from_left;
    }

    public boolean is_came_from_right() {
        return _came_from_right;
    }

    public void set_came_from_right(boolean _came_from_right) {
        this._came_from_right = _came_from_right;
    }

    public boolean is_came_from_up() {
        return _came_from_up;
    }

    public void set_came_from_up(boolean _came_from_up) {
        this._came_from_up = _came_from_up;
    }

    public boolean is_came_from_down() {
        return _came_from_down;
    }

    public void set_came_from_down(boolean _came_from_down) {
        this._came_from_down = _came_from_down;
    }

}
