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
				var training = JSON.parse(request.responseText);
				console.log(training);
				//get elements by id and assign each value to be displayed
				document.querySelector('#idEdit').value = training.id;
				document.querySelector('#descriptionEdit').value = training.description;
				// cut time portion of date
				var date = training.date.substr(0,10);
				document.querySelector('#dateEdit').value = date;
				document.querySelector('#teamEdit').value = training.teamId;
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
//File upload
/*======================================*/
var uploadField = document.querySelectorAll("#fileSize");

for (const upload of uploadField){
	upload.addEventListener("change", function(){
		 if(this.files[0].size > 1048576 || this.files[1].size > 1048576){
		       alert("File is too big, max size 1 mbit !");
		       this.value = "";
		    };
	})
}

/*======================================*/
//Search
/*======================================*/
var clearButton = document.querySelector('#clear-button');
clearButton.addEventListener('click', function(){
	//clear url 
	window.location='/rest/training/1';
})