import java.util.ArrayList;
import java.util.Arrays;

public class Problem {
    private String _algo;
    private boolean _time;
    private boolean _open;
    private Node _start;
    private Node _goal;
    private Node test;
    private Node ans;

    public void search(){
        if (this._algo.equals("BFS")){
            BFS bfs = new BFS();
            bfs.search(_start,_goal);
        }
        else if (this._algo.equals("DFID")){

        }

    }



    public void load(String path){
        ArrayList<String> data = Parse.load();
        this._algo = data.get(0);
        _time = data.get(1).contains("with");
        _open = !data.get(2).contains("no");
        String [] size = (data.get(3).split("x"));
        int rows = Integer.parseInt(size[0]);
        int columns = Integer.parseInt(size[1]);
        String [] black = data.get(4).substring(6).replace(" ", "").split(",");
        ArrayList<String> blacks = new ArrayList<>(Arrays.asList(black));
        ArrayList<String> reds =new ArrayList<>(Arrays.asList(data.get(5).substring(4).replace(" ", "").split(",")));
        Slot [][] locations= new Slot[rows][columns];
        for (int i = 6; i < 6 + rows; ++i){
            Slot [] slots = new Slot[columns];
            String [] row = data.get(i).split(",");
            for (int j = 0 ; j < columns ; ++j){
                Slot slot = new Slot(row[j]);
                if (blacks.contains(row[j])){
                    slot.set_color(Color.Black);
                }
                else if (reds.contains(row[j])){
                    slot.set_color(Color.Red);
                    slot.set_cost(30);
                }
                else if (row[j].equals("_")){
                    slot.set_color(Color.Empty);
                }
                else {
                    slot.set_color(Color.Green);
                    slot.set_cost(1);
                }
                slots[j] = slot;
            }
            locations[i - 6] = slots;
        }

        this._start = new Node(locations);
        locations = new Slot[rows][columns];
        this._start.set_parent(null);
        int z = 1;
        for (int i = 0 ; i < rows ; ++i){
            Slot [] slots = new Slot[columns];
            for (int j = 0 ; j < columns ; ++j){
                slots[j] = new Slot("" + z);
                z++;
            }
            locations[i] = slots;
        }
        this._goal = new Node(locations);
        this._goal.get_board()[rows -1 ][columns -1 ].set_val("_");
    }


}
