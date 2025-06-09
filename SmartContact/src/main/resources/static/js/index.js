/**
 * 
 */
console.log("thise ")

console.log("this");
function toggleFunction() {
  if ($(".sidebar").is(":visible")) {
    // Sidebar is currently visible, so we're hiding it
    $(".sidebar").hide(200);
    $(".contain").css("margin-left", "0%");
  } else {
    // Sidebar is hidden, so we're showing it
    $(".sidebar").show(200);
    $(".contain").css("margin-left", "20%");
  }
  
  
};


const serch = ()=>{
	
	console.log("");
	
	let query=$("#serch-input").val();

	
	if(query==''){
		$(".serch-result").hide();
		
	}else{
		// sending request to server
		
		let url = `http://localhost:6212/serch/${query}`;
	
		fetch(url).then((response)=>{
		return response.json();
		})
		.then((data) => {
			console.log(data);
			
			let text = "<div class='list-group'>";

			  data.forEach((contact) => {
			      text += `<a href='/user/contact/${contact.cid}' class='list-group-item list-group-item-action'>
			                  ${contact.name}
			               </a>`;
			  });

			  text += "</div>";

			
			  $(".serch-result").html(text);
			            $(".serch-result").show();
			
		});
		
		console.log(query);
		$(".serch-result").show()
	}
}


