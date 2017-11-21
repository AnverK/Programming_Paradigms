public class BinarySearchSpan {
    // pre: (0 <= i <= j < a.length <=> a[i] >= a[j]) && a[-1] = +inf && a[a.length] = -inf
    // post: min i: a[i] <= x && a' == a
    private static int cyclicBinarySearchLeft(int x, int[] a) {
        // This function doesn't change a, so precondition is always true if it was true in the beginning.
        int l = -1, r = a.length;
        // l == -1, r == a.length
        // I: a[l'] > x && a[r'] <= x && r' - l' <= (r - l + 1) / 2 && r' >= l'
        while (l < r - 1) {
            // l < r - 1 && I
            int m = (l + r) / 2;
            // Note: m >= 0 because l >= -1 && r > l + 1, so r >= 0, so (l + r) / 2 >= (-1) / 2 = 0
            // m < a.length because r <= a.length && l < r - 1, so (l + r) / 2 < (2 * a.length - 1) / 2 == floor(a.length) == a.length
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
        // a' == a because we didn't change it
        return r;
    }

    // pre: 1) (0 <= i <= j < a.length <=> a[i] >= a[j]) && a[-1] = +inf && a[a.length] = -inf
    // 2) a[l] >= x && a[r] < x
    // 3) r >= l
    // 4) l >= -1 && r <= a.length
    // post: max i: a[i] >= x && a' == a
    private static int recursiveBinarySearchRight(int x, int a[], int l, int r) {
        // pre
        if (l >= r - 1) {
            // r - l <= 1 && r >= l && (a[r] < x && a[l] >= x) && (0 <= i <= j < a.length <=> a[i] >= a[j])
            // for each i: 0 <= i <= l a[i] >= x; r <= l + 1 && a[r] < x, so r is max i: a[i] >= x
            // a' == a because we didn't change it
            return l;
        }
        int m = (l + r) / 2;
        // pre && m == (l + r) / 2
        // Note: m >= 0 because l >= -1 && r > l + 1, so r >= 0, so (l + r) / 2 >= (-1) / 2 = 0
        // m < a.length because r <= a.length && l < r - 1, so (l + r) / 2 < (2 * a.length - 1) / 2 == floor(a.length) == a.length
        if (a[m] >= x) {
            // a[m] >= x && l < r - 1 && m == (l + r) / 2. Proof that precondition is true (now l' = m):
            // 1) 0 <= i <= j < a.length  <=> a[i] >= a[j] it's true because we don't change a.
            // 3) l' == (l + r) / 2 <= (r - 1 + r) / 2 == (2 * r - 1) / 2 == floor(r) == r == r'
            // 2) a[r] < x -- true because we don't change a and r && a[m] > x.
            // 4) l' == (l + r) / 2 >= (r + r) / 2 == r >= l >= -1
            // r' == r <= a.length
            // So, precondition is true, so it's ok to run this function.
            // Also, len([m; r]) < len([l; r]) (proof about lens in cyclicBinarySearch), so the answer would be found out.

            // The result is in [m; r] because in [l; m] all the elements >= x, and there is at least one element
            // which >=x and have index for it not less than in [l; m]
            return recursiveBinarySearchRight(x, a, m, r);
            // a' == a because we didn't change it
            // result of this function is right because result of upper-recursive function was right
        } else {
            // a[m] < x && l < r - 1 && m == (l + r) / 2. Proof that precondition is true (now r' = m):
            // 1) 0 <= i <= j < a.length  <=> a[i] >= a[j] it's true because we don't change a.
            // 3) r' == (l + r) / 2 >= (l + 1 + l) / 2 == (2 * l + 1) / 2 == ceiling(l) == l
            // 2) a[l] >= x -- true because we don't change a and l && a[m] < x.
            // 4) r' == (l + r) / 2 <= (l + l) / 2 == l <= r <= a.length
            // l' == l >= -1
            // So, precondition is true, so it's ok to run this function.

            // Also, len([l;m]) < len([l;r]) (proof about lens in cyclicBinarySearch), so the answer would be found out.
            // The result is in [l; m] because in [m; r] all the elements > x.
            return recursiveBinarySearchRight(x, a, l, m);
            // a' == a because we didn't change it
            // result of this function is right because result of upper-recursive function was right
        }
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int a[] = new int[args.length - 1];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
//        System.out.println(cyclicBinarySearch(x, a));
//        System.out.println(cyclicBinarySearchLeft(x, a) + " " + recursiveBinarySearchRight(x, a, -1, a.length));
        System.out.println(cyclicBinarySearchLeft(x, a) + " " + (recursiveBinarySearchRight(x, a, -1, a.length) - cyclicBinarySearchLeft(x, a) + 1));
    }
}
// Don't read it
//    args.length > 0
//    0 <= i < args.length: args[i] have to be parsed to Integer by "Integer.parseInt"
//    1 <= i <= j < args.length: args[i].toInt >= args[j].toInt
//        x == Integer.parseInt(args[0]);