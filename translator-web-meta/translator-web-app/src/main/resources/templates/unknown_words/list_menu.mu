<div class="row justify-content-between my-3">
<div class="dropdown">
	<button
		class="btn-sm btn-primary dropdown-toggle"
		type="button"
		id="liteModeMenu"
		data-toggle="dropdown"
		aria-haspopup="true"
		aria-expanded="false">
		{{#lite}}Турбо-режим{{/lite}}
		{{^lite}}Режим изучения{{/lite}}
	</button>
	<div class="dropdown-menu" aria-labelledby="liteModeMenu">
		<a
			class="dropdown-item"
			href="{{layout.contextPath}}/unknown-words?lite={{#lite}}false{{/lite}}{{^lite}}true{{/lite}}{{#source}}&source={{id}}{{/source}}{{#sortBy}}&sortBy={{.}}{{/sortBy}}">
			{{#lite}}Режим изучения{{/lite}}
			{{^lite}}Турбо-режим{{/lite}}
		</a>
	</div>
</div>

<div class="dropdown">
	<button
		class="btn-sm btn-primary dropdown-toggle"
		type="button"
		id="sortByMenu"
		data-toggle="dropdown"
		aria-haspopup="true"
		aria-expanded="false">
		{{#sortByName}}По имени{{/sortByName}}
		{{^sortByName}}По популярности{{/sortByName}}
	</button>
	<div class="dropdown-menu" aria-labelledby="sortByMenu">
		<a
			class="dropdown-item"
			href="{{layout.contextPath}}/unknown-words?{{#lite}}lite={{lite}}{{/lite}}{{#source}}&source={{id}}{{/source}}&sortBy={{#sortByName}}frequency{{/sortByName}}{{^sortByName}}name{{/sortByName}}">
			{{#sortByName}}По популярности{{/sortByName}}
			{{^sortByName}}По имени{{/sortByName}}
		</a>
	</div>
</div>
</div>