//slesct slide div
const slides = document.querySelectorAll('.slide');
// select buttons
const next = document.querySelector('#next');
const prev = document.querySelector('#prev');


const nextSlide = function(){
	//select surrent div
	const current = document.querySelector('.current');
	//remove current classs form div
	current.classList.remove('current');
	// check for next div and add current to it exists
	if(current.nextElementSibling){
		current.nextElementSibling.classList.add('current');
	}else {
		//add current to first div if there is no divs 
		slides[0].classList.add('current');
	}

	
}

const prevSlide = function(){
	//select surrent div
	const current = document.querySelector('.current');
	//remove current classs form div
	current.classList.remove('current');
	// check for prev div
	if(current.previousElementSibling){
		current.previousElementSibling.classList.add('current');
	}else {
		//add current to last div 
		slides[slides.length-1].classList.add('current');
	}
	
	
}

//button events
next.addEventListener('click', function(){
	nextSlide();
});

prev.addEventListener('click', function(){
	prevSlide();
});
