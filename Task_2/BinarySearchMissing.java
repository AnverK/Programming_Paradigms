public class BinarySearchMissing {
    // pre: (0 <= i <= j < a.length <=> a[i] >= a[j]) && a[-1] = +inf && a[a.length] = -inf
    // post: min i: a[i] <= x
    private static int cyclicBinarySearch(int x, int[] a) {
        // This function doesn't change a, so precondition is always true if it was true in the beginning.
        int l = -1, r = a.length;
        // l == -1, r == a.length
        // I: a[l'] > x && a[r'] <= x && r' - l' <= (r - l + 1) / 2 && r' >= l'
        while (l < r - 1) {
            // l < r - 1 && I
            int m = (l + r) / 2;
            // l < r - 1 && I && m == (l + r) / 2
            if (a[m] > x) {
                // a[m] > x && l < r - 1 && I && m == (l + r) / 2
                l = m;
                // l' == m
                // l' == (l + r) / 2 <= (r - 1 + r) / 2 == (2 * r - 1) / 2 == floor(r) == r == r'
                // r' - l' == r - (l + r) / 2 <= r - floor((l + r) / 2) == r - (l + r - 1) / 2 == (r - l + 1) / 2 -- proof for I
                // a[l'] > x && a[r'] <= x && r' - l' <= (r - l + 1) / 2 && m == (l + r) / 2 && r' >= l'
            } else {
                // a[m] <= x && l < r - 1 && I && m == (l + r) / 2
                r = m;
                // r' == m
                // r' == (l + r) / 2 >= (l + 1 + l) / 2 == (2 * l + 1) / 2 == ceiling(l) == l == l'
                // r' - l' == (l + r) / 2 - l <= ceiling((l + r) / 2) - l <= (l + r + 1) / 2 - l <= (r - l + 1)/2 -- proof for I
                // a[l'] > x && a[r'] <= x && r' - l' <= (r - l + 1) / 2 && m == (l + r) / 2 && r' >= l'
            }
            // a[l'] > x && a[r'] <= x && r' - l' <= (r - l + 1) / 2 && m == (l + r) / 2 && r' >= l'
            // This invariant means that after every iteration length between left and right borders reduces:
            // r' - l' == len2, r - l == len1. I ==> len2 < len1. Proof:
            // Let len2 >= len1. Then len2 <= (len1 + 1) / 2 <= (len2 + 1) / 2.
            // let (len2 + 1) % 2 == 0. Then len2 <= 1, len1 <= len2 <= 1, but condition: l < r - 1, so len1 > 1.
            // Let (len2 + 1) % 2 == 1. Then (len2 + 1) / 2 == len2 / 2, so len2 <= 0 and len1 <= 0. But len1 > 1.
            // That is why the cycle always has end if preconditions before it were true
            // I && m == (l + r) / 2
        }
        // r - l <= 1 && r >= l && a[r] <= x && a[l] > x && (0 <= i <= j < a.length  <=> a[i] >= a[j])
        // for each i: 0 <= i <= l a[i] > x; r <= l + 1 && a[r] <= x
        // so r is min i: a[i] <= x.
        if (r < a.length && a[r] == x)
            return r;
        return -r - 1;
    }

    // pre: 1) (0 <= i <= j < a.length <=> a[i] >= a[j]) && a[-1] = +inf && a[a.length] = -inf
    // 2) a[l] > x && a[r] <= x
    // 3) r >= l
    // post: min i: a[i] <= x
    private static int recursiveBinarySearch(int x, int a[], int l, int r) {
        // pre
        if (l >= r - 1) {
            // r - l <= 1 && r >= l && (a[r] <= x && a[l] > x) && (0 <= i <= j < a.length <=> a[i] >= a[j])
            // for each i: 0 <= i <= l a[i] > x; r <= l + 1 && a[r] <= x, so r is min i: a[i] <= x
            if (r < a.length && a[r] == x)
                return r;
            return -r - 1;
        }
        int m = (l + r) / 2;
        // pre && m == (l + r) / 2
        if (a[m] > x) {
            // a[m] > x && l < r - 1 && m == (l + r) / 2. Proof that precondition is true (now l' = m):
            // 1) 0 <= i <= j < a.length  <=> a[i] >= a[j] it's true because we don't change a.
            // 3) l' == (l + r) / 2 <= (r - 1 + r) / 2 == (2 * r - 1) / 2 == floor(r) == r
            // 2) a[r] <= x -- true because we don't change a and r && a[m] > x.
            // So, precondition is true, so it's ok to run this function.
            // Also, len([m;r]) < len([l;r]) (proof about lens in cyclicBinarySearch), so the answer would be found out.
            return recursiveBinarySearch(x, a, m, r);
        } else {
            // a[m] <= x && l < r - 1 && m == (l + r) / 2. Proof that precondition is true (now r' = m):
            // 1) 0 <= i <= j < a.length  <=> a[i] >= a[j] it's true because we don't change a.
            // 3) r' == (l + r) / 2 >= (l + 1 + l) / 2 == (2 * l + 1) / 2 == ceiling(l) == l
            // 2) ((for each i: 0 <= i <= l: a[i] > x) || l == -1) -- true because we don't change a and l.
            // r' <= a.length. If r == a.length -- ok. Else, because of a[m] <= x. And because of "1)" for each i: r <= i < a.length: a[i] <= x
            // So, precondition is true, so it's ok to run this function.
            // Also, len([l;m]) < len([l;r]) (proof about lens in cyclicBinarySearch), so the answer would be found out.
            return recursiveBinarySearch(x, a, l, m);
        }
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int a[] = new int[args.length - 1];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
//        System.out.println(cyclicBinarySearch(x, a));
        System.out.println(recursiveBinarySearch(x, a, -1, a.length));

    }
}
