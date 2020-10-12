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
//Attendance modal
/*======================================*/
var attendanceButtons = document.querySelectorAll('.attendance-button');
var attendanceModal = document.querySelector('#attendanceModal');

for (const button of attendanceButtons){
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
				// parse response text to json format, so we can acess training properties
				var training = JSON.parse(request.responseText);
				//create new input wher we will hold training id
				const teamIdValue = document.createElement('input');
				//select div to insert input before it 
				const submitButton =document.querySelectorAll('.attendance-div')[0];
				//select form where we will insert created input
				const form = document.querySelectorAll('.attendance-form')[0];
				// add input attributes, so we can pass id to addAttendance controller
				teamIdValue.setAttribute('type', 'text');
				teamIdValue.setAttribute('value', training.id);
				teamIdValue.setAttribute('name', 'id');
				//dont display this input
				teamIdValue.style.display = 'none';
				// insert input into attendanceModla form
				form.insertBefore(teamIdValue, submitButton);
				
				
				// for every player cretate new div with label and checbox
				for (const player of training.team.players){
					// crete new div, label and checkbox
					//select form and div before witch we will place new divs
					const playerDiv = document.createElement('div');
					const label = document.createElement('label');
					const checkBox = document.createElement('input');
					const form = document.querySelectorAll('.attendance-form')[0];
					const submitButton =document.querySelectorAll('.attendance-div')[0];
					const div1 = document.createElement('div');
					const div2 = document.createElement('div');
					// add class name sowe can style this div 
					playerDiv.classList.add('attendance-player-div');
					div1.classList.add('div1');
					div2.classList.add('div2');
					
					// add label inner html to be players name and surname
					label.innerHTML = player.name+' '+player.surname;
					// set input type, value and name
					checkBox.setAttribute('type','checkbox');
					// we will collect array of player id in addAttendance Controller
					checkBox.setAttribute('name','playerId');
					checkBox.setAttribute('value', player.id);
					// append label and checkbox
					div1.appendChild(label);
					div2.appendChild(checkBox);
					playerDiv.appendChild(div1);
					playerDiv.appendChild(div2);
					// add created div before submit button
					form.insertBefore(playerDiv, submitButton);
					console.log(form);
				}
			}
		}
		
		request.send();
		
		attendanceModal.style.display = 'block';
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
	    attendanceModal.style.display ='none'
	    window.location='/rest/training';
	  })
	  // set if click is outside of form to close it
	  window.addEventListener('click', function(event){
		  // if user clicked on modal then close it
		  if(event.target == editModal){
				editModal.style.display = 'none';
				
				}
		  if(event.target == attendanceModal){
			  attendanceModal.style.display = 'none';
			window.location='/rest/training';
		  }
		  
	  });
	
}

/*======================================*/
//Search
/*======================================*/
var clearButton = document.querySelector('#clear-button');
clearButton.addEventListener('click', function(){
	//clear url 
	window.location='/rest/training';
})