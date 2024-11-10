package cn.azite.halo.pangu;

public class PanguJSInjector {

    static String getScript(String selector) {
        return """
                <!-- plugin-pangu start -->
                <script defer src="/plugins/halo-plugin-pangu/assets/static/pangu.min.js"></script>
                <script>
                    document.addEventListener('DOMContentLoaded', () => {
                      const selector = "%s";
                      if (selector === "" || selector === "null") {
                        pangu.spacingPageBody();
                      } else {
                        document.querySelectorAll(selector).forEach((contentNode) => {
                          pangu.spacingNode(contentNode);
                        });
                      }
                    });
                </script>
                <!-- plugin-pangu end -->
                """.formatted(selector);
    }

}
