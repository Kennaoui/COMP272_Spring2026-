import ds.stack.*;

public class ParenMatcherStringTest {

    public static void main(String[] args) {

        String[] tests = {
            "(a+b)*[c]",          // true
            "([])",               // true
            "([)]",               // false (wrong nesting)
            ")a+b",               // false (closing too early)
            "(()",                // false (leftover opening)
            "{[()()]}",           // true
            "",                   // true (no symbols to mismatch)
            "abc + 123",          // true (no grouping symbols)
            "([{}])",             // true
            "((2+3)*5])"          // false (mismatched closing)
        };

        for (int i = 0; i < tests.length; i++) {
            String expr = tests[i];
            boolean result = ParenMatcher.parenMatch(expr);
            System.out.println("Test " + (i + 1) + ": \"" + expr + "\" -> " + result);
        }
