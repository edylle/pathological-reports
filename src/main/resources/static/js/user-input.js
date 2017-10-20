var fieldUsername = document.querySelector('[name="username"]');
var fieldPassword = document.querySelector('[name="password"]');

// prevent whitespaces
fieldUsername.addEventListener('keypress', function(event) {
	var key = event.keyCode;
	if (key === 32) {
		event.preventDefault();
	}
});

//prevent whitespaces
fieldPassword.addEventListener('keypress', function(event) {
	var key = event.keyCode;
	if (key === 32) {
		event.preventDefault();
	}
});