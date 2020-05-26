
import java.util.*;

public class Algorithm {
    private int sum = 1;
    private Node _start;
    private Node _goal;
    private final Printer printer = new Printer();
    private long begin;
    private long end;

    public void search(Node _start, Node _goal, String algo, boolean _with_open, boolean _with_time) {
        this._start = _start;
        this._goal = _goal;
        this.printer.set_with_open(_with_open);
        this.printer.set_with_time(_with_time);


        switch (algo) {
            case "BFS":
                begin = System.currentTimeMillis();
                BFS();
                break;
            case "DFID":
                begin = System.currentTimeMillis();
                DFID();
                break;
            case "A_STAR":
                begin = System.currentTimeMillis();
                A_STAR();
                break;
            case "IDA_STAR":
                begin = System.currentTimeMillis();
                IDA_STAR();
                break;
            case "DFBnB":
                begin = System.currentTimeMillis();
                DFBnB();
                break;

        }
    }


    private void BFS() {
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
                if (child.get_cost() == -1){
                    end = System.currentTimeMillis();
                    printer.fail(sum,begin,end,open_list);
                    return;
                }
                sum++;
                if (!closed_list.contains(child) && !open_list.contains(child)) {
                    if (child.equals(_goal)) {
                        end = System.currentTimeMillis();
                        printer.print(child, begin,end, sum, open_list );
                        return;
                    }

                    queue.add(child);
                    open_list.add(child);
                }
            }
        }

        end = System.currentTimeMillis();
        printer.fail(sum,begin,end,open_list);
    }

    private CFS DFID() {
        for (int limit = 1; true; ++limit) {
            HashSet<Node> path = new HashSet<>();
            CFS result = limitedDFS(_start, _goal, limit, path);
            if (result != CFS.CUTOFF) {
                if (result != CFS.SUCCESS) {
                    end = System.currentTimeMillis();
                    printer.fail(sum, begin, end, path);
                }
                return result;
            }
        }
    }

    private CFS limitedDFS(Node node, Node goal, int limit, HashSet<Node> path) {
        if (goal.equals(node)){
            end = System.currentTimeMillis();
            printer.print(node, begin, end, sum, path);
            return CFS.SUCCESS;
        }

        else if (limit == 0)
            return CFS.CUTOFF;
        else {
            path.add(node);
            boolean is_cutoff = false;
            ChildrenMaker childrenMaker = new ChildrenMaker();
            childrenMaker.set_node(node);
            for (Node child = childrenMaker.getChild(); child != null; child = childrenMaker.getChild()) {
                if (child.get_cost() == -1){
                    end = System.currentTimeMillis();
                    return CFS.FAIL;
                }
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
                end = System.currentTimeMillis();
                printer.print(current,begin,end,sum,open_list);
                return;
            }
            closed_list.add(current);
            childrenMaker.set_node(current);
            for (Node child = childrenMaker.getChild(); child != null ; child = childrenMaker.getChild()){
                if (child.get_cost() == -1){
                    end = System.currentTimeMillis();
                    printer.fail(sum,begin,end,open_list);
                    return;
                }
                sum++;
                if (!closed_list.contains(child) && !open_list.contains(child)){
                    open_list.add(child);
                    queue.add(child);
                }
                else if (open_list.contains(child)){
                    Node twin = find(child, open_list);
                    assert twin != null;
                    if (twin.get_g() > child.get_g()){
                        open_list.remove(twin);
                        queue.remove(twin);
                        open_list.add(child);
                        queue.add(child);
                    }
                }
            }
        }

        printer.fail(sum,begin,end,open_list);
    }


    private void IDA_STAR() {
        Stack<Node> stack = new Stack<>();
        HashSet<Node> path = new HashSet<>();
        double t = _start.get_h();
        while (t != Integer.MAX_VALUE) {
            _start.setOut(false);
            double minF = Integer.MAX_VALUE;
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
                        if (child.get_cost() == -1){
                            end = System.currentTimeMillis();
                            printer.fail(sum,begin,end,path);
                            return;
                        }
                        sum++;
                        if (child.get_f() > t) {
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
                            end = System.currentTimeMillis();
                            printer.print(child,begin,end,sum,path);
                            return;
                        }
                        stack.add(child);
                        path.add(child);
                    }
                }
            }

            t = minF;
        }

        printer.fail(sum,begin,end,null);
    }



    private void DFBnB(){
        Stack<Node> stack = new Stack<>();
        HashSet<Node> path = new HashSet<>();
        stack.add(_start);
        path.add(_start);
        ArrayList<Node> result = new ArrayList<>();
        ChildrenMaker childrenMaker = new ChildrenMaker();
        int t = Integer.MAX_VALUE;
        while (!stack.isEmpty()){
            Node current = stack.pop();
            if (current.isOut()){
                path.remove(current);
            }
            else {
                current.setOut(true);
                stack.add(current);
                childrenMaker.set_node(current);
                ArrayList<Node> children = childrenMaker.getChildren();
                children.sort(Node::compareTo);
                sum += children.size();
                for (int i = 0; i < children.size() ; ++i) {
                    Node child = children.get(i);
                    if (child.get_cost() == -1){
                        end = System.currentTimeMillis();
                        printer.fail(sum,begin,end,path);
                        return;
                    }
                    if (child.get_f() >= t) {
                        while (i < children.size()) {
                            children.remove(i);
                        }
                    }
                    else if (path.contains(child)) {
                            Node twin = find(child,path);
                        assert twin != null;
                        if (twin.isOut()){
                                children.remove(child);
                            }
                            else {
                                if (twin.get_f() <= child.get_f()){
                                    children.remove(child);
                                }
                                else{
                                    stack.remove(twin);
                                    path.remove(twin);
                                }
                            }
                    }
                    else if (child.equals(_goal)){
                        t = child.get_f();
                        result = path(stack);
                        result.add(child);
                        while (i < children.size()) {
                            children.remove(i);
                        }
                    }

                }

                for (int i = children.size() - 1 ; i >= 0 ; --i){
                    stack.add(children.get(i));
                    path.add(children.get(i));
                }
            }
        }

        end = System.currentTimeMillis();
        if (!result.isEmpty()){
            printer.print(result.get(result.size() - 1), begin,end,sum,path);
        }
        else {
            printer.fail(sum,begin,end,path);
        }

    }

    private ArrayList<Node> path(Stack<Node> stack) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node node : stack){
            if (node.isOut())
                nodes.add(node);
        }
        if (!nodes.isEmpty())
            nodes.remove(0);
        return nodes;
    }


    private Node find(Node child, HashSet<Node> path) {
        for (Node runner : path) {
            if (runner.equals(child))
                return runner;
        }
        return null;
    }
}




