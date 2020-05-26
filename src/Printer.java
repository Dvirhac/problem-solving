
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Printer {

    private boolean _with_open;
    private boolean _with_time;
    private PrintWriter out;

    public void print(Node goal, long begin, long end, int sum, HashSet<Node> nodes) {
        String path = getPath(goal);
        int cost = getCost(goal);

        try {
            out = new PrintWriter("output.txt");
            StringBuilder ans = new StringBuilder();
            ans.append(path).append("\n")
                    .append("Num: ").append(sum).append("\n")
                    .append("Cost: ").append(cost).append("\n");

            if (_with_time){
                double time = (end-begin);
                double total_time = time/1000;
                ans.append(total_time).append("\n");
            }

            if (_with_open){
                if (nodes!=null){
                    for (Node node : nodes){
                        ans.append(node.toString()).append("\n");
                    }
                }
            }
            System.out.println(ans.toString());
            out.println(ans.toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private int getCost(Node goal) {
        int cost = 0;
        Node runner = goal;
        while(runner.get_parent() != null){
            cost = cost + runner.get_cost();
            runner = runner.get_parent();
        }
        return cost;
    }

    private String getPath(Node goal) {
        StringBuilder path = new StringBuilder();
        Node runner = goal;
        while(runner.get_parent() != null){
            path.append(runner.get_path());
            runner = runner.get_parent();
        }
        StringBuilder ans = new StringBuilder();
        String [] moves= path.toString().split("-");
        for (int i = moves.length - 1 ; i >=1 ; --i){
            ans.append(moves[i]).append("-");
        }
        ans.append(moves[0]);
        return ans.toString();

    }



    public void fail(int sum, long begin, long end , HashSet<Node> nodes) {
        PrintWriter out;
        try {
            out = new PrintWriter("output.txt");
            StringBuilder ans = new StringBuilder();
            ans.append("no solution").append("\n")
                    .append("Num: ").append(sum).append("\n");
            if (_with_time){
                double time = (end-begin);
                double total_time = time/1000;
                ans.append(total_time).append("\n");
            }
            out.println(ans.toString());
            System.out.println(ans.toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static void print(String st){
        System.out.println(st);
    }
    public boolean is_with_open() { return _with_open; }
    public void set_with_open(boolean _with_open) { this._with_open = _with_open; }
    public boolean is_with_time() { return _with_time; }
    public void set_with_time(boolean _with_time) { this._with_time = _with_time; }


}
