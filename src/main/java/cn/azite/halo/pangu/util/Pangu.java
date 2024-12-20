package cn.azite.halo.pangu.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Paranoid text spacing for good readability, to automatically insert
 * whitespace between CJK (Chinese, Japanese,
 * Korean), half-width English, digit and symbol characters.
 *
 * <p>
 * These whitespaces between English and Chinese characters are called "Pangu
 * Spacing" by sinologist, since it separate
 * the confusion between full-width and half-width characters. Studies showed
 * that who dislike to add whitespace between
 * English and Chinese characters also have relationship problem. Almost 70
 * percent of them will get married to the one
 * they don't love, the rest only can left the heritage to their cat. Indeed,
 * love and writing need some space in good
 * time.
 * </p>
 *
 * <p>
 * <a href="https://ld246.com/article/1472639605458">為什麼你們就是不能加個空格呢？</a>
 * </p>
 *
 * @author Vinta Chen
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Aug 31, 2016
 * @since 1.6.0
 */
public class Pangu {

    /**
     * You should use the constructor to create a {@code Pangu} object with default
     * values.
     */
    public Pangu() {
    }

    /*
     * Some capturing group patterns for convenience.
     *
     * CJK: Chinese, Japanese, Korean
     * ANS: Alphabet, Number, Symbol
     */
    private static final Pattern CJK_ANS = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
                    + "([a-z0-9`~@\\$%\\^&\\*\\-_\\+=\\|\\\\/])",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern ANS_CJK = Pattern.compile(
            "([a-z0-9`~!\\$%\\^&\\*\\-_\\+=\\|\\\\;:,\\./\\?])"
                    + "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern CJK_QUOTE = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
                    + "([\"'])");

    private static final Pattern QUOTE_CJK = Pattern.compile(
            "([\"'])"
                    + "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])");

    private static final Pattern FIX_QUOTE = Pattern.compile("([\"'])(\\s*)(.+?)(\\s*)([\"'])");

    private static final Pattern CJK_BRACKET_CJK = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
                    + "([\\({\\[]+(.*?)[\\)}\\]]+)"
                    + "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])");

    private static final Pattern CJK_BRACKET = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
                    + "([\\(\\){}\\[\\]<>])");

    private static final Pattern BRACKET_CJK = Pattern.compile(
            "([\\(\\){}\\[\\]<>])"
                    + "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])");

    private static final Pattern FIX_BRACKET = Pattern.compile("([(\\({\\[)]+)(\\s*)(.+?)(\\s*)([\\)}\\]]+)");

    private static final Pattern CJK_HASH = Pattern.compile(
            "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])"
                    + "(#(\\S+))");

    private static final Pattern HASH_CJK = Pattern.compile(
            "((\\S+)#)"
                    + "([\\p{InHiragana}\\p{InKatakana}\\p{InBopomofo}\\p{InCJKCompatibilityIdeographs}\\p{InCJKUnifiedIdeographs}])");

    /**
     * Performs a paranoid text spacing on {@code text}.
     *
     * @param text the string you want to process, must not be {@code null}.
     * @return a comfortable and readable version of {@code text} for paranoiac.
     */
    public static String spacingText(String text) {
        // CJK and quotes
        Matcher cqMatcher = CJK_QUOTE.matcher(text);
        text = cqMatcher.replaceAll("$1 $2");

        Matcher qcMatcher = QUOTE_CJK.matcher(text);
        text = qcMatcher.replaceAll("$1 $2");

        Matcher fixQuoteMatcher = FIX_QUOTE.matcher(text);
        text = fixQuoteMatcher.replaceAll("$1$3$5");

        // CJK and brackets
        String oldText = text;
        Matcher cbcMatcher = CJK_BRACKET_CJK.matcher(text);
        String newText = cbcMatcher.replaceAll("$1 $2 $4");
        text = newText;

        if (oldText.equals(newText)) {
            Matcher cbMatcher = CJK_BRACKET.matcher(text);
            text = cbMatcher.replaceAll("$1 $2");

            Matcher bcMatcher = BRACKET_CJK.matcher(text);
            text = bcMatcher.replaceAll("$1 $2");
        }

        Matcher fixBracketMatcher = FIX_BRACKET.matcher(text);
        text = fixBracketMatcher.replaceAll("$1$3$5");

        // CJK and hash
        Matcher chMatcher = CJK_HASH.matcher(text);
        text = chMatcher.replaceAll("$1 $2");

        Matcher hcMatcher = HASH_CJK.matcher(text);
        text = hcMatcher.replaceAll("$1 $3");

        // CJK and ANS
        Matcher caMatcher = CJK_ANS.matcher(text);
        text = caMatcher.replaceAll("$1 $2");

        Matcher acMatcher = ANS_CJK.matcher(text);
        text = acMatcher.replaceAll("$1 $2");

        return text;
    }

    /**
     * @author Azite
     */
    public static void execute(Document document, boolean isProcessSpaces, boolean isProcessQuotes) {
        document.traverse((node, depth) -> {
            if (node instanceof TextNode textNode) {
                final Node parent = textNode.parent();
                if (parent instanceof Element parentElem) {
                    String text = textNode.getWholeText();
                    if (!parentElem.tagName().equals("code")) {
                        if (isProcessSpaces) {
                            text = spacingText(text);
                        }
                        if (isProcessQuotes) {
                            text = text.replace("“", "「");
                            text = text.replace("”", "」");
                            text = text.replace("‘", "『");
                            text = text.replace("’", "』");
                        }
                        textNode.text(text);
                    }

                    /*
                     * 处理标签被夹在文本节点之间的情况
                     * 
                     * 如 <p>测试<a>Link</a>测试</p>
                     * 将被格式化成 <p>测试 <a>Link</a> 测试</p>
                     */
                    if (isProcessSpaces && !parentElem.tagName().equals("p")) {
                        if (parentElem.previousSibling() instanceof TextNode previousTextNode) {
                            String previousText = previousTextNode.getWholeText();
                            String spacedText = spacingText(previousText + text.charAt(0));
                            spacedText = spacedText.substring(0, spacedText.length() - 1);
                            if (!spacedText.equals(previousText)) {
                                previousTextNode.text(spacedText);
                            }
                        }
                        if (parentElem.nextSibling() instanceof TextNode nextTextNode) {
                            String nextText = nextTextNode.getWholeText();
                            String spacedText = spacingText(text.charAt(text.length() - 1) + nextText);
                            spacedText = spacedText.substring(1);
                            if (!spacedText.equals(nextText)) {
                                nextTextNode.text(spacedText);
                            }
                        }
                    }
                }
            }
        });
    }

}