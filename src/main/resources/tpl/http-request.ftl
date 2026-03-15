<#ftl output_format="HTML">
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpRequestAttachment" -->
<div><#if data.method??>${data.method}<#else>GET</#if> to <#if data.url??>${data.url}<#else>Unknown</#if></div>

<#if data.body??>
    <h4>Body</h4>
    <div>
        <pre class="preformated-text">
<#t>${data.body?replace('"(email|password|accessToken)"\\s*:\\s*"[^"]+"', '"$1": "****"', 'r')}
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

<#if (data.cookies)?has_content>
    <h4>Cookies</h4>
    <div>
        <#list data.cookies as name, value>
            <div>${name}: ${value!"null"}</div>
        </#list>
    </div>
</#if>

<#if data.curl??>
    <h4>Curl</h4>
    <div>
        <pre class="preformated-text">
<#t>${data.curl?replace('(-H "Authorization: )([^"]+)"', '$1****"', 'r')?replace('"(email|password|accessToken)"\\s*:\\s*"[^"]+"', '"$1": "****"', 'r')}
        </pre>
    </div>
</#if>
