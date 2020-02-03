{{#layout}}

<h1>История получения определений слов ({{status}})</h1>

<a class="btn btn-outline-primary btn-sm"
   href="{{layout.contextPath}}/admin/definitions/log/last">Последние записи
</a>
{{#statistic}}
	<a class="btn btn-outline-secondary btn-sm"
	   href="{{layout.contextPath}}/admin/definitions/log?status={{status}}">{{status}}
		<span class="badge">{{count}}</span>
	</a>
{{/statistic}}

<table class="table table-sm table-hover">
	<thead>
	<tr>
		<th>Слово</th>
		<th>Статус</th>
		<th>Обновлено</th>
		<th>Текст ошибки</th>
	</tr>
	</thead>
	<tbody>
	{{#entries}}
		<tr>
		<td>{{word.name}}</td>
		<td>{{status}}</td>
		<td>{{updated}}</td>
		<td>{{message}}</td>
		</tr>
	{{/entries}}
	</tbody>
</table>
{{^entries}}
<h2>Пусто</h2>
{{/entries}}


{{/layout}}