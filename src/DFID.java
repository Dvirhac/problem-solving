import java.util.HashSet;

public class DFID   {
    public CFS search(Node start, Node goal) {
        long start_time = System.currentTimeMillis();
        for (int limit = 1; true; ++limit){
            HashSet<Node> path = new HashSet<>();
            CFS result = limitedDFS(start, goal, limit,path);
            if (result != CFS.CUTOFF){
                long end_time = System.currentTimeMillis();
                double total_time = end_time-start_time;
                System.out.println(total_time/1000);
                return result;
            }

        }
    }

    private CFS limitedDFS(Node node, Node goal, int limit, HashSet<Node> path){
        if (goal.equals(node))
            return path(node);
        else if (limit == 0)
            return CFS.CUTOFF;
        else{
            path.add(node);
            boolean is_cutoff = false;
            ChildrenMaker childrenMaker = new ChildrenMaker();
            childrenMaker.set_node(node);
            for (Node child = childrenMaker.getChild(); child != null; child = childrenMaker.getChild()){
                if (path.contains(child))
                    continue;
                CFS result = limitedDFS(child, goal, limit-1, path);
                if (result == CFS.CUTOFF)
                    is_cutoff = true;
                else if (result != CFS.FAIL){
                    return CFS.SUCCESS;
                }
            }

            path.remove(node);
            if (is_cutoff){
                return CFS.CUTOFF;
            }
            else{
                return CFS.FAIL;
            }
        }
    }


    private CFS path(Node goal){
        Printer.print(goal);
        return CFS.SUCCESS;
    }

}
