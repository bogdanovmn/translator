{{#layout}}

{{#source}}
	<h1>{{.}}</h1>
{{/source}}
{{^source}}
	<h1>Все источники</h1>
{{/source}}

<div class="btn-group">
	{{#filter.menu}}
		<a class="btn btn-sm btn-outline-secondary {{#selected}}active{{/selected}}"
		   href='{{layout.contextPath}}{{filter.resourcePath}}?{{filterParams}}'>{{title}}</a>
	{{/filter.menu}}
</div>

{{#source}}
	<a class="btn btn-sm btn-outline-secondary {{#properNames}}active{{/properNames}}"
	   href='{{layout.contextPath}}/cloud/sources/{{id}}/proper-names'>Имена собственные</a>

	<a  role="button"
		class="btn btn-outline-primary btn-sm float-right"
		href="{{layout.contextPath}}/unknown-words?source={{id}}">Изучать!
	</a>
{{/source}}

<div class="cloud">
	{{#words}}
		<span style="font-size: {{relativeSizePercent}}%; color: {{color}};">
			{{word.name}}
		</span>
	{{/words}}
</div>

{{/layout}}