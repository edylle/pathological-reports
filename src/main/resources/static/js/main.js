$(function() {
	$('[data-toggle="tooltip"]').tooltip();
	$("#" + navActive).addClass("active");
});

function enableFields() {
	$(".js-disabled").attr("disabled", false);
	$(".js-btn-view").remove();
	$(".js-btn-new").css("display", "inline-block");
	$(".js-password-new").css("display", "block");
};