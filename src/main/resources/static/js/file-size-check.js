function isFileSizeAllowed(files, size) {
    for (var x in files) {
        var filesize = ((files[x].size/1024)/1024).toFixed(4);
        if (files[x].name != "item" && typeof files[x].name != "undefined" && filesize > size) { 
        	return false;
        }
    }
    
    return true;
};