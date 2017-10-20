(function() {
	var takePicture = document.querySelector("#input-image");

	if (takePicture) {
		// Set events
		takePicture.onchange = function(event) {
			// Get a reference to the taken picture or chosen file
			var files = event.target.files, file;

			if (!isFileSizeAllowed(files, 0.999)) {
				takePicture.value = "";
				$("#id-label-inputfile").css("background-image", "url(" + contextPath + "/img/user-icon.png" + ")");
				$("#imageHelp-error").hide();
				$("#imageHelp").hide();
				$("#imageHelp-invalid").css("display", "block");
				return;
			}

			$("#imageHelp-error").hide();
			$("#imageHelp").hide();
			$("#imageHelp-invalid").hide();

			if (files && files.length > 0) {
				file = files[0];
				try {
					// Get window.URL object
					var URL = window.URL || window.webkitURL;

					// Create ObjectURL
					var imgURL = URL.createObjectURL(file);

					// Set img src to ObjectURL
					$("#id-label-inputfile").css("background-image", "url(" + imgURL + ")");

				} catch (e) {
					try {
						// Fallback if createObjectURL is not supported
						var fileReader = new FileReader();
						fileReader.onload = function(event) {
							$("#id-label-inputfile").css("background-image", "url(" + event.target.result + ")");
						};
						fileReader.readAsDataURL(file);
					} catch (e) {
						// Display error message
						$("#imageHelp-error").css("display", "block");
					}
				}
			}
		};
	}
})();