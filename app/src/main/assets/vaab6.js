//source: http://davidwalsh.name/text-select-widget
/* attempt to find a text selection */
function getSelected() {
	if(window.getSelection) { return window.getSelection(); }
	else if(document.getSelection) { return document.getSelection(); }
	else {
		var selection = document.selection && document.selection.createRange();
		if(selection.text) { return selection.text; }
		return false;
	}
	return false;
}
/* create sniffer */
$(document).ready(function() {
	var url = 'http://www.vajehyab.com/?q={term}', vurl = 'http://vajje.com/search?query={term}', surl = 'http://sorud.info/?Text={term}', selectionImage, vImage, sImage;
	$('#maincnt').mouseup(function(e) {
		var selection = getSelected();
		if(selection && (selection = new String(selection).replace(/^\s+|\s+$/g,''))) {
			//ajax here { http://davidwalsh.name/text-selection-ajax }
			if(!selectionImage) {
				selectionImage = $('<a>').attr({
					href: url,
					title: 'جستجو در لغت‌نامهٔ دهخدا',
					target: '_blank',
					id: 'selection-image'
				}).hide();
				$(document.body).append(selectionImage);
			}
			selectionImage.attr('href',url.replace('{term}',encodeURI(selection))).css({
				top: e.pageY - 30,	//offsets
				left: e.pageX - 13 //offsets
			}).fadeIn();
			if(!vImage) {
				vImage = $('<a>').attr({
					href: vurl,
					title: 'جستجو در سایت واژه',
					target: '_blank',
					id: 'vajje'
				}).hide();
				$(document.body).append(vImage);
			}
			vImage.attr('href',vurl.replace('{term}',encodeURI(selection))).css({
				top: e.pageY + 10,	//offsets
				left: e.pageX - 13 //offsets
			}).fadeIn();

			if(!sImage) {
				sImage = $('<a>').attr({
					href: surl,
					title: 'تعیین وزن عبارت',
					target: '_blank',
					id: 'vazn'
				}).hide();
				$(document.body).append(sImage);
			}
			sImage.attr('href',surl.replace('{term}',encodeURI(selection.replace('\r\n\r\n', '\r\n').replace('\r\n', '|')))).css({
				top: e.pageY - 10,	//offsets
				left: e.pageX - 50 //offsets
			}).fadeIn();

		}
	});
	$(document.body).mousedown(function() {
		if(selectionImage) { selectionImage.fadeOut();}
		if(vImage) { vImage.fadeOut();}
		if(sImage) { sImage.fadeOut();}
	});
	//http://vedadian.net/fa/2011/01/farsi-labels-for-farsi-ordered-lists/
  $("ol").each(function() {
    var i = 1;
    $(this).css("list-style-type","none")
         .children("li").each(function() {
          var label = "";
          var order = i++;
          while(order >= 1)
          {
            label = String.fromCharCode(order % 10 + 0x6F0) + label;
            order /= 10;
          }
          if(label == "")
            label = String.fromCharCode(0 + 0x6F0);
          label += ".";
          var li = $(this);
          li.css("padding-right", "0px").css("margin-right", "10px");
          li.html("<span style=\"display:block; float:right; margin-left:10px;\">" + label + "&nbsp</span>" + li.html());
         });
  });
});