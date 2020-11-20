/*======================================*/
//Edit modal
/*======================================*/
var editButtons = document.querySelectorAll('.edit-button');
var editModal = document.querySelector('#editModal');

for (const button of editButtons){
	button.addEventListener('click', function(event){
		event.preventDefault();
		
		//make new request 
		var request = new XMLHttpRequest();
		//type of request, url/file name, async or not
		request.open('GET', button.href, true);
		
		//
		request.onload = function (){
			//check if status is OK, before we do anything
			if(request.status == 200){
				// parse response text to json format, so we can acess coach properties
				var post = JSON.parse(request.responseText);
				console.log(post);
				//get elements by id and assign each value to be displayed
				document.querySelector('#idEdit').value = post.id;
				document.querySelector('#descriptionEdit').value = post.description;
				// cut time portion of date
				var date = post.date.substr(0,10);
				document.querySelector('#dateEdit').value = date;
				document.querySelector('#titleEdit').value = post.title;
			}
		}
		
		request.send();
		
		editModal.style.display ='block';
	})
}

/*======================================*/
//Close modal
/*======================================*/

var modalCloseButtons = document.querySelectorAll('.modalCloseButton');
//for all close buttons in deleteModal
for (const button of modalCloseButtons) {
	
	  button.addEventListener('click', function() {
	    
	    editModal.style.display = 'none';
	  })
	  // set if click is outside of form to close it
	  window.addEventListener('click', function(event){
		  // if user clicked on modal then close it
		  if(event.target == editModal){
				editModal.style.display = 'none';
				
				}
		  
	  });
	
}

/*======================================*/
//Search
/*======================================*/
var clearButton = document.querySelector('#clear-button');
clearButton.addEventListener('click', function(){
	//clear url 
	window.location='/rest/post';
})