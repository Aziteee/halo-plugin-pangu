package cn.azite.halo.pangu;

import cn.azite.halo.pangu.util.Pangu;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.ReactivePostContentHandler;

@Component
@RequiredArgsConstructor
public class PanguPostContentHandler implements ReactivePostContentHandler {

    private final ReactiveSettingFetcher settingFetcher;

    @Override
    public Mono<PostContentContext> handle(@NonNull PostContentContext postContent) {
        return settingFetcher.fetch(PanguSetting.GROUP, PanguSetting.class)
                .map(setting -> {
                    boolean isServerSpacing = setting.isServerSpacing();
                    boolean isProcessQuotes = setting.isProcessQuotes();
                    if (isServerSpacing || isProcessQuotes) {
                        String oldContent = postContent.getContent();
                        Document document = Jsoup.parse(oldContent);

                        Pangu.execute(document, isServerSpacing, isProcessQuotes);

                        postContent.setContent(document.outerHtml());
                    }
                    return postContent;
                });
    }

}
