import java.util.*;

public class Algorithm {
    private int sum = 1;
    private Node _start;
    private Node _goal;
    private boolean _with_open;
    private boolean _with_time;


    public void search(Node _start, Node _goal, String algo, boolean _with_open, boolean _with_time) {
        this._start = _start;
        this._goal = _goal;
        this._with_open = _with_open;
        this._with_time = _with_time;

        switch (algo) {
            case "BFS":
                BFS();
                break;
            case "DFID":
                DFID();
                break;
            case "IDA_STAR":
                IDA_STAR();
                break;
            case "A_STAR":
                A_STAR();
                break;

        }
    }


    private void BFS() {
        long current_time = System.currentTimeMillis();
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> closed_list = new HashSet<>(), open_list = new HashSet<>();
        queue.add(_start);
        open_list.add(_start);
        ChildrenMaker childrenMaker = new ChildrenMaker();
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            open_list.remove(node);
            closed_list.add(node);
            childrenMaker.set_node(node);
            for (Node child = childrenMaker.getChild(); child != null; child = childrenMaker.getChild()) {
                sum++;
                if (!closed_list.contains(child) && !open_list.contains(child)) {
                    if (child.equals(_goal)) {
                        long end_time = System.currentTimeMillis();
                        double time = end_time - current_time;
                        System.out.println(time / 1000);
                        Printer.print(child);
                        System.out.println(sum);
                        for (Node open : open_list) {
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

    private CFS DFID() {
        long start_time = System.currentTimeMillis();
        for (int limit = 1; true; ++limit) {
            HashSet<Node> path = new HashSet<>();
            CFS result = limitedDFS(_start, _goal, limit, path);
            if (result != CFS.CUTOFF) {
                long end_time = System.currentTimeMillis();
                double total_time = end_time - start_time;
                System.out.println(total_time / 1000);
                return result;
            }
        }
    }

    private CFS limitedDFS(Node node, Node goal, int limit, HashSet<Node> path) {
        if (goal.equals(node))
            return path(node);
        else if (limit == 0)
            return CFS.CUTOFF;
        else {
            path.add(node);
            boolean is_cutoff = false;
            ChildrenMaker childrenMaker = new ChildrenMaker();
            childrenMaker.set_node(node);
            for (Node child = childrenMaker.getChild(); child != null; child = childrenMaker.getChild()) {
                sum++;
                if (path.contains(child))
                    continue;
                CFS result = limitedDFS(child, goal, limit - 1, path);
                if (result == CFS.CUTOFF)
                    is_cutoff = true;
                else if (result != CFS.FAIL) {
                    return CFS.SUCCESS;
                }
            }

            path.remove(node);
            if (is_cutoff) {
                return CFS.CUTOFF;
            } else {
                return CFS.FAIL;
            }
        }
    }


    private CFS path(Node goal) {
        System.out.println(sum);
        Printer.print(goal);
        return CFS.SUCCESS;
    }

    private void IDA_STAR() {
        Stack<Node> stack = new Stack<>();
        HashSet<Node> path = new HashSet<>();
        int t = _start.get_h();
        while (t != Integer.MAX_VALUE) {
            int minF = Integer.MAX_VALUE;
            stack.add(_start); //L
            path.add(_start);// H
            ChildrenMaker childrenMaker = new ChildrenMaker();
            while (!stack.empty()) {
                Node current = stack.pop();
                if (current.isOut()) {
                    path.remove(current);
                } else {
                    current.setOut(true);
                    stack.add(current);
                    childrenMaker.set_node(current);
                    for (Node child = childrenMaker.getChild(); child != null; child = childrenMaker.getChild()) {
                        if (child.get_f() > t) { //TOTO:: set F and G
                            minF = Math.min(minF, child.get_f());
                            continue;
                        }
                        if (path.contains(child)) {
                            Node twin = find(child,path);
                            assert twin != null;
                            if (twin.isOut())
                                continue;

                            else {
                                if (twin.get_f() > child.get_f()){
                                    path.remove(twin);
                                    stack.remove(twin);
                                }

                                else {
                                    continue;
                                }
                            }
                        }

                        if (child.equals(_goal)){
                            Printer.print(child);
                            return;
                        }
                        stack.add(child);
                        path.add(child);

                    }
                }
            }

            t = minF;
        }
        Printer.print("no solution");
    }






    private void A_STAR(){
        PriorityQueue<Node> queue = new PriorityQueue<>();
        HashSet<Node> open_list = new HashSet<>();
        HashSet<Node> closed_list = new HashSet<>();
        queue.add(_start);
        open_list.add(_start);
        ChildrenMaker childrenMaker = new ChildrenMaker();
        while (!queue.isEmpty()){
            Node current = queue.poll();
            open_list.remove(current);
            if (_goal.equals(current)){
                System.out.println(sum);
                Printer.print(current);
                return;
            }
            closed_list.add(current);
            childrenMaker.set_node(current);
            for (Node child = childrenMaker.getChild(); child != null ; child = childrenMaker.getChild()){
                sum++;
                if (!closed_list.contains(child) && !open_list.contains(child)){
                    open_list.add(child);
                    queue.add(child);
                }
                else if (open_list.contains(child)){
                    Node twin = find(child, open_list);
                    assert twin != null;
                    if (twin.get_f() > child.get_f()){
                        open_list.remove(twin);
                        queue.remove(twin);
                        open_list.add(child);
                        queue.add(child);
                    }
                }
            }
        }

        Printer.print("no solution");

    }


    private Node find(Node child, HashSet<Node> path) {
        for (Node runner : path) {
            if (runner.equals(child))
                return runner;
        }
        return null;
    }
}




