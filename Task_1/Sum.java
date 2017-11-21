public class Sum {
//    private static void printUsage(){
//        System.out.println("Usage: java Sum <num 1> <num 2> ...\nNumbers can be included in string");
//    };
    public static void main(String[] args) {
//        if (args.length < 1) {
//            printUsage();
//            return;
//        }
        int ans = 0;
        for(int i = 0; i < args.length; i++) {
            for (int left = 0; left < args[i].length(); left++) {
                if(!Character.isWhitespace(args[i].charAt(left))) {
                    int right = left;
                    while(right < args[i].length() && !Character.isWhitespace(args[i].charAt(right))) {
                        right++;
                    }
                    ans += Integer.parseInt(args[i].substring(left, right));
                    left = right;
                }
            }
        }
        System.out.println(ans);
    }
}
