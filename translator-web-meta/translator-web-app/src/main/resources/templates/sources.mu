{{#layout}}

{{#sources.0}}
	<h1>Все источники</h1>
{{/sources.0}}
{{#sources}}
	<div id="source-{{source.id}}" class="card my-4">
		<div class="card-header">
			<span class="badge badge-secondary">{{userWordsRememberedCount}} из {{source.nonBlackListCount}} слов изучено</span>
		</div>
		<div class="card-body progressbar-wrapper">
			{{source.name}}
			<div class="progressbar" style="width: {{userWordsRememberedPercent}}%"></div>
		</div>

		<div class="card-footer">
			<div class="btn-group">
				<a class="btn btn-sm btn-outline-secondary"
				   href="{{layout.contextPath}}/unknown-words?source={{source.id}}">Изучать
				</a>
				<a class="btn btn-sm btn-outline-secondary"
				   href="{{layout.contextPath}}/cloud/sources/{{source.id}}">Облако слов
				</a>
				{{#isAdmin}}
					<button type="button"
							class="btn btn-sm btn-outline-danger"
							data-action="delete"
							data-source-id="{{source.id}}"
							data-href="/admin/sources/{{source.id}}">Удалить
					</button>
				{{/isAdmin}}
			</div>
		</div>
	</div>
{{/sources}}

{{^sources}}
	<h2>Нет источников</h2>
{{/sources}}


<script>
	var sourceBlockInAction = {};

	$('div.card button').click(
		function() {
			var button = $(this);
			var actionUrl = "{{layout.contextPath}}" + button.attr("data-href")
			var sourceBlockId = button.attr('data-source-id');

			if (sourceBlockInAction[sourceBlockId]) {
				return
			}

			sourceBlockInAction[sourceBlockId] = true;
			button.prop("disabled", true);

			switch (button.attr("data-action")) {
				case "delete":
					actionAndHide(sourceBlockId, actionUrl, "DELETE");
					break;
			}
		}
	);

	function actionAndHide(sourceBlockId, url, method) {
		console.log("[action and hide] " + url);
		$.ajax({ url: url, method: method})
			.done(function(resp) {
				console.log('[OK] ' + url);
				$("#source-" + sourceBlockId).remove();
			})
			.fail(function (jqXHR, excpetion) {
				console.log("[ERROR]" + url + "\n" + excpetion)
			})
			.always(function () {
				sourceBlockInAction[sourceBlockId] = false;
			});
	}
</script>
{{/layout}}