<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- NewPage -->
<html lang="en">
<head>
    <title>Overview (CIMI Reference Model )</title>
    <meta name="date" content="2016-01-11">
    <meta name="keywords" content="Clinical Information Model Initiative (CIMI)">
    <link rel="stylesheet" type="text/css" href="${stylesheet}" title="Style">
</head>
<body>
<script type="text/javascript"><!--
if (location.href.indexOf('is-external=true') == -1) {
    parent.document.title="Overview (CIMI Reference Model)";
}
//-->
</script>
<noscript>
    <div>JavaScript is disabled on your browser.</div>
</noscript>
<!-- ========= START OF TOP NAVBAR ======= -->
<div class="topNav"><a name="navbar_top">
    <!--   -->
</a><a href="#skip-navbar_top" title="Skip navigation links"></a><a name="navbar_top_firstrow">
    <!--   -->
</a>
    <ul class="navList" title="Navigation">
        <li class="navBarCell1Rev">Overview</li>
        <!--
        <li>Package</li>
        <li>Class</li>
        <li>Use</li>
        <li><a href="overview-tree.html">Tree</a></li>
        <li><a href="deprecated-list.html">Deprecated</a></li>
        <li><a href="index-files/index-1.html">Index</a></li>
        <li><a href="help-doc.html">Help</a></li>
        -->
    </ul>
    <div class="aboutLanguage"><em><strong>HL7&trade;&nbsp;Clinical&nbsp;Information&nbsp;Model&nbsp;Initiative</strong></em></div>
</div>
<div class="subNav">
    <ul class="navList">
        <li>Prev</li>
        <li>Next</li>
    </ul>
    <ul class="navList">
        <li><a href="index.html?overview-summary.html" target="_top">Frames</a></li>
        <li><a href="overview-summary.html" target="_top">No Frames</a></li>
    </ul>
    <ul class="navList" id="allclasses_navbar_top">
        <li><a href="allclasses-noframe.html">All Classes</a></li>
    </ul>
    <div>
        <script type="text/javascript"><!--
        allClassesLink = document.getElementById("allclasses_navbar_top");
        if(window==top) {
            allClassesLink.style.display = "block";
        }
        else {
            allClassesLink.style.display = "none";
        }
        //-->
        </script>
    </div>
    <a name="skip-navbar_top">
        <!--   -->
    </a></div>
<!-- ========= END OF TOP NAVBAR ========= -->
<div class="header">
    <h1 class="title">Clinical Information Model Initiative (CIMI) Reference Model Specification</h1>
</div>
<div class="header">
    <div class="subTitle">
        <div class="block">Welcome to the HL7 CIMI Reference Model Documentation Version 0.0.3 generated on ${currentDate}.</div>
    </div>
    <p>Please click <a href="overview.html">here</a> for a high level UML view of the core model.</p>
    <p>Please click <a href="CIMIArchitectureGuide/CIMIArchitectureGuide.html">here</a> to view the CIMI Architecture Guide</p>
    <p>Please click <a href="CIMIStyleGuide/CIMIStyleGuide.html">here</a> to view the CIMI Style Guide</p>
    <p>Please click <a href="CIMIClinicalExample/CIMIClinicalExample.html">here</a> to view a clinical example on the use of CIMI models for the development of Detailed Clinical Models</p>
    <p>An archetype viewer has been made available <a href="http://models.opencimi.org/may_ballot_2017" target="_blank">here</a></p>
</div>
<div class="contentContainer">
    <table class="overviewSummary" border="0" cellpadding="3" cellspacing="0" summary="Classes and definitions">
        <caption><span>Classes</span><span class="tabEnd">&nbsp;</span></caption>
        <tr>
            <th class="colFirst" scope="col">Classes</th>
            <th class="colLast" scope="col">Description</th>
        </tr>
        <tbody>
        <#list classes?sort_by("className") as class>
            <tr class="altColor">
                <td class="colFirst">
                    <#if class.flagClass == true>
                        <b><a href="${class.classDetailsUrl}"><font color="red">${class.className}</font></a><b>
                    <#else>
                        <a href="${class.classDetailsUrl}">${class.className}</a>
                    </#if>
                </td>
                <td class="colLast">
                    <#if class.documentation == "Documentation missing">
                        <div class="block"><b><font color="red">${class.documentation}</font><b></div>
                    <#else>
                        <div class="block">${class.documentation}</div>
                    </#if>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
<div class="footer"><a name="overview_description">
    <!--   -->
</a>
    <div class="subTitle">
        <div class="block">This document is the API specification for the CIMI Reference Model</div>
    </div>
</div>
<!-- ======= START OF BOTTOM NAVBAR ====== -->
<div class="bottomNav"><a name="navbar_bottom">
    <!--   -->
</a><a href="#skip-navbar_bottom" title="Skip navigation links"></a><a name="navbar_bottom_firstrow">
    <!--   -->
</a>
    <ul class="navList" title="Navigation">
        <li class="navBarCell1Rev">Overview</li>
        <li>Package</li>
        <li>Class</li>
        <li>Use</li>
        <li><a href="overview-tree.html">Tree</a></li>
        <li><a href="deprecated-list.html">Deprecated</a></li>
        <li><a href="index-files/index-1.html">Index</a></li>
        <li><a href="help-doc.html">Help</a></li>
    </ul>
    <div class="aboutLanguage"><em><strong>CIMI Reference Model</strong></em></div>
</div>
<div class="subNav">
    <ul class="navList">
        <li>Prev</li>
        <li>Next</li>
    </ul>
    <ul class="navList">
        <li><a href="index.html?overview-summary.html" target="_top">Frames</a></li>
        <li><a href="overview-summary.html" target="_top">No Frames</a></li>
    </ul>
    <ul class="navList" id="allclasses_navbar_bottom">
        <li><a href="allclasses-noframe.html">All Classes</a></li>
    </ul>
    <div>
        <script type="text/javascript"><!--
        allClassesLink = document.getElementById("allclasses_navbar_bottom");
        if(window==top) {
            allClassesLink.style.display = "block";
        }
        else {
            allClassesLink.style.display = "none";
        }
        //-->
        </script>
    </div>
    <a name="skip-navbar_bottom">
        <!--   -->
    </a></div>
<!-- ======== END OF BOTTOM NAVBAR ======= -->
<!--<p class="legalCopy"><small><font size="-1"> <a href="https://docs.google.com/spreadsheets/d/1HFSSdGC7e4szzXu5wJH12c0x-nyS80LLRV68zzfovPU/edit#gid=0">Submit your comments</a> <br>For further API reference and developer documentation, see <a href="#" target="_blank">CIMI Documentation</a>. That documentation contains more detailed, developer-targeted descriptions, with conceptual overviews, definitions of terms, workarounds, and working code examples.<br> <a href="#">Copyright</a> &#x00a9; 2017 HL7 and/or its affiliates.  All rights reserved. Use is subject to <a href="#">license terms</a>. </font></small></p>-->
</body>
</html>