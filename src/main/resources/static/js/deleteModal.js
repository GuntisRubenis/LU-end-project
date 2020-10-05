
var deleteButtons = document.querySelectorAll('.delete-button');
var deleteModal = document.querySelector('#deleteModal');
var modalCloseButtons = document.querySelectorAll('.modalCloseButton');

// for all delete buttons add click event to delete user by id
for (const button of deleteButtons) {
	
	  button.addEventListener('click', function(event) {
	    event.preventDefault();
	   
	    console.log(button.href);
	    // set dleteModalButtons href to current href
	    var modalDeleteButton = document.querySelector('.modalDeleteButton');
	    modalDeleteButton.href = button.href;
	    
	    // display modal
	    deleteModal.style.display = 'block';
	  })	
	
	}
// for all close buttons in deleteModal
for (const button of modalCloseButtons) {
	
	  button.addEventListener('click', function() {
	    
	    deleteModal.style.display = 'none';
	  })
	  // set if click is outside of form to close it
	  window.addEventListener('click', function(event){
		  // if user clicked on modal then close it
		  if(event.target == deleteModal){
				deleteModal.style.display = 'none';
				} 
	  });
	
	}
	

	
