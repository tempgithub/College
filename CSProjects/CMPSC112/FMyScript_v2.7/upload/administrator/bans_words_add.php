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

if($_POST['submitform'] == "1")
{
	$sql = "insert bans_words set word='".mysql_real_escape_string($_POST['add'])."'";
	$conn->execute($sql);
	$message = "Word Successfully Banned.";
	Stemplate::assign('message',$message);
}

$mainmenu = "5";
$submenu = "4";
Stemplate::assign('mainmenu',$mainmenu);
Stemplate::assign('submenu',$submenu);
STemplate::display("administrator/global_header.tpl");
STemplate::display("administrator/bans_words_add.tpl");
STemplate::display("administrator/global_footer.tpl");
?>