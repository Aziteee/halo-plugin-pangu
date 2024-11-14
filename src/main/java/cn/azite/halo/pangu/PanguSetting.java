package cn.azite.halo.pangu;

public record PanguSetting(String solution, String selector, boolean isProcessQuotes) {

    public static final String GROUP = "config";

    public enum Solution {
        BROWSER("browser"),
        SERVER("server");

        public final String value;

        Solution(String value) {
            this.value = value;
        }
    }
}
