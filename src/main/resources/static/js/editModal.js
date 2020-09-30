
var editButtons = document.querySelectorAll('.editButton');
var editModal = document.querySelector('#editModal');
var saveButton = document.querySelector('.modalSaveButton');
// for all buttons in coach table
for (const button of editButtons){
	//add event listener
	button.addEventListener('click', function(event){
		//prevent form submiting
		event.preventDefault();
		//make new request 
		var xhr = new XMLHttpRequest();
		//type of request, url/file name, async or not
		xhr.open('GET', button.href, true);
		console.log(xhr.responseText);
		
		//
		xhr.onload = function (){
			//check if status is OK, before we do anything
			if(xhr.status == 200){
				// parse response text to json format, so we can acess coach properties
				var coach = JSON.parse(xhr.responseText);
				//get elements by id and assign each value to be displayed
				document.querySelector('#idEdit').value = coach.id;
				document.querySelector('#nameEdit').value = coach.name;
				document.querySelector('#surnameEdit').value = coach.surname;
				document.querySelector('#ageEdit').value = coach.age;
				document.querySelector('#phoneEdit').value = coach.phone;
				document.querySelector('#emailEdit').value = coach.email;
				document.querySelector('#categoryEdit').value = coach.category;
				document.querySelector('#teamsEdit').value = coach.teams;
				document.querySelector('#assistantTeamsEdit').value = coach.assistantTeams;
			}
		}
		
		xhr.send();
		
		
		// make modal to show, by changing its display style
		editModal.style.display = 'block';
	})
}


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