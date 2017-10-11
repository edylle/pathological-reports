$('#modal-activate-user').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url');

	if (!action.endsWith('/')) {
		action += '/';
	}

	action += button.data('username');

	form.attr('action', action);

	modal.find('.modal-body span').html(button.data('message'));

});