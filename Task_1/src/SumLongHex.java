public class SumLongHex {
    //    private static void printUsage(){
//        System.out.println("Usage: java Sum <num 1> <num 2> ...\nNumbers can be included in string");
//    };
    public static void main(String[] args) {
//        if (args.length < 1) {
//            printUsage();
//            return;
//        }
        long ans = 0;
        for (int i = 0; i < args.length; i++) {
            for (int left = 0; left < args[i].length(); left++) {
                if (!Character.isWhitespace(args[i].charAt(left))) {
                    int right, radix = 10;
                    if (left + 1 < args[i].length() && args[i].substring(left, left + 2).toUpperCase().equals("0X")) {
//                    if(left + 1 < args[i].length() && (Character.toUpperCase(args[i].charAt(left+1)) == 'X' && args[i].charAt(left) == '0')) {
                        radix = 16;
                        left += 2;
                    }
                    right = left;
                    while (right < args[i].length() && !Character.isWhitespace(args[i].charAt(right))) {
                        right++;
                    }
                    if (radix == 16) {
                        ans += Long.parseUnsignedLong(args[i].substring(left, right).toUpperCase(), radix);
                    } else {
                        ans += Long.parseLong(args[i].substring(left, right).toUpperCase());
                    }
                    left = right;
                }
            }
        }
        System.out.println(ans);
    }
}
