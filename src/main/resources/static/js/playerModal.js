
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
//End of edit modal
/*======================================*/

/*======================================*/
//Start of upload modal
/*======================================*/
var uploadModal= document.querySelector('#uploadModal');
var detailsButtons = document.querySelectorAll('.details-button');

for (const button of detailsButtons){
	button.addEventListener('click', function(event){
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
				//get upload forms action url
				var actionUrl = document.querySelector('#uploadForm').action;
				console.log(actionUrl);
				//add actipn forms url coaches id, so we can save pictures qith unique id
				var url = document.querySelector('#uploadForm').action = actionUrl+player.id;
				console.log(url);
			}
		}
		
		request.send();
		
		uploadModal.style.display = 'block';
	})
}
/*======================================*/
//End of upload modal
/*======================================*/

/*======================================*/
//Start of photo modal
/*======================================*/
var photoButtons = document.querySelectorAll('.photo-button');
var photoModal = document.querySelector('#photoModal');

for(const button of photoButtons){
	button.addEventListener('click', function(event){
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
				//set coach image url
				document.querySelector('#photoModalImage').src = '/img/players/' + player.id +'.jpg';
				document.querySelector('#photo-name').innerHTML = player.name;
				document.querySelector('#photo-surname').innerHTML = player.surname;
				document.querySelector('#photo-age').innerHTML = player.age;
			}
		}
		
		request.send();
		
		
		
		photoModal.style.display = 'block';
	})
}
/*======================================*/
//End of photo modal
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
	window.location='/rest/player';
})