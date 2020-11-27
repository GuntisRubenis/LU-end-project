/*======================================*/
//Start of edit modal
/*======================================*/
var editButtons = document.querySelectorAll('.edit-button');
var editModal = document.querySelector('#editModal');

// for all buttons in coach table
for (const button of editButtons){
	//add event listener
	button.addEventListener('click', function(event){
		//prevent form submiting
		event.preventDefault();
		//make new request 
		var request = new XMLHttpRequest();
		//type of request, url/file name, async or not
		request.open('GET', button.href, true);
		console.log(request.responseText);
		
		//
		request.onload = function (){
			//check if status is OK, before we do anything
			if(request.status == 200){
				// parse response text to json format, so we can acess coach properties
				var team = JSON.parse(request.responseText);
				//get elements by id and assign each value to be displayed
				document.querySelector('#idEdit').value = team.id;
				document.querySelector('#teamNameEdit').value = team.teamName;
				document.querySelector('#coachIdEdit').value = team.coachId;
				document.querySelector('#assistantCoachIdEdit').value = team.assistantCoachId;
			}
		}
		
		request.send();
		
		
		// make modal to show, by changing its display style
		editModal.style.display = 'block';
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
	    uploadModal.style.display = 'none';
	    photoModal.style.display = 'none';
	  })
	  // set if click is outside of form to close it
	  window.addEventListener('click', function(event){
		  // if user clicked on modal then close it
		  if(event.target == editModal || event.target == uploadModal || event.target == photoModal  ){
				editModal.style.display = 'none';
				uploadModal.style.display = 'none';
				photoModal.style.display = 'none';
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
	window.location='/rest/team';
})