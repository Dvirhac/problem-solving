public class Printer {

    public static void print(Node goal) {
        StringBuilder path = new StringBuilder();
        int cost = 0;
        Node runner = goal;
        while(runner.get_parent() != null){
            path.append(runner.getPath());
            cost = cost + runner.get_cost();
            runner = runner.get_parent();

        }

        System.out.println("path: " + path.toString());
        System.out.println("cost: " + cost );
    }

    public static void print(String st){

    }
}
