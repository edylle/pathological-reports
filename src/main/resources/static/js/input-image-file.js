(function () {
    var takePicture = document.querySelector("#input-imagem"),
        showPicture = document.querySelector("#show-imagem");

    if (takePicture && showPicture) {
        // Set events
        takePicture.onchange = function (event) {
            // Get a reference to the taken picture or chosen file
            var files = event.target.files,
                file;

            if (!isFileSizeAllowed(files, 0.999)) {
            	takePicture.value = "";
            	showPicture.src = "";
            	$(document.getElementsByClassName("p-tamanho-arquivo")).html('Este arquivo excedeu o tamanho de 999kB. Por favor, selecione um arquivo menor.');
            	return;
            }
            $(document.getElementsByClassName("p-tamanho-arquivo")).html('Tamanho máximo permitido: 999kB');

            if (files && files.length > 0) {
                file = files[0];
                try {
                    // Get window.URL object
                    var URL = window.URL || window.webkitURL;

                    // Create ObjectURL
                    var imgURL = URL.createObjectURL(file);

                    // Set img src to ObjectURL
                    showPicture.src = imgURL;

                    $(showPicture).addClass("img-cadastro");

                    // Revoke ObjectURL after imagehas loaded
                    showPicture.onload = function() {
                        URL.revokeObjectURL(imgURL);  
                    };
                }
                catch (e) {
                    try {
                        // Fallback if createObjectURL is not supported
                        var fileReader = new FileReader();
                        fileReader.onload = function (event) {
                            showPicture.src = event.target.result;
                        };
                        fileReader.readAsDataURL(file);
                    }
                    catch (e) {
                        // Display error message
                        var error = document.querySelector("#erro-imagem");
                        if (error) {
                            error.innerHTML = "Envio de imagens não suportado.";
                        }
                    }
                }
            }
        };
    }
})();