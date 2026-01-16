package ds.stack;
import java.util.EmptyStackException;

public class ParenMatcher {

    // Returns true iff all grouping symbols in X match correctly.
    // Tokens that are not grouping symbols are ignored.
   public static boolean parenMatch(String expr) {
        int n = expr.length()
        ArrayStack<Character> S = new ArrayStack<>(n);
        boolean valid = true;
        int i = 0;
        char c;
    
        while (i < n && valid) {
            c = expr.charAt(i);
    
            if (isOpening(c)) {
                S.push(c);
            } 
            else if (isClosing(c)) {
                if (S.isEmpty()) {
                    valid = false;           // nothing to match with
                } 
                else if (!matches(S.pop(), c)) {
                    valid = false;           // wrong type
                }
            }
            i++;
        }
        return valid && S.isEmpty();
    }

    private static boolean isOpening(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    private static boolean isClosing(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')')
            || (open == '[' && close == ']')
            || (open == '{' && close == '}');
    }
}
