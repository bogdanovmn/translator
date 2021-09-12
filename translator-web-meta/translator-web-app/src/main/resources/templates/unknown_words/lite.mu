{{#layout}}

{{> unknown_words/title}}

{{#words}}
	<div id="word-{{id}}" class="row mb-2" data-word-id="{{id}}">

		<div class="col-4"><b>{{name}}</b></div>
		<div class="btn-group col-1">
			<button type="button"
					class="btn btn-outline-success btn-sm"
					data-action="remembered"
					data-word-id="{{id}}"
					data-href="/words/{{id}}/remembered">Знаю!
			</button>

			<button type="button"
					class="btn btn-outline-secondary btn-sm"
					data-word-id="{{id}}"
					data-action="hold-over"
					data-href="/words/{{id}}/hold-over">Пропустить
			</button>

			{{#isAdmin}}
				<button type="button"
						class="btn btn-outline-danger btn-sm"
						data-action="black-list"
						data-word-id="{{id}}"
						data-href="/admin/words/{{id}}/black-list">Удалить
				</button>
			{{/isAdmin}}
		</div>
	</div>
{{/words}}

{{>inc/pagination}}

{{> unknown_words/bottom}}

<script>
	var wordBlockInAction = {};

	$('div[id^="word-"] button').click(
		function() {
			var button = $(this);
			var actionUrl = "{{layout.contextPath}}" + button.attr("data-href");
			var wordId = button.attr('data-word-id');
			var wordBlockId = 'word-' + wordId;
			var wordBlock = $('#' + wordBlockId);

			if (wordBlockInAction[wordBlockId]) {
				return
			}
			wordBlockInAction[wordBlockId] = true;

			switch (button.attr("data-action")) {
				case "hold-over":
					actionAndHide(wordBlock, actionUrl);
					break;
				case "remembered":
					actionAndHide(wordBlock, actionUrl);
					break;
				case "black-list":
					actionAndHide(wordBlock, actionUrl);
					break;
				default:
					wordBlockInAction[wordBlockId] = false;
			}
		}
	);

	function actionAndHide(wordBlock, url) {
		console.log("[action and hide] " + url);
		wordBlock.remove();
		$.ajax({ url: url, method: 'PUT'})
			.done(function(resp) {
				console.log('[OK] ' + url);
				loadNewPortion();
			})
			.fail(function (jqXHR, excpetion) {
				console.log("[ERROR]" + url + "\n" + excpetion)
			})
			.always(function () {
			});
	}

	function loadNewPortion() {
		var words = $('div[id^="word-"]');
		if (words.length === 0) {
			location.reload()
		}
	}
</script>

{{/layout}}