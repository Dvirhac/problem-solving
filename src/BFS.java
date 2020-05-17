
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {
    @Override
    public void search(Node start, Node goal) {
        long current_time = System.currentTimeMillis();
        int sum = 1;
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> closed_list = new HashSet<>(),open_list = new HashSet<>();
        queue.add(start);
        open_list.add(start);
        Operation operation = new Operation();
        while (!queue.isEmpty()){
            Node node = queue.poll();
            open_list.remove(node);
            closed_list.add(node);
            operation.set_node(node);
            for (Node child : operation.getChildren()){
                sum++;
                if (!closed_list.contains(child) && !open_list.contains(child)){
                    if (child.equals(goal)){
                        long end_time = System.currentTimeMillis();
                        double time = end_time-current_time;
                        System.out.println(time/1000);
                        Printer.print(goal);
                        System.out.println(sum);
                        for (Node open : open_list){
                            System.out.println(open.toString());
                        }
                        return;
                    }

                    queue.add(child);
                    open_list.add(child);
                }
            }
        }
        Printer.print("no solution");
    }

}