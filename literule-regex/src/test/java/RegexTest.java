import com.yart.literule.regex.match.Regex;

public class RegexTest {

    private static Regex regex = Regex.compile("a(b|c)*");
    private static String[] strs = {"ac", "acc", "a", "a   bcccdb", "ab", "abcd", "a3abcd", "a33333defd", "aabcabcabcabcabcabcdb",
            "abbbbbbbbb", "acccccccbad", "acccccccccccccccccccccccccb", "abbbbbbbbbbbbbbbc"};

//    @Benchmark
//    @Measurement(iterations = 2)
//    @Threads(1)
//    @Fork(0)
//    @Warmup(iterations = 0)
//    public void nfa() {
//        for (String str : strs) {
//            regex.isMatch(str);
//        }
//    }
//

    public static void main(String[] args) {
        test();
//        Options options = new OptionsBuilder().include(RegexTest.class.getSimpleName()).build();
//        try {
//            new Runner(options).run();
//        } catch (Exception e) {
//            System.out.println(e.fillInStackTrace());
//        } finally {
//            System.out.println("finshed");
//        }
    }

    private static void test() {
//        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
//        Regex regex1 = Regex.compile("a*aaaaaaaaaaaaaaaaaaaaaab");
//        System.out.println(regex1.isDfaMatch(str));
//        System.out.println("_________________");
//        System.out.println(regex1.isMatch(str));
//        System.out.println("_________________");
//        Regex regex = Regex.compile("a(b|c)*");
//        List<String> res = regex.match("aabacabbbcaccc");
//        regex.printNfa();
//        System.out.println("");
//        regex.printDfa();
//
        System.out.println(regex.isMatch("ac"));
        System.out.println(regex.isMatch("acc"));
        System.out.println(regex.isMatch("a"));
        System.out.println(regex.isMatch("a   bcccdb"));
        System.out.println(regex.isMatch("ab"));
        System.out.println(regex.isMatch("abcd"));
        System.out.println(regex.isMatch("a3abcd"));
        System.out.println(regex.isMatch("a33333defd"));
        System.out.println(regex.isMatch("aabcabcabcabcabcabcdb"));
    }
}
