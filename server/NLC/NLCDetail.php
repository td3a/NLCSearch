<?php   
//取得指定位址的內容，并储存至 $text  
//http://localhost:801/nlc/NLCDetail.php?doc_library=NLC01&doc_number=007986811
$urlStr="http://opac.nlc.cn/F/?func=item-global";
// $docLibrary = "NLC01";
// $docNumber = "007986811";
$docLibrary = "";
$docNumber = "";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
	if (empty($_POST["doc_library"])) {
    	$nameErr = "doc_library is required";
    	return;
  	} else {
    	$docLibrary = test_input($_POST["doc_library"]);
  	}
  	if (empty($_POST["doc_number"])) {
    	$nameErr = "doc_number is required";
    	return;
  	} else {
    	$docNumber = test_input($_POST["doc_number"]);
  	}
  
}elseif ($_SERVER["REQUEST_METHOD"] == "GET") {
	if (empty($_GET["doc_library"])) {
    	$nameErr = "doc_library is required";
    	return;
  	} else {
    	$docLibrary = test_input($_GET["doc_library"]);
  	}
  	if (empty($_GET["doc_number"])) {
    	$nameErr = "doc_number is required";
    	return;
  	} else {
    	$docNumber = test_input($_GET["doc_number"]);
  	}  
}else{
	return;
}

$urlStr = $urlStr."&doc_library=".$docLibrary."&doc_number=".$docNumber;
$allText=file_get_contents($urlStr);    

$bookArray = fileStr2Array($allText);
$storeDetail = showDetail($bookArray);
echo urldecode(json_encode($storeDetail));


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}


function fileStr2Array($text){	  
	$startStr='<table border=0 cellspacing=2 width=96%>';
	$startPostion = strpos($text, $startStr);

	$endStr="<!-- item-global-body-tail -->";
	$endPostion = strpos($text, $endStr);

	$text2=substr($text,$startPostion,$endPostion-$startPostion); 	
	$text2 = $text2."</table>";		
	$bookArray= array();
	$i=0;
	while (true) {	
		$startPostion = strpos($text2, "<tr bgcolor=");
		if($startPostion == 0) break;		
		$endPostion = strpos($text2,"</tr>");		
		$subTextStr = substr($text2,$startPostion,$endPostion);	

		$bookArray[$i]=$subTextStr;	
		$i++;
		$text2=substr($text2, $endPostion+5);							
	}
	unset($bookArray[0]); 
	return $bookArray;
}

function showDetail($bookArray){	
	$detailArray = array();	
	$i =0;
 	foreach ($bookArray as $tr ) { 	
 		$detail = array();	
 		$endPostion = strpos($tr, "</td>");
 		$tr = substr($tr, $endPostion+5);			

 		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>");
 		$length = $endPostion - $startPostion - 31;
 	 	$filed1 = substr($tr, $startPostion+31, $length); 	 	
		$detail['reservation'] = urlencode(trim(ltrim($filed1, '<br>')));
 	 	//echo "预约:".$filed1."<br>";		
 	 	$endPostion = strpos($tr, "</td>");			
		$tr = substr($tr, $endPostion+5);							 		

		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>"); 		
 		$length = $endPostion - $startPostion - 31;
 	 	$filed1 = substr($tr, $startPostion+31, $length);	
 	 	//echo "单册状态:".$filed1."<br>";
		$detail['loanstatus'] = urlencode(trim(ltrim($filed1, '<br>')));
 	 	$endPostion = strpos($tr, "</td>"); 	 			
		$tr = substr($tr, $endPostion+5);

		$endPostion = strpos($tr, "</td>"); 	 			
		$tr = substr($tr, $endPostion+5);				
		
		
		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>"); 		 		
 		$length = $endPostion - $startPostion - 31; 		
 	 	$filed1 = substr($tr, $startPostion+31, $length);	
 	 	//echo "索取号:".$filed1."<br>";	 	 	
		$detail['collectionno'] = urlencode(trim(ltrim($filed1, '<br>')));
 	 	$tr = substr($tr, $endPostion+5);	
 	 
		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>");
 		$length = $endPostion - $startPostion - 31; 		
 	 	$filed1 = substr($tr, $startPostion+31, $length);	
 	 	//echo "应还日期:$filed1<br>"; 	 	
		$detail['duedate'] = urlencode(trim(ltrim($filed1, '<br>')));
 	 	$tr = substr($tr, $endPostion+5);

		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>");
 		$length = $endPostion - $startPostion - 31; 		
 	 	$filed1 = substr($tr, $startPostion+31, $length);	
 	 	//echo "应还时间:".$filed1."<br>";
		$detail['duehour'] = urlencode(trim(ltrim($filed1, '<br>')));
 	 	$tr = substr($tr, $endPostion+5);					
		

		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>");
 		$length = $endPostion - $startPostion - 31; 		
 	 	$filed1 = substr($tr, $startPostion+31, $length);	
 	 	//echo "子库:".$filed1."<br>"; 	 	 	
		$detail['sublibrary'] = urlencode(trim(ltrim($filed1, '<br>'))); 	 	
		$tr = substr($tr, $endPostion+5);

		$endPostion = strpos($tr, "</td>"); 	 			
		$tr = substr($tr, $endPostion+5);
		$endPostion = strpos($tr, "</td>"); 	 			
		$tr = substr($tr, $endPostion+5);

		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>");
 		$length = $endPostion - $startPostion - 31;
 	 	$filed1 = substr($tr, $startPostion+31, $length);	
 	 	$scriptPostion = strpos($filed1, ">");
 	 	$filed1 = substr($filed1, $scriptPostion);
 	 	$filed1 = ltrim($filed1, '</A>');
 	 	//echo "架位导航:".$filed1."<br>";	
		$detail['location'] = urlencode(trim(ltrim($filed1, '<br>'))); 	 	
		$tr = substr($tr, $endPostion+5);
		
		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>");
 		$length = $endPostion - $startPostion - 31;
 	 	$filed1 = substr($tr, $startPostion+31, $length);	
 	 	//echo "请求数:".ltrim($filed1,'<br>')."<br>";
 	 	$detail['requestno'] = urlencode(trim(ltrim($filed1, '<br>')));;		 	 	
		$tr = substr($tr, $endPostion+5);

		$startPostion = strpos($tr, "<td align=");	 		
 		$endPostion = strpos($tr, "</td>");
 		$length = $endPostion - $startPostion - 31;
 	 	$filed1 = substr($tr, $startPostion+31, $length);	
 	 	//echo "条码:".$filed1."<br>"; 	 	
		$detail['barcode'] = urlencode(trim(ltrim($filed1, '<br>'))); 	 	
		$tr = substr($tr, $endPostion+5);

		$detailArray[$i] = $detail;
		$i++;
 	 }
 	 return $detailArray; 
}

?>