/*======================================*/
//Start of edit modal
/*======================================*/
var editButtons = document.querySelectorAll('.edit-button');
var editModal = document.querySelector('#editModal');

for (const button of editButtons){
	button.addEventListener('click', function(event){
		event.preventDefault();
		var request = new XMLHttpRequest();
		//type of request, url/file name, async or not
		request.open('GET', button.href, true);
		console.log(request.responseText);
		
		//
		request.onload = function (){
			//check if status is OK, before we do anything
			if(request.status == 200){
				// parse response text to json format, so we can acess coach properties
				var user = JSON.parse(request.responseText);
				console.log(user.username);
				//get elements by id and assign each value to be displayed
				document.querySelectorAll('.user-form')[0].action = '/secure/admin/user/update'	
				document.querySelectorAll('.tittle')[0].innerHTML = 'Edit user';
				document.querySelector('#idEdit').value = user.id;
				document.querySelector('#userNameEdit').value = user.username;
				document.querySelector('#nameEdit').value = user.name;
				document.querySelector('#surnameEdit').value = user.surname;
				document.querySelector('#phoneEdit').value = user.phone;
				document.querySelector('#emailEdit').value = user.email;
				document.querySelector('#roleEdit').value = user.roles[0].id;
				
				
			}
		}
		
		request.send();
		
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
	window.location='/secure/admin/user';
})