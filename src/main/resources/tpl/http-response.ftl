<#ftl output_format="HTML">
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpResponseAttachment" -->
<div>Status code: <#if data.responseCode??>${data.responseCode}<#else>Unknown</#if></div>

<#assign isLoginRequest = (data.url?? && data.url?contains("/login"))>

<#if data.url??>
    <div>URL: ${data.url}</div>
</#if>

<#if data.body??>
    <h4>Body</h4>
    <div>
        <pre class="preformated-text">
<#assign maskedBody = data.body>
<#if isLoginRequest>
    <#assign maskedBody = maskedBody?replace('"(email|password)"\\s*:\\s*"[^"]+"', '"$1": "****"', 'r')>
</#if>
<#assign maskedBody = maskedBody?replace('"accessToken"\\s*:\\s*"[^"]+"', '"accessToken": "****"', 'r')>
<#t>${maskedBody}
        </pre>
    </div>
</#if>

<#if (data.headers)?has_content>
    <h4>Headers</h4>
    <div>
        <#list data.headers as name, value>
            <div>${name}: <#if name?lower_case == "authorization">****<#else>${value!"null"}</#if></div>
        </#list>
    </div>
</#if>
