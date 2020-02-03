{{#words.0}}
	<a href=""><img src="{{layout.contextPath}}/img/refresh.png" alt="refresh"/></a>
{{/words.0}}

{{^words}}
	<h2>Все уже изучено</h2>
{{/words}}

{{#source}}
	<div class="row justify-content-center">
		<div class="col-4">
			<a  role="button"
				class="btn btn-outline-secondary btn-sm"
				href="{{layout.contextPath}}/cloud/sources/{{id}}">Облако слов
			</a>
		</div>
	</div>
{{/source}}