<?php   
//取得指定位址的內容，并储存至 $text  
//http://localhost:801/nlc/NLCSearch.php?keystr=python&local_base=NLC09
$urlStr="http://opac.nlc.gov.cn/F?func=find-b&find_code=WRD";
//$localBase = 'NLC01';
//$keyStr = 'python';
$books = array();

$keyStr = "";
$localBase = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
	if (empty($_POST["keystr"])) {
    	$nameErr = "keystr is required";
    	return;
  	} else {
    	$keyStr = test_input($_POST["keystr"]);
  	}
  	if (empty($_POST["local_base"])) {
    	$nameErr = "local_base is required";
    	return;
  	} else {
    	$localBase = test_input($_POST["local_base"]);
  	}
  
}elseif ($_SERVER["REQUEST_METHOD"] == "GET") {
	if (empty($_GET["keystr"])) {
    	$nameErr = "keystr is required";
    	return;
  	} else {
    	$keyStr = test_input($_GET["keystr"]);
  	}
  	if (empty($_GET["local_base"])) {
    	$nameErr = "local_base is required";
    	return;
  	} else {
    	$localBase = test_input($_GET["local_base"]);
  	}  
}else{
	return;
}

$urlStr = $urlStr."&local_base=".$localBase."&request=".$keyStr;

$allText=file_get_contents($urlStr);    

$bookArray = fileStr2Array($allText);

$books = array();
$i =0;
foreach ($bookArray as $book1) {	
 	$book = showBookDetail($book1);
 	$books[$i] = $book;
 	$i++;
}

echo urldecode(json_encode($books));


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

function fileStr2Array($text){	  
	$startStr='<table class=items';
	$startPostion = strpos($text, $startStr);

	$endStr="<!-- filename: short-lcl-tail -->";
	$endPostion = strpos($text, $endStr);

	$text2=substr($text,$startPostion,$endPostion-$startPostion); 
	$bookArray= array();

	$i=0;
	while (true) {	
		$endPostion=strpos($text2,"</table>");		
		$subTextStr= substr($text2,0,$endPostion);	

		$bookArray[$i]=$subTextStr;	
		$i++;
		$text2=substr($text2, $endPostion);
		$startPostion=strpos($text2, $startStr);
		
		if($startPostion==0){
			break;
		}
		$text2=substr($text2, $startPostion);		
	}
	return $bookArray;
}


function showBookDetail($book1){
	//$book1= $bookArray[0];
	//书编号
	$bookidstartStr="doclist[";
	$bookidendStr="]=";
	$bookidstart= strpos($book1, $bookidstartStr);
	$bookidend= strpos($book1, $bookidendStr);
	$bookid= substr($book1,$bookidstart+9,$bookidend-$bookidstart-10);
	//书名
	$booknameStartStr="itemtitle>";
	$booknameStart= strpos($book1,$booknameStartStr);
	$book1=substr($book1,$booknameStart+10);
	$booknameStartStr=">";
	$booknameStart= strpos($book1,$booknameStartStr);
	$booknameEndStr="</a>";
	$booknameEnd=strpos($book1,$booknameEndStr);
	$bookname=substr($book1,$booknameStart+1,$booknameEnd-$booknameStart-1);
	//作者
	$bookauthorStartStr="class=content>";
	$bookauthorStart=strpos($book1, $bookauthorStartStr);
	$book1= substr($book1, $bookauthorStart+14);
	$bookauthorEndStr="<!-- ";
	$bookauthorEnd= strpos($book1, $bookauthorEndStr);
	$author=substr($book1, 0,$bookauthorEnd);
	$book1= substr($book1, $bookauthorEnd);
	//去除注释行
	$sp="-->";
	$spEnd= strpos($book1, $sp);
	$book1= substr($book1, $spEnd+1);
	//出版社
	$publishStartStr="class=content>";
	$publishStart=strpos($book1, $publishStartStr);
	$book1= substr($book1, $publishStart+14);
	$publishEndStr="<tr>";
	$publishEnd= strpos($book1, $publishEndStr);
	$publish=substr($book1, 0,$publishEnd);
	$publish=trim($publish);
	
	$book1= substr($book1, $publishEnd);
	//年份
	$yearStartStr="class=content>";
	$yearStart=strpos($book1, $yearStartStr);
	$yearStr=substr($book1, $yearStart+14,4);

	
	$book = array();
	//echo "编号:".$bookid."<br>";	
	$book['bookid'] =  urlencode(trim(ltrim($bookid, '<BR>')));	
	//echo "书名:".$bookname."<br>";	
	$book['bookname'] = urlencode(trim(ltrim($bookname, '<BR>')));	
	//echo "作者:".$author."<br>";
	$book['author'] = urlencode(trim(ltrim($author, '<BR>')));	
	//echo "出版社:".$publish."<br>"
	$book['publish'] = urlencode(trim(ltrim($publish, '<BR>')));	
	//echo "出版年份:".$yearStr."<br>";	
	$book['year'] = urlencode(trim(ltrim($yearStr, '<BR>')));	
	return $book;	
}
?>