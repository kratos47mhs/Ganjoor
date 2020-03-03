        function FindNext () {
            var str = document.getElementById ("findInput").value;
            if (str == "") {
                alert ("برای مہربانی کچھ لکھ کر تلاش کریں۔۔!!");
                return;
            }
            
            if (window.find) {        // Firefox, Google Chrome, Safari
                var found = window.find (str);
                if (!found) {
                    alert (str +  " ~ مزید نہیں ہے" );
                }
				
            }
            else {
                alert ("Your browser does not support this example!");
            }
        }



window.onscroll = function() {scrollFunction()};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("myBtn").style.display = "block";
    } else {
        document.getElementById("myBtn").style.display = "none";
    }
}

function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

function myAlert() {
    alert("اس حرف سے کوئی سوال موجود نہیں ۔۔۔!!");}
	
	function copyToClipboard(element) {
  var $temp = $("<textarea>");
  var brRegex = /<br\s*[\/]?>/gi;
  $("body").append($temp);
  $temp.val($(element).html().replace(brRegex, "\r\n")).select();
  document.execCommand("copy");
  $temp.remove();
}