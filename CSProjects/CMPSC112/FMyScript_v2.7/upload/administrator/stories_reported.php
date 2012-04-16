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

include("../include/config.php");
include_once("../include/functions/import.php");
verify_login_admin();
$adminurl = $config['adminurl'];

// DELETE 
if($_REQUEST[delete]=="1")
{
	$DPID = intval($_REQUEST['PID']);
	if($DPID > 0)
	{
		$deleteme = $DPID;
		
		$query="SELECT CID FROM posts_comments WHERE PID='".mysql_real_escape_string($DPID)."'";
		$results = $conn->execute($query);
		$returnthis = $results->getrows();
		$vtotal = count($returnthis);
		for($i=0;$i<$vtotal;$i++)
		{
			$DCID = intval($returnthis[$i]['CID']);
			if($DCID > 0)
			{
				$sql="DELETE FROM posts_comments_reports WHERE CID='".mysql_real_escape_string($DCID)."'";
				$conn->Execute($sql);
			}
		}
		$deletefrom[] = "posts";
		$deletefrom[] = "posts_comments";
		$deletefrom[] = "posts_comments_reports";
		$deletefrom[] = "posts_favorited";
		$deletefrom[] = "posts_reports";

		for($j=0;$j < count($deletefrom);$j++)
		{
			$query = "DELETE FROM ".$deletefrom[$j]." WHERE PID='$deleteme'";
			$conn->Execute($query);
		}
		
		$message = "Story Successfully Deleted.";
		Stemplate::assign('message',$message);
	}
}
// DELETE 

//ACTIVE
if($_POST['asub']=="1")
{
	$APID = $_POST['APID'];
	$aval = $_POST['aval'];
	if($aval == "0")
	{
		$aval2 = "1";
	}
	else
	{
		$aval2 = "0";
	}
	$sql="UPDATE posts SET active='".intval($aval2)."' WHERE PID='".mysql_real_escape_string($APID)."'";
	$conn->Execute($sql);
}
//ACTIVE

//DELETE REPORT
if($_POST['rsub']=="1")
{
	$RRID = $_POST['RRID'];
	$sql="DELETE FROM posts_reports WHERE RID='".mysql_real_escape_string($RRID)."'";
	$conn->Execute($sql);
}
//DELETE REPORT

if($_REQUEST['sortby']=="story")
{
	$sortby = "story";
	$sort =" order by A.story";
	$add1 = "&sortby=story";
}
elseif($_REQUEST['sortby']=="time")
{
	$sortby = "time";
	$sort =" order by time";
	$add1 = "&sortby=time";
}
elseif($_REQUEST['sortby']=="pip")
{
	$sortby = "pip";
	$sort =" order by A.pip";
	$add1 = "&sortby=pip";
}
else
{
	$sortby = "PID";
	$sort =" order by A.PID";
	$add1 = "&sortby=PID";
}

if($_REQUEST['sorthow']=="desc")
{
	$sorthow ="desc";
	$add1 .= "&sorthow=desc";
}
else
{
	$sorthow ="asc";
	$add1 .= "&sorthow=asc";
}

//Search
$fromid = intval($_REQUEST['fromid']);
$toid = intval($_REQUEST['toid']);
$story = $_REQUEST['story'];
$active = $_REQUEST['active'];
$add1 .= "&fromid=$fromid&toid=$toid&story=$story&remarks=$remarks&username=$username&mature=$mature&active=$active";
if($_POST['submitform'] == "1" || ($_REQUEST['fromid']!="" || $toid>0 || $story!="" || $active!=""))
{
	if($fromid > 0)
	{
		$addtosql = "AND A.PID>='".mysql_real_escape_string($fromid)."'";
		Stemplate::assign('fromid',$fromid);
	}
	else
	{
		$addtosql = "AND A.PID>'".mysql_real_escape_string($fromid)."'";
	}
	if($toid > 0)
	{
		$addtosql .= "AND A.PID<='".mysql_real_escape_string($toid)."'";
		Stemplate::assign('toid',$toid);
	}
	if($story != "")
	{
		$addtosql .= "AND A.story like'%".mysql_real_escape_string($story)."%'";
		Stemplate::assign('story',$story);
	}
	if($active != "")
	{
		$addtosql .= "AND A.active='1'";
		Stemplate::assign('active',$active);
	}
	Stemplate::assign('search',"1");
}
//Search End

$page = intval($_REQUEST['page']);
if($page=="")
{
	$page = "1";
}
$currentpage = $page;

if ($page >=2)
{
	$pagingstart = ($page-1)*$config['items_per_page'];
}
else
{
	$pagingstart = "0";
}

$queryselected = "select A.PID,C.RID from posts A, posts_reports C WHERE A.PID=C.PID $addtosql $sort $sorthow limit $config[maximum_results]";
$query2 = "select A.*,C.* from posts A, posts_reports C WHERE A.PID=C.PID $addtosql $sort $sorthow limit $pagingstart, $config[items_per_page]";
$executequeryselected = $conn->Execute($queryselected);
$totalposts = $executequeryselected->rowcount();	
if ($totalposts > 0)
{
	if($totalposts<=$config[maximum_results])
	{
		$total = $totalposts;
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
	$results = $executequery2->getrows();
	$beginning=$pagingstart+1;
	$ending=$pagingstart+$executequery2->recordcount();
	$pagelinks="";
	$k=1;
	$theprevpage=$currentpage-1;
	$thenextpage=$currentpage+1;
	if ($currentpage > 0)
	{	
		if($currentpage > 1) 
		{
			$pagelinks.="<a href='$adminurl/stories_reported.php?page=1$add1' title='first page'>First</a>&nbsp;";
			$pagelinks.="<a href='$adminurl/stories_reported.php?page=$theprevpage$add1'>Previous</a>&nbsp;";
		};
		$counter=0;
		$lowercount = $currentpage-5;
		if ($lowercount <= 0) $lowercount = 1;
		while ($lowercount < $currentpage)
		{
			$pagelinks.="<a href='$adminurl/stories_reported.php?page=$lowercount$add1'>$lowercount</a>&nbsp;";
			$lowercount++;
			$counter++;
		}
		$pagelinks.=$currentpage."&nbsp;";
		$uppercounter = $currentpage+1;
		while (($uppercounter < $currentpage+10-$counter) && ($uppercounter<=$toppage))
		{
			$pagelinks.="<a href='$adminurl/stories_reported.php?page=$uppercounter$add1'>$uppercounter</a>&nbsp;";
			$uppercounter++;
		}
		if($currentpage < $toppage) 
		{
			$pagelinks.="<a href='$adminurl/stories_reported.php?page=$thenextpage$add1'>Next</a>&nbsp;";
			$pagelinks.="<a href='$adminurl/stories_reported.php?page=$toppage$add1' title='last page'>Last</a>&nbsp;";
		};
	}
}
else
{
	$error = "There are no reported stories.";
}

$mainmenu = "4";
$submenu = "1";
Stemplate::assign('mainmenu',$mainmenu);
Stemplate::assign('submenu',$submenu);
Stemplate::assign('sorthow',$sorthow);
Stemplate::assign('sortby',$sortby);
Stemplate::assign('currentpage',$currentpage);
STemplate::display("administrator/global_header.tpl");
STemplate::assign('beginning',$beginning);
STemplate::assign('ending',$ending);
STemplate::assign('pagelinks',$pagelinks);
STemplate::assign('total',$total+0);
STemplate::assign('results',$results);
Stemplate::assign('error',$error);
STemplate::display("administrator/stories_reported.tpl");
STemplate::display("administrator/global_footer.tpl");
?>