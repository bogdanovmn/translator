{{#layout}}

{{#exportResult}}
	<h1>Импорт завершен</h1>
	{{#sources.0}}
		<h2>Новые источники</h2>
	{{/sources.0}}
	{{#sources}}
		<table class="import-info">
			<tr><td>Имя файла</td>  <td>{{rawName}}</td></tr>
			<tr><td>Автор</td>      <td>{{author}}</td></tr>
			<tr><td>Заголовок</td>  <td>{{title}}</td></tr>
			<tr><td>Кол-во слов</td><td>{{wordsCount}}</td></tr>
		</table>
	{{/sources}}
	{{#users.0}}
		<h2>Пользователи</h2>
	{{/users.0}}
	{{#users}}
		<table class="import-info">
			<tr><td>E-mail</td><td>{{email}}</td></tr>
		</table>
	{{/users}}
	<a href="">Импортировать еще</a>
{{/exportResult}}

{{^exportResult}}
<h1>Импорт из XML</h1>

{{>inc/error_msg}}

<div class=form>
	<form method=post enctype="multipart/form-data">
		<table>
		<tr>
			<td><input type=file name=file value='{{ImportForm.file}}'>
            <td>{{formError.file}}
		<tr>
			<td colspan=2><input type=submit value='  Импорт в БД  '>
		</table>
	</form>
</div>
{{/exportResult}}

{{/layout}}
