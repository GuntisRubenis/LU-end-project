//Add modal JAVASCRIPT
//=================================================================================
// get modal elements 
var addModal = document.getElementById("addModal");

// get add modal button
var modalButton = document.getElementById("addButton");
//get close button
var closeModalButton = document.getElementsByClassName("modalCloseButton")[0];

// lsiten for a click on modal button and execute function openModal
modalButton.addEventListener('click', openAddModal);
// listen for click on close button and execute closeModal function
closeModalButton.addEventListener('click', closeModal);


// function to open add modal
function openAddModal(){
	addModal.style.display = 'block';
}
// function to close modal 
function closeModal(){
	addModal.style.display = 'none';
}


//listen for click outside of modal
window.addEventListener('click', clickOutside);

//function to close modal by clicking on it  
function clickOutside(e){
	if(e.target == addModal || e.target == passwordModal){
	addModal.style.display = 'none';
	passwordModal.style.display = 'none';
	}
}

//Change password modal JAVASCRIPT
//=================================================================================
//get modal elements 
var passwordModal = document.getElementById("passwordModal");

//get add modal button
var passwordButton = document.getElementById("passwordButton");

var closePasswordButton = document.getElementsByClassName("modalCloseButton")[1];


//lsiten for a click on modal button and execute function openModal
passwordButton.addEventListener('click', openPasswordModal);
closePasswordButton.addEventListener('click', closePasswordModal);
//function to open add modal
function openPasswordModal(){
	passwordModal.style.display = 'block';
}

function closePasswordModal(){
	passwordModal.style.display = 'none';
}




