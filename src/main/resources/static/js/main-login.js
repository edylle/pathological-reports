var previewsForm = $('#form-login');

$('.message a').click(function(event) {
	event.preventDefault();

	previewsForm.animate({ height : "toggle", opacity : "toggle" }, "slow");
	previewsForm = $('#' + $(this).attr("data-target-formId"));
	previewsForm.animate({ height : "toggle", opacity : "toggle" }, "slow");

	$("#p-login-error").hide();
	$("#p-login-success").hide();
});