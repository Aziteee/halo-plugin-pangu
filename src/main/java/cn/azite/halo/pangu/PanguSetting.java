package cn.azite.halo.pangu;

public record PanguSetting(String solution, String selector, boolean isProcessQuotes) {

    public static final String GROUP = "config";

    public enum Solution {
        BROWSER("browser"),
        SERVER("server"),
        BOTH("both");

        public final String value;

        Solution(String value) {
            this.value = value;
        }
    }

    public boolean isServerSpacing() {
        return this.solution.equals(Solution.SERVER.value) || this.solution.equals(Solution.BOTH.value);
    }

    public boolean isBrowserSpacing() {
        return this.solution.equals(Solution.BROWSER.value) || this.solution.equals(Solution.BOTH.value);
    }
}
