[@ui.bambooSection titleKey="tools.redfox.bamboo.webhooks.configuration.section.title" descriptionKey="tools.redfox.bamboo.webhooks.configuration.section.description"]
    [@ww.textfield label="Shared secret" name="custom.bamboo.webhook.secret" class="long-field" required="false"/]
    [@ww.hidden name="custom.bamboo.webhook.mode" required="true" value="${mode}"/]
    <div class="aui-tabs horizontal-tabs" id="webhooks-mode">
        <ul class="tabs-menu">
            <li class="menu-item [#if mode == 1] active-tab[/#if]" data-mode="1">
                <a href="#tabs-mode-1">Basic</a>
            </li>
            <li class="menu-item [#if mode == 2] active-tab[/#if]" data-mode="2">
                <a href="#tabs-mode-2">Advanced</a>
            </li>
        </ul>
        <div class="tabs-pane [#if mode == 1] active-pane[/#if]" id="tabs-mode-1">
            <p>Use same URL for all webhooks</p>
            [@ww.textfield label="Global URL" name="custom.bamboo.webhook.global" class="long-field" required="false"/]
        </div>
        <div class="tabs-pane [#if mode == 2] active-pane[/#if]" id="tabs-mode-2">
            <p>Specify individual URL's for each event. Leave empty field to ignore given event.</p>
            [#list buildEvents as section]
                <h6>${section.getKey()}</h6>
                [#list section.getValue() as event]
                    [@ww.textfield label="${event.getName()}" name="${event.getId()}" class="long-field" type="url" required="false"/]
                [/#list]
            [/#list]
        </div>
    </div>
[/@ui.bambooSection]

<script>
    AJS.toInit(function () {
        var modeField = AJS.$("#updateChainMiscellaneous_custom_bamboo_webhook_mode");
        AJS.$('#webhooks-mode .menu-item').on("click", (e) => {
            modeField.val(AJS.$(e.currentTarget).data("mode"));
        });
    });
</script>
