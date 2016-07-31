$(function(){
	
	$.get('dashboard/xhrGetListings',function(o){
		
		console.log(o);
		
		for (var i = 0; i < o.length; i++)
			{			
				$('#listInserts').append('<div>' + o[i].text + '<a class="del" rel="'+o[i].id+'"href="#">X</a></div>');
			}
		//$('#parentElement').on('click', '.myButton', function)
		//$('.del').live('click', function() {
		$('.container').on('click','.del',function(){ 
			delItem = $(this);
			var id = $(this).attr('rel');
			alert(id);
			
			$.post('dashboard/xhrDeleteListing', {'id': id}, function(o){
				delItem.parent().remove();
				//alert(1);
			}, 'json');
			
			return false;
		});
		
		
	}, 'json');
	
	
	
	$('#randomInsert').submit(function(){
		var url = $(this).attr('action');
		var data = $(this).serialize();
		
		
		$.post(url, data, function(o){
			
			console.log(o);
			//alert(o.text);
			$('#listInserts').append('<div>' + o.text + '<a class="del" rel="'+ o.id +'" href="#">X<a/> ');
		},'json');
		return false;
	});
	

});