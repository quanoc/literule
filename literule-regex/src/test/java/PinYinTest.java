import com.yart.literule.regex.match.PinYinMatcher;

public class PinYinTest {

    public static void main(String[] args) {
        test();
    }
    public static void test(){
        PinYinMatcher regex = PinYinMatcher.compile("(.)*公众号(.)*");
//        System.out.println(regex.isMatch("我的公重号是"));
//        System.out.println(regex.isMatch("我的弓中号是的"));
//        System.out.println(regex.isMatch("我的宫重号是的"));
        System.out.println(regex.isMatch("我的公众呺是的"));

    }
}
