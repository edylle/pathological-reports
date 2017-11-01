$(function() {
	$('[data-toggle="tooltip"]').tooltip();
	$('[rel="tooltip"]').tooltip();
	$("#" + navActive).addClass("active");
	$(".js-number-two-digits").mask("9?9");
});

function enableFields() {
	$(".js-disabled").attr("disabled", false);
	$(".js-btn-view").remove();
	$(".js-btn-new").css("display", "inline-block");
	$(".js-password-new").css("display", "block");
};