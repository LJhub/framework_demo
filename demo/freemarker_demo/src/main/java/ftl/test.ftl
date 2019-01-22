<html>
<head>
    <meta charset="utf-8">
    <title>Freemarker入门小DEMO </title>
</head>

<body>
<#include "head.ftl">
<#--我只是一个注释，我不会有任何输出  -->
${name},你好。${message}
<#assign linkman='马化腾'>
联系人: ${linkman}<br/>
<#assign info={"mobile":"123456","address":"深圳黑马"}>
手机:${info.mobile}地址:${info.address}

<#if success=true>
你是大佬
<#else>
你是扫地增
</#if>

<h4>------商品列表 (语法: ?counter表示当前索引的角标 ,list as 个体  用于遍历)------</h4>
<#list list as good>
    ${good?counter}-->${good.name}:${good.price}<br/>
</#list>

<h4>计算list大小</h4>
<h6>共${list?size}条记录</h6>

<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign date=text?eval>
${date.bank}:${date.account}

<h4>--------------时间的转换 (语法:?date or time or datetime)--------------</h4>
当前日期:${now?date}<br/>
当前时间:${now?time}<br/>
当前日期时间:${now?datetime}<br/>

<h4>--------------int转换string (语法:?c)--------------</h4>
${point?c}

<h4>--------------测试null (语法:??)--------------</h4>
<#if testNull??>
    ${testNull}
<#else>
    testNull为空
</#if>

<h4>--后台不传递值,我们也可以进行非空判断,而不报错(语法:!)--</h4>
${none!'none'}

<h4>------------逻辑运算符------------</h4>
<#if one lte two+20>
one大于two
<#else>
one小于two
</#if>


</body>
</html>
