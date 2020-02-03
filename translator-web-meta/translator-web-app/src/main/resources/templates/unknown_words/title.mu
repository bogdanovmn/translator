{{#words.0}}
	<h1>
		{{#source}}
			{{.}}
		{{/source}}
		{{^source}}
			Слова из всех источников
		{{/source}}

		<span class="badge badge-success badge-sm">
			{{#words.0}}
				{{#source}}
					{{userCount}} / {{source.wordsCount}}
				{{/source}}
				{{^source}}
					{{rememberedCount}} / {{toRememberCount}}
				{{/source}}
			{{/words.0}}
		</span>
	</h1>

	{{> unknown_words/list_menu}}
{{/words.0}}