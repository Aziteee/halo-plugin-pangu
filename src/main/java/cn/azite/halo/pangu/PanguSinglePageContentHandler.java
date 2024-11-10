package cn.azite.halo.pangu;

import cn.azite.halo.pangu.util.Pangu;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.ReactiveSinglePageContentHandler;

@Component
@RequiredArgsConstructor
public class PanguSinglePageContentHandler implements ReactiveSinglePageContentHandler {

    private final ReactiveSettingFetcher settingFetcher;

    @Override
    public Mono<SinglePageContentContext> handle(@NonNull SinglePageContentContext singlePageContent) {
        return settingFetcher.fetch(PanguSetting.GROUP, PanguSetting.class)
            .map(setting -> {
                if (setting.solution().equals("server")) {
                    String oldContent = singlePageContent.getContent();
                    Document document = Jsoup.parse(oldContent);

                    Pangu.spacingDoc(document);

                    singlePageContent.setContent(document.outerHtml());
                }
                return singlePageContent;
            });
    }
}
