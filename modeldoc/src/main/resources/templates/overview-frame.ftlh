<#function tree_open count>
    <#if count == 0>
        <#return "" />
    <#else>
        <#assign spaces>
            <#list 1..count as i>
            <ul>
                <#if i < count>
                    <li>
                <#else>
                    <li class="last">
                </#if>
            </#list>
        </#assign>
        <#return spaces />
    </#if>
</#function>
<#function tree_close count>
    <#if count == 0>
        <#return "" />
    <#else>
        <#assign spaces>
            <#list 1..count as i>
            </li></ul>
            </#list>
        </#assign>
        <#return spaces />
    </#if>
</#function>
<#macro processNodes nodelist level>
    <#list nodelist as node>
        <li>${tree_open(level)}<a name=${node.packageItem.name} href="#" onClick="loadPackageFrames('${node.packageItem.url}','${node.packageItem.packageInfoPath}')">${node.packageItem.name}</a>${tree_close(level)}</li>
        <@processNodes node.childrenNodes level+1/>
    </#list>
</#macro>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- NewPage -->
<html lang="en">
<head>
    <title>All packages</title>
    <meta name="date" content="2016-01-11">
    <meta name="keywords" content="Overview List, Reference model packages">
    <link rel="stylesheet" type="text/css" href="${stylesheet}" title="Style">
    <script type="text/javascript">
        function loadPackageFrames(pkgContent, pkgInfo)
        {
            parent.packageFrame.location=pkgContent;
            parent.classFrame.location=pkgInfo;
        }
    </script>
</head>
<body>
<h1 title="Reference Model Packages" class="bar"><strong>Reference Model Packages</strong></h1>
<div class="indexHeader"><a href="allclasses-frame.html" target="packageFrame">All Classes</a></div>
<div class="indexContainer">
    <h2 title="Packages">Packages</h2>
    <ul class="tree">
        <@processNodes packagetree.childrenNodes 0/>
    </ul>
</div>
<p>&nbsp;</p>
</body>
</html>