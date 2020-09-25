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
	if(e.target == addModal){
	addModal.style.display = 'none';
	}
}
