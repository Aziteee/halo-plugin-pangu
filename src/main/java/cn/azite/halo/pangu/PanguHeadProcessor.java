package cn.azite.halo.pangu;

import cn.azite.halo.pangu.util.Pangu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.dialect.TemplateHeadProcessor;

@Component
@RequiredArgsConstructor
public class PanguHeadProcessor implements TemplateHeadProcessor{

    private final ReactiveSettingFetcher settingFetcher;

    @Override
    public Mono<Void> process(ITemplateContext context, IModel model,
        IElementModelStructureHandler structureHandler) {
        return settingFetcher.fetch(PanguSetting.GROUP, PanguSetting.class)
            .doOnNext(setting -> {
                if (setting.solution().equals("browser")) {
                    final IModelFactory modelFactory = context.getModelFactory();
                    model.add(modelFactory.createText(PanguJSInjector.getScript(setting.selector())));
                }
            })
            .then();
    }

}
