/*======================================*/
//Start of edit form
/*======================================*/
var editButtons = document.querySelectorAll('.edit-button');

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
				
				//cretae new div for old password, and insert it when edditing user
				const form = document.querySelectorAll('.user-form')[0];
				const buttons =document.querySelectorAll('.form-buttons')[0];
				var newDiv = document.createElement('div');
				var oldPWDInput = document.createElement('input');
				var oldPWDLabel = document.createElement('label');
				newDiv.classList.add('form-div');
				oldPWDLabel.innerHTML = 'Old password';
				oldPWDInput.setAttribute('type', 'password');
				oldPWDInput.setAttribute('name', 'oldPassword');
				newDiv.appendChild(oldPWDLabel);
				newDiv.appendChild(oldPWDInput);
				form.insertBefore(newDiv, buttons);
				document.querySelector('#password-label').innerHTML = "New password";
			}
		}
		
		request.send();
		
		
	})
}
/*======================================*/
//Search
/*======================================*/
var clearButton = document.querySelector('#clear-button');
clearButton.addEventListener('click', function(){
	//clear url 
	window.location='/secure/admin/user';
})