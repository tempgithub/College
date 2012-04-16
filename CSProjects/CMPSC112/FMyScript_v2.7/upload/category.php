<?php
/**************************************************************************************************
| F My Script
| http://www.fmyscript.com
| webmaster@fmyscript.com
|
|**************************************************************************************************
|
| By using this software you agree that you have read and acknowledged our End-User License 
| Agreement available at http://www.fmyscript.com/eula.html and to be bound by it.
|
| Copyright (c) 2010 fmyscript.com. All rights reserved.
|**************************************************************************************************/

include("include/config.php");
include("include/functions/import.php");
$thebaseurl = $config['baseurl'];

$catid = intval($_REQUEST[cat]);
$page = intval($_REQUEST[page]);

$s = stripslashes(strip_tags($_REQUEST['s']));
STemplate::assign('s',$s);
if($s == "t")
{
	$ftime = time() - (24 * 60 * 60);
	$addsql .= " AND time_added>='$ftime'";
	$addsql2 .= " AND A.time_added>='$ftime'";
}
elseif($s == "w")
{
	$ftime = time() - (7 * 24 * 60 * 60);
	$addsql .= " AND time_added>='$ftime'";
	$addsql2 .= " AND A.time_added>='$ftime'";
}
elseif($s == "m")
{
	$ftime = firstDayOfMonth2();
	$addsql .= " AND time_added>='$ftime'";
	$addsql2 .= " AND A.time_added>='$ftime'";
}
elseif($s == "y")
{
	$ftime = firstDayOfYear2();
	$addsql .= " AND time_added>='$ftime'";
	$addsql2 .= " AND A.time_added>='$ftime'";
}

if($page=="")
{
	$page = "1";
}
$currentpage = $page;

if ($page >=2)
{
	$pagingstart = ($page-1)*$config[items_per_page];
}
else
{
	$pagingstart = "0";
}

$query1 = "SELECT count(*) as total from posts where active='1' $addsql AND category='".$catid."' order by PID desc limit $config[maximum_results]";
$query2 = "SELECT * from posts A, categories B where A.active='1' $addsql2 AND A.category=B.CATID AND A.category='".$catid."' order by A.PID desc limit $pagingstart, $config[items_per_page]";
	
$executequery1 = $conn->Execute($query1);

$totalposts = $executequery1->fields['total'];
if ($totalposts > 0)
{
	if($executequery1->fields['total']<=$config[maximum_results])
	{
		$total = $executequery1->fields['total'];
	}
	else
	{
		$total = $config[maximum_results];
	}
	
	$toppage = ceil($total/$config[items_per_page]);
	if($toppage==0)
	{
		$xpage=$toppage+1;
	}
	else
	{
		$xpage = $toppage;
	}
	
	$executequery2 = $conn->Execute($query2);
	$posts = $executequery2->getrows();
	$beginning=$pagingstart+1;
	$ending=$pagingstart+$executequery2->recordcount();
	$pagelinks="";
	$k=1;
	$theprevpage=$currentpage-1;
	$thenextpage=$currentpage+1;
	
	if($s != "")
	{
		$adds = "&s=$s";
	}
	if ($currentpage > 0)
	{
		if($currentpage > 1) 
		{
			$pagelinks.="<a href='$thebaseurl/category.php?cat=$catid&page=1$adds'>$lang[64]</a>&nbsp;&nbsp;";
			$pagelinks.="...&nbsp;&nbsp;";
			$pagelinks.="<a href='$thebaseurl/category.php?cat=$catid&page=$theprevpage$adds'>&laquo; $lang[65]</a>&nbsp;&nbsp;";
			STemplate::assign('tpp',$theprevpage);
		}
		
		$counter=0;
		
		$lowercount = $currentpage-5;
		if ($lowercount <= 0) $lowercount = 1;
		
		while ($lowercount < $currentpage)
		{
			$pagelinks.="<a href='$thebaseurl/category.php?cat=$catid&page=$lowercount$adds'>$lowercount</a>&nbsp;&nbsp;";
			$lowercount++;
			$counter++;
		}
		
		$pagelinks.="<strong>$currentpage</strong>&nbsp;&nbsp;";
		
		$uppercounter = $currentpage+1;
		
		while (($uppercounter < $currentpage+10-$counter) && ($uppercounter<=$toppage))
		{
			$pagelinks.="<a href='$thebaseurl/category.php?cat=$catid&page=$uppercounter$adds'>$uppercounter</a>&nbsp;&nbsp;";
			$uppercounter++;
		}
		
		if($currentpage < $toppage) 
		{
			$pagelinks.="<a href='$thebaseurl/category.php?cat=$catid&page=$thenextpage$adds'>$lang[66] &raquo;</a>&nbsp;&nbsp;";
			STemplate::assign('tnp',$thenextpage);
			$pagelinks.="...&nbsp;&nbsp;";
			$pagelinks.="<a href='$thebaseurl/category.php?cat=$catid&page=$toppage$adds'>$lang[67]</a>&nbsp;&nbsp;";
		}
	}
}

if(intval($total) == "0")
{
	$error = $lang['76'];
}

$templateselect = "category.tpl";
$cname = get_cat($catid);
$pagetitle = $cname;
STemplate::assign('cname',$cname);
STemplate::assign('cid',$catid);
STemplate::assign('pagetitle',$pagetitle);

//TEMPLATES BEGIN
STemplate::assign('error',$error);
STemplate::assign('beginning',$beginning);
STemplate::assign('ending',$ending);
STemplate::assign('pagelinks',$pagelinks);
STemplate::assign('total',$total);
STemplate::assign('posts',$posts);
STemplate::display('header.tpl');
STemplate::display($templateselect);
STemplate::display('footer.tpl');
//TEMPLATES END
?>