{{#layout}}

{{> unknown_words/title}}

{{#words}}
	<div id="word-{{word.id}}" class="card my-4" data-word-id="{{word.id}}">
		<div class="card-header">
			<b>{{word.name}}</b>
			<span class="badge badge-secondary float-right font-weight-light">
				{{#source}}
					<b>{{frequency}}</b> упоминаний
				{{/source}}
				{{^source}}
					<b>{{word.frequency}}</b> упоминаний / <b>{{word.sourcesCount}}</b> источников
				{{/source}}
			</span>
		</div>

		<div id="word-content-{{id}}" class="{{#word.definitions.0}}card-body text-muted{{/word.definitions.0}}">
			{{#word.definitions.0}}
			<p class="text-body">{{word.definitions.0.pronunciation}}</p>
			{{#word.definitions}}
					<p class="word-definition">
						<b>{{partOfSpeech.name}} <i>{{partOfSpeechNote}}</i></b>
						<br><span class="text-body">{{description}}</span>
						{{#synonyms}}
							<br><i>Syn.:</i> {{synonyms}}
						{{/synonyms}}
						{{#examples.0}}
							<br><i>Ex.:</i> {{examples.0.value}}
						{{/examples.0}}
						{{#examples.1}}
							<br><i>Ex.:</i> {{examples.1.value}}
						{{/examples.1}}
					</p>
				{{/word.definitions}}
				<a class=""
				   href="https://en.oxforddictionaries.com/definition/{{word.name}}"
				   target="_blank">Подробнее
				</a>
			{{/word.definitions.0}}
		</div>

		<div class="card-footer">
			<div class="btn-group">
				<button type="button"
						class="btn btn-outline-success btn-sm"
						data-action="remembered"
						data-word-id="{{word.id}}"
						data-href="/words/{{word.id}}/remembered">Знаю!
				</button>

				<button type="button"
						class="btn btn-outline-secondary btn-sm"
						data-word-id="{{word.id}}"
						data-action="hold-over"
						data-href="/words/{{word.id}}/hold-over">Пропустить
				</button>

				{{#isAdmin}}
					<button type="button"
							class="btn btn-outline-danger btn-sm"
							data-action="black-list"
							data-word-id="{{word.id}}"
							data-href="/admin/words/{{word.id}}/black-list">Удалить
					</button>
				{{/isAdmin}}
			</div>
		</div>
	</div>
{{/words}}

{{> unknown_words/bottom}}


<script>
	var wordBlockInAction = {};

	$('div.card button').click(
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
		var words = $('div.card');
		if (words.length === 0) {
			location.reload()
		}
	}
</script>

{{/layout}}