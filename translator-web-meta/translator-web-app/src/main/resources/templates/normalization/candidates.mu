{{#layout}}

<table class="table table-sm table-hover">
	<thead>
		<tr>
			<th>Базовое слово</th>
			<th>Формы</th>
			<th>...</th>
		</tr>
	</thead>
	{{#entries}}
		<tr id="candidate-{{id}}">
			<td>{{base}}</td>
			<td>{{forms}}</td>
			<td>
				<button type="button"
						class="btn btn-outline-success btn-sm"
						data-action="approve"
						data-candidate-id="{{id}}">Применить
				</button>
				<button type="button"
						class="btn btn-outline-danger btn-sm"
						data-action="delete"
						data-candidate-id="{{id}}">Удалить
				</button>
			</td>
		</tr>
	{{/entries}}
</table>

<script>
	var blockInAction = {};

	$('button').click(
		function() {
			var button = $(this);
			var candidateId = button.attr('data-word-id');
			var blockId = 'candidate-' + candidateId;
			var actionUrl = "{{layout.contextPath}}/normalization/" + button.attr("data-candidate-id");

			if (blockInAction[blockId]) {
				return
			}
			blockInAction[blockId] = true;
			button.prop("disabled", true);


			switch (button.attr("data-action")) {
				case "approve":
					actionAndHide(blockId, actionUrl);
					break;
				case "delete":
					actionAndHide(blockId, actionUrl);
					break;
			}
		}
	);

	function actionAndHide(blockId, url) {
		console.log("[action and hide] " + url);
		$.ajax({ url: url, method: 'PUT'})
			.done(function(resp) {
				console.log('[OK] ' + url);
				$("#" + blockId).remove();
			})
			.fail(function (jqXHR, excpetion) {
				console.log("[ERROR]" + url + "\n" + excpetion)
			})
			.always(function () {
				blockInAction[blockId] = false;
			});
	}
</script>

{{/layout}}