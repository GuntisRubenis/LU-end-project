const slides = document.querySelectorAll('.slide');
const next = document.querySelector('#next');
const prev = document.querySelector('#prev');
const auto = false;
const intervalTime = 5000;
let SlideInterval;

const nextSlide = function(){
	//select surrent div
	const current = document.querySelector('.current');
	//remove current classs form div
	current.classList.remove('current');
	// check for next div
	if(current.nextElementSibling){
		current.nextElementSibling.classList.add('current');
	}else {
		//add current to start
		slides[0].classList.add('current');
	}
	setTimeout(() => {current.classList.remove('current')});
	
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
		//add current to last
		slides[slides.length-1].classList.add('current');
	}
	setTimeout(() => {current.classList.remove('current')});
	
}

//button events
next.addEventListener('click', function(){
	nextSlide();
});

prev.addEventListener('click', function(){
	prevSlide();
});
