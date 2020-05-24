import java.util.Arrays;

public class Node implements Comparable{
    private boolean out = false;
    private Square [][] _board;
    private String _path;
    private final int _iter;
    private final int _came_from;
    private final int _cost;
    private final int _g;
    private final Node _parent;
    private int _h = 1;


    public Node(Square[][] locations) {
        this._board = locations;
        this._cost = 0;
        this._g = 0;
        this._iter = 0;
        this._came_from = -1;
        this._parent = null;
    }

    public Node(Node o, int _came_from, int _cost){

        this._board = copy(o._board);
        this._came_from = _came_from;
        this._iter = o._iter + 1;
        this._parent = o;
        this._cost = _cost;
        this._g = o._g + this._cost;
    }

    private Square[][] copy(Square[][] board) {
        Square[][] new_board= new Square[board.length][board[0].length];
        for (int i = 0 ; i < board.length; ++i){
            for (int j = 0 ; j < board[i].length ; ++j){
                Square slot = new Square(board[i][j]);
                new_board[i][j] = slot;
            }
        }
        return new_board;

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
    public int compareTo(Object o) {
        Node other = (Node) o;
        int f = Integer.compare(_g + _h, other._g + other._h);
        if (f != 0) return f;
        int iter = Integer.compare(_iter, other._iter);
        if ( iter != 0) return iter;
        return Integer.compare(_came_from, other._came_from);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_board);
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



    //------------------getters ans setters------------------------
    private int h(){ return 0; }
    public void set_h(int _h) { this._h = _h; }
    public int get_f(){ return _h + _g; }
    private void set_h () { this._h = 1; }
    private int get_g(){ return _g; }
    public int get_came_from() { return _came_from; }
    public int get_h() { return _h; }
    public boolean isOut() { return out; }
    public void setOut(boolean out) { this.out = out; }
    public void setH(int h) { this._h = h; }
    public Square[][] get_board() { return _board; }
    public Node get_parent() { return _parent; }
    public int get_cost() { return _cost; }
    public String get_path() { return _path; }
    public void set_path(String _path) { this._path = _path; }


}
