
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
				var player = JSON.parse(request.responseText);
				//get elements by id and assign each value to be displayed
				document.querySelector('#idEdit').value = player.id;
				document.querySelector('#nameEdit').value = player.name;
				document.querySelector('#surnameEdit').value = player.surname;
				document.querySelector('#ageEdit').value = player.age;
				document.querySelector('#phoneEdit').value = player.phone;
				document.querySelector('#emailEdit').value = player.email;
				document.querySelector('#positionEdit').value = player.position;
				document.querySelector('#alternatePositionEdit').value = player.alternatePosition;
				document.querySelector('#strongFootEdit').value = player.strongFoot;
				document.querySelector('#teamIdEdit').value = player.teamId;
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
//Search
/*======================================*/
var clearButton = document.querySelector('#clear-button');
clearButton.addEventListener('click', function(){
	//clear url 
	window.location='/rest/player/1';
})